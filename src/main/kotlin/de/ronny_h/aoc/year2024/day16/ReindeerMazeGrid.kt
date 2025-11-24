package de.ronny_h.aoc.year2024.day16

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStarAllPaths
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = ReindeerMaze().run(89460, 504)

class ReindeerMaze : AdventOfCode<Int>(2024, 16) {
    override fun part1(input: List<String>): Int {
        val maze = ReindeerMazeGrid(input)
        maze.printGrid()
        return maze.calculateLowestScore()
    }

    override fun part2(input: List<String>): Int {
        val maze = ReindeerMazeGrid(input)
        return maze.collectAllShortestPathsTiles()
    }
}

private class ReindeerMazeGrid(input: List<String>) : Grid<Char>(input, '#') {
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
        val start = Node(Direction.EAST, Coordinates(1, height - 2))
        val goal = Node(Direction.EAST, Coordinates(width - 2, 1))

        val neighbours: (Node) -> List<Node> = { n ->
            listOf(
                Node(n.direction, n.position + n.direction),
                Node(n.direction.turnLeft(), n.position),
                Node(n.direction.turnRight(), n.position)
            ).filter { get(it.position) != wall }
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

        @Suppress("unused")
        val printIt: (Set<Node>, Node, () -> String) -> Unit = { visited, current, info ->
            printGrid(visited.map { it.position }.toSet(), 'o', current.position, current.direction)
            println(info.invoke())
        }

        return aStarAllPaths(start, goal::positionEquals, neighbours, d, h)
    }
}
