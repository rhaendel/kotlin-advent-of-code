package de.ronny_h.aoc.year24.day20

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.ShortestPath
import de.ronny_h.aoc.extensions.aStar
import printAndCheck
import readInput

fun main() {
    val day = "Day20"

    fun part1(input: List<String>, minPicosecondsSaved: Int, shortcutMaxLength: Int): Int {
        val track = RaceTrack(input)
        track.printGrid()
        return track.countAllShortcutsSavingAtLeast(minPicosecondsSaved, shortcutMaxLength)
    }

    fun part1Small(input: List<String>) = part1(input, 10, 2)
    fun part1Large(input: List<String>) = part1(input, 100, 2)

    fun part2Small(input: List<String>) = part1(input, 76, 20)
    fun part2Large(input: List<String>) = part1(input, 100, 20)

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

    printAndCheck(testInput, ::part2Small, 3)
    printAndCheck(input, ::part2Large, 1026446)
}

private class RaceTrack(input: List<String>) : Grid<Char>(input, '#') {
    private val wall = nullElement
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

    fun countAllShortcutsSavingAtLeast(minToSave: Int, shortcutMaxLength: Int): Int {
        val shortestPath = shortestPath()
        val distances = shortestPath.path.reversed().mapIndexed { distanceToGoal, position ->
            position to distanceToGoal
        }.toMap()

        fun Coordinates.isInShortcutRangeFrom(position: Coordinates): Boolean =
            this taxiDistanceTo position <= shortcutMaxLength

        fun Coordinates.isShorterToGoalThan(position: Coordinates): Boolean =
            distances.getValue(this) + (this taxiDistanceTo position) <= distances.getValue(position) - minToSave

        return shortestPath.path.flatMap { position ->
            shortestPath
                .path
                .filter { it.isInShortcutRangeFrom(position) }
                .filter { it.isShorterToGoalThan(position) }
        }.size
    }
}
