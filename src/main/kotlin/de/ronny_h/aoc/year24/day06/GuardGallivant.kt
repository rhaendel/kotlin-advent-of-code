package de.ronny_h.aoc.year24.day06

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Direction.NORTH
import de.ronny_h.aoc.extensions.Grid
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import printAndCheck
import readInput
import java.lang.Runtime.getRuntime
import java.util.concurrent.atomic.AtomicInteger

suspend fun main() {
    val day = "Day06"
    val input = readInput(day)
    val guard = GuardGallivant()

    println("$day part 1")
    printAndCheck(input, guard::part1, 4580)

    println("$day part 2")
    printAndCheck(input, guard::part2, 1480)
}

class GuardGallivant {
    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Coordinates>()
        Lab(input).doTheGuardWalk { position, _ ->
            visited.add(position)
            true
        }
        return visited.size
    }

    suspend fun part2(input: List<String>): Int {
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

        return loopCount.get()
    }
}

private class Lab(input: List<String>) : Grid<Char>(input, ' ') {

    private val guard = '^'
    private val obstruction = '#'
    private val free = '.'
    private val offMap = nullElement
    override fun Char.toElementType() = this

    private fun findTheGuard(): Coordinates = forEachElement { row, col, element ->
        if (element == guard) Coordinates(row, col) else null
    }.filterNotNull().first()

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
        var position = findTheGuard()
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
