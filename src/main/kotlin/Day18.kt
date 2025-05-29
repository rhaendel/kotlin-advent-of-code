import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction
import de.ronny_h.extensions.Direction.EAST
import de.ronny_h.extensions.Direction.NORTH
import de.ronny_h.extensions.Direction.SOUTH
import de.ronny_h.extensions.Direction.WEST
import de.ronny_h.extensions.Grid
import de.ronny_h.extensions.ShortestPath
import de.ronny_h.extensions.aStar

fun main() {
    val day = "Day18"

    fun part1(input: List<String>, width: Int): Int {
        val memorySpace = MemorySpace(width, input.map {
            val (x, y) = it.split(',')
            Coordinates(x.toInt(), y.toInt())
        })
        memorySpace.printGrid()
        println("-----------------")
        val shortestPaths = memorySpace.shortestPath(Coordinates(0,0), Coordinates(width-1, width-1))
        memorySpace.printGrid(path = shortestPaths.first().path.associateWith { 'O' })
        return shortestPaths.first().distance
    }

    fun part1Small(input: List<String>) = part1(input, 7) // 0..6
    fun part1Big(input: List<String>) = part1(input, 71)  // 0..70

    fun part2(input: List<String>): Int {
        return input.size
    }

    println("$day part 1")

    val testInput = readInput("${day}_test")
    printAndCheck(testInput.subList(0, 12), ::part1Small, 22)

    val input = readInput(day)
    printAndCheck(input.subList(0, 1024), ::part1Big, 3714264)


    println("$day part 2")

    printAndCheck(
        listOf(
            "1   6"
        ), ::part2, 1
    )
    printAndCheck(testInput, ::part2, 31)
    printAndCheck(input, ::part2, 18805872)
}

private class MemorySpace(width: Int, corrupted: List<Coordinates>) : Grid<Char>(width, width, '.', '#', corrupted) {
    private val corrupted = '#'
    override fun Char.toElementType() = this

    fun shortestPath(start: Coordinates, goal: Coordinates): List<ShortestPath<Coordinates>> {
        val neighbours: (Coordinates) -> List<Coordinates> = { node ->
            Direction
                .entries
                .map { node + it }
                .filter { getAt(node) != corrupted }
        }

        val d: (Coordinates, Coordinates) -> Int = { a, b ->
            // pre-condition: a and b are neighbours
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal}

        return aStar(start, goal::equals, neighbours, d, h)
    }
}
