package de.ronny_h.aoc.year2024.day20

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.animation.*
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStar
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Grid
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.EMPTY
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.GOAL
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.SHORTCUT_START
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.START
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.VISITED
import de.ronny_h.aoc.year2024.day20.RaceTrack.Companion.WALL
import java.awt.Color.red

fun main() = RaceCondition().run(1438, 1026446)

class RaceCondition : AdventOfCode<Int>(2024, 20) {
    fun part1(input: List<String>, minPicosecondsSaved: Int, shortcutMaxLength: Int): Int {
        val track = RaceTrack(input)
//        track.recorder = AnimationRecorder()
//        track.printGrid()
        val result = track.countAllShortcutsSavingAtLeast(minPicosecondsSaved, shortcutMaxLength)
        track.recorder?.saveTo(
            "animations/$year-${paddedDay()}_${javaClass.simpleName}.gif",
            mapOf(
                START to green,
                GOAL to yellow,
                WALL to lightGrey,
                EMPTY to gray,
                VISITED to lightBlue,
                SHORTCUT_START to red,
            ),
        )
        return result
    }

    fun part1Small(input: List<String>) = part1(input, 10, 2)
    override fun part1(input: List<String>) = part1(input, 100, 2)

    fun part2Small(input: List<String>) = part1(input, 76, 20)
    override fun part2(input: List<String>) = part1(input, 100, 20)

}

private class RaceTrack(input: List<String>) : Grid<Char>(input, WALL) {

    companion object {
        const val START = 'S'
        const val GOAL = 'E'
        const val WALL = '#'
        const val EMPTY = '.'
        const val VISITED = 'o'
        const val SHORTCUT_START = 'x'
    }

    override fun Char.toElementType(): Char = this

    private val start = find(START)
    private val goal = find(GOAL)

    fun shortestPath(): ShortestPath<Coordinates> {
        val neighbours: (Coordinates) -> List<Coordinates> = { node ->
            Direction
                .entries
                .map { node + it }
                .filter { get(node) != WALL }
        }

        val d: (Coordinates, Coordinates) -> Int = { _, _ ->
            // pre-condition: the coordinates are neighbours
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal }

        return aStar(
            start, { this == goal }, neighbours, d, h,
            printIt = { visited, _, _ ->
                recorder?.record(toString(visited.filter { get(it) == EMPTY }.toSet(), VISITED))
            }
        )
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

        val shortcutStartPositions = shortestPath.path.flatMap { position ->
            shortestPath
                .path
                .filter { position.isInShortcutRangeFrom(it) }
                .filter { position.isShorterToGoalThan(it) }
        }
        recorder?.recordLastFrameWithOverrides(shortcutStartPositions, SHORTCUT_START)
        recorder?.repeatLast(2, 3)
        return shortcutStartPositions.size
    }
}
