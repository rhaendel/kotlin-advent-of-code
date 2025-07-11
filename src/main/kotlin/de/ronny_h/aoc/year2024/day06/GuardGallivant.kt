package de.ronny_h.aoc.year2024.day06

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Direction.NORTH
import de.ronny_h.aoc.extensions.grids.Grid
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import java.lang.Runtime.getRuntime
import java.util.concurrent.atomic.AtomicInteger

fun main() = GuardGallivant().run(4580, 1480)

class GuardGallivant : AdventOfCode<Int>(2024, 6) {
    override fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Coordinates>()
        Lab(input).doTheGuardWalk { position, _ ->
            visited.add(position)
            true
        }
        return visited.size
    }

    override fun part2(input: List<String>): Int = runBlocking {
        val lab = Lab(input)
        val loopCount = AtomicInteger(0)

        withContext(Dispatchers.Default) {
            val coresCount = getRuntime().availableProcessors()
            val positions = lab.freePositions(this, coresCount)
            (1..coresCount).map {
                async {
                    for (additionalObstruction in positions) {
                        val visited = mutableSetOf<Pair<Coordinates, Direction>>()
                        lab.doTheGuardWalk(additionalObstruction) { position, direction ->
                            if (visited.add(position to direction)) {
                                true
                            } else {
                                loopCount.incrementAndGet()
                                false
                            }
                        }
                    }
                }
            }.awaitAll()
        }

        return@runBlocking loopCount.get()
    }
}

private class Lab(input: List<String>) : Grid<Char>(input, ' ') {

    private val guard = '^'
    private val obstruction = '#'
    private val free = '.'
    private val offMap = nullElement
    override fun Char.toElementType() = this

    @OptIn(ExperimentalCoroutinesApi::class)
    fun freePositions(scope: CoroutineScope, bufferSize: Int) = scope.produce(capacity = bufferSize) {
        forEachElement { row, col, char ->
            if (char == free) Coordinates(row, col) else null
        }
            .filterNotNull()
            .forEach {
                send(it)
            }
    }

    fun doTheGuardWalk(
        additionalObstruction: Coordinates? = null,
        visit: (Coordinates, Direction) -> Boolean,
    ) {
        var direction = NORTH
        var position = find(guard)
        var doWalkFurther = true

        while (getAt(position) != offMap && doWalkFurther) {
            doWalkFurther = visit(position, direction)
            val stepAhead = position + direction
            if (getAt(stepAhead) == obstruction || stepAhead == additionalObstruction) {
                direction = direction.turnRight()
            } else {
                position = stepAhead
            }
        }
    }
}
