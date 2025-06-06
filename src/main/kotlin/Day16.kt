import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.ShortestPath
import de.ronny_h.aoc.extensions.aStarAllPaths

fun main() {
    val day = "Day16"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val maze = ReindeerMaze(input)
        maze.printGrid()
        return maze.calculateLowestScore()
    }

    printAndCheck(
        """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent().lines(),
        ::part1, 7036
    )

    printAndCheck(
        """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent().lines(),
        ::part1, 11048
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 89460)


    println("$day part 2")

    fun part2(input: List<String>): Int {
        val maze = ReindeerMaze(input)
        return maze.collectAllShortestPathsTiles()
    }

    printAndCheck(
        """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent().lines(),
        ::part2, 45
    )

    printAndCheck(
        """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent().lines(),
        ::part2, 64
    )
    printAndCheck(input, ::part2, 504)
}

private class ReindeerMaze(input: List<String>) : Grid<Char>(input, '#') {
    private val wall = nullElement
    override fun Char.toElementType() = this

    data class Node(val direction: Direction, val position: Coordinates) {
        override fun toString() = "$position$direction"
        fun positionEquals(other: Node) = this.position == other.position
    }

    fun calculateLowestScore(): Int {
        val allShortestPaths = getAllShortestPaths()

        printGrid(path = allShortestPaths.map { it.path }.flatten().associate { it.position to it.direction.asChar() })
        return allShortestPaths.first().distance
    }

    fun collectAllShortestPathsTiles() = getAllShortestPaths()
        .flatMap { it.path }
        .map { it.position }
        .toSet()
        .size

    private fun getAllShortestPaths(): List<ShortestPath<Node>> {
        // A* algorithm
        // - heuristic function: manhattan distance
        // - weight function:
        //   * 1 for going forward
        //   * 1000 for each 90° turn
        val start = Node(Direction.EAST, Coordinates(height - 2, 1))
        val goal = Node(Direction.EAST, Coordinates(1, width - 2))

        val neighbours: (Node) -> List<Node> = { n ->
            listOf(
                Node(n.direction, n.position + n.direction),
                Node(n.direction.turnLeft(), n.position),
                Node(n.direction.turnRight(), n.position)
            ).filter { getAt(it.position) != wall }
        }

        val d: (Node, Node) -> Int = { a, b ->
            if (a.position == goal.position && b.position == goal.position) {
                // direction at goal doesn't care
                0
            } else if (a.direction == b.direction) {
                // straight ahead
                1
            } else {
                1000
            }
        }

        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        val printIt: (Set<Node>, Node, () -> String) -> Unit = { visited, current, info ->
            printGrid(visited.map { it.position }.toSet(), 'o', current.position, current.direction)
            println(info.invoke())
        }

        return aStarAllPaths(start, goal::positionEquals, neighbours, d, h)
    }

}
