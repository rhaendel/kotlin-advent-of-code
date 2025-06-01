import de.ronny_h.extensions.*
import de.ronny_h.extensions.Direction.*

fun main() {
    val day = "Day20"

    fun part1(input: List<String>, minPicosecondsSaved: Int): Int {
        val track = RaceTrack(input)
        track.printGrid()
        val baseline = track.shortestPath().distance
        return track.countAllShortcutsShorterThan(baseline - minPicosecondsSaved)
    }

    fun part1Small(input: List<String>) = part1(input, 10)
    fun part1Large(input: List<String>) = part1(input, 100)

    fun part2(input: List<String>): Int {
        return input.size
    }

    println("$day part 1")

    val testInput = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
    """.trimIndent().split('\n')
    printAndCheck(testInput, ::part1Small, 10)

    val input = readInput(day)
    printAndCheck(input, ::part1Large, 1438)


    println("$day part 2")

    printAndCheck(testInput, ::part2, 31)
    printAndCheck(input, ::part2, 18805872)
}

private class RaceTrack(input: List<String>) : Grid<Char>(input, '#') {
    private val wall = nullElement
    private val free = '.'
    override fun Char.toElementType(): Char = this

    private val start = find('S')
    private val goal = find('E')

    private fun find(symbol: Char): Coordinates = forEachElement { row, col, elem ->
        when (elem) {
            symbol -> Coordinates(row, col)
            else -> null
        }
    }.filterNotNull().first()

    fun shortestPath(): ShortestPath<Coordinates> {
        val neighbours: (Coordinates) -> List<Coordinates> = { node ->
            Direction
                .entries
                .map { node + it }
                .filter { getAt(node) != wall }
        }

        val d: (Coordinates, Coordinates) -> Int = { a, b ->
            // pre-condition: a and b are neighbours
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal }

        return aStar(start, goal, neighbours, d, h)
    }

    fun countAllShortcutsShorterThan(picoseconds: Int): Int =
        forEachCoordinates { pos, elem ->
            when {
                elem != wall -> null
                pos.isOnTheBorder() -> null
                pos.canNotBeCrossed() -> null
                else -> pos
            }
        }
            .filterNotNull()
            .map { coord ->
                setAt(coord, free)
                val pathWithShortcut = shortestPath()
                setAt(coord, wall)
                pathWithShortcut.distance
            }
            .filter { it <= picoseconds }
            .count()

    private fun Coordinates.isOnTheBorder(): Boolean {
        return row == 0 || row == (height - 1) || col == 0 || col == width - 1
    }

    private fun Coordinates.canNotBeCrossed(): Boolean {
        return (getAt(this + NORTH) == wall || getAt(this + SOUTH) == wall) &&
                (getAt(this + EAST) == wall || getAt(this + WEST) == wall)
    }

}
