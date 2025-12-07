package de.ronny_h.aoc.year2025.day07

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction.*
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid

fun main() = Laboratories().run(1600, 8632253783011)

class Laboratories : AdventOfCode<Long>(2025, 7) {
    override fun part1(input: List<String>): Long = TachyonManifoldDiagram(input).followTheBeam().splitterCount

    override fun part2(input: List<String>): Long = TachyonManifoldDiagram(input).followTheBeam().numberOfTimeLines
}

data class TachyonManifoldInfo(val splitterCount: Long, val numberOfTimeLines: Long) {
    operator fun plus(other: TachyonManifoldInfo) =
        TachyonManifoldInfo(splitterCount + other.splitterCount, numberOfTimeLines + other.numberOfTimeLines)
}

private const val emptySpace = '.'
private const val splitter = '^'
private const val beam = '|'
private const val outOfMap = '#'

class TachyonManifoldDiagram(input: List<String>) : SimpleCharGrid(input, outOfMap) {
    private val start = find('S')

    fun followTheBeam(): TachyonManifoldInfo = followTheBeam(start + SOUTH)

    private val timelinesStartingAt = mutableMapOf<Coordinates, Long>()

    private fun timelinesStartingAt(start: Coordinates): Long {
        var position = start
        while (timelinesStartingAt[position] == null) {
            position += SOUTH
            if (position.y == height) {
                return 1
            }
        }
        return timelinesStartingAt.getValue(position)
    }

    private fun followTheBeam(start: Coordinates): TachyonManifoldInfo {
        if (get(start) == beam) return TachyonManifoldInfo(0, timelinesStartingAt(start))

        var position: Coordinates = start
        while (get(position) == emptySpace) {
            set(position, beam)
            position += SOUTH
        }

        if (get(position) == outOfMap) return TachyonManifoldInfo(0, 1)
        if (get(position) == beam) return TachyonManifoldInfo(0, timelinesStartingAt(start))

        check(get(position) == splitter) { "$position: expected a splitter ($splitter), but was: '${get(position)}'" }
        val left = followTheBeam(position + WEST)
        val right = followTheBeam(position + EAST)
        timelinesStartingAt[position + WEST] = left.numberOfTimeLines
        timelinesStartingAt[position + EAST] = right.numberOfTimeLines
        timelinesStartingAt[position] = left.numberOfTimeLines + right.numberOfTimeLines
        return TachyonManifoldInfo(1, 0) + left + right
    }
}
