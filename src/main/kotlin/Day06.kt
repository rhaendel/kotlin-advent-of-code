import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction
import de.ronny_h.extensions.Direction.NORTH
import de.ronny_h.extensions.Grid
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import java.lang.Runtime.getRuntime
import java.util.concurrent.atomic.AtomicInteger

suspend fun main() {
    val day = "Day06"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Coordinates>()
        Lab(input).doTheGuardWalk { position, _ ->
            visited.add(position)
            true
        }
        return visited.size
    }

    printAndCheck(
        listOf(
            ".#..",
            "...#",
            ".^..",
            "..#."
        ),
        ::part1, 5
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 41)

    val input = readInput(day)
    printAndCheck(input, ::part1, 4580)


    println("$day part 2")

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

    printAndCheck(
        listOf(
            ".#..",
            "...#",
            ".^..",
            "..#."
        ),
        ::part2, 1
    )

    printAndCheck(testInput, ::part2, 6)
    printAndCheck(input, ::part2, 1480)
}

private class Lab(input: List<String>) : Grid<Char>(input) {

    private val guard = '^'
    private val obstruction = '#'
    private val free = '.'
    private val offMap = ' '
    override val nullElement = offMap
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
