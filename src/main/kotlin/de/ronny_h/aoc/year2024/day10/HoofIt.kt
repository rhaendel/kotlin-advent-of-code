package de.ronny_h.aoc.year2024.day10

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = HoofIt().run(816, 1960)

class HoofIt : AdventOfCode<Int>(2024, 10) {
    override fun part1(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.score }

    override fun part2(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.rating }
}

private class TopographicMap(input: List<String>) : Grid<Int>(input, Int.MIN_VALUE) {

    override fun Char.toElementType() = if (isDigit()) digitToInt() else nullElement

    private fun heights(): Sequence<Pair<Coordinates, Int>> = forEachElement { row, col, height ->
        Coordinates(col, row) to height
    }

    private fun heightAt(coordinates: Coordinates) = getAt(coordinates)

    fun searchTrailheads(): List<Trailhead> {
        val startPositions = heights().filter { it.second == 0 }
        return startPositions
            .map {
                val trailhead = Trailhead(it.first)
                if (trailhead.findPathsToNines(::heightAt)) {
                    trailhead
                } else {
                    null
                }
            }
            .mapNotNull { it }
            .toList()
    }
}

class Trailhead(private val startPosition: Coordinates) {
    val score: Int
        get() = reachableNines.size

    var rating: Int = 0
        private set

    private val reachableNines = mutableSetOf<Coordinates>()

    private fun addReachable(coordinates: Coordinates) {
        reachableNines.add(coordinates)
        rating++
    }

    fun findPathsToNines(heightAt: (Coordinates) -> Int): Boolean {
        findPathsToNines(startPosition, -1, heightAt)
        return score > 0
    }

    private fun findPathsToNines(coordinates: Coordinates, lastHeight: Int, heightAt: (Coordinates) -> Int) {
        val height = heightAt(coordinates)
        // println("$coordinates height: $height lastHeight: $lastHeight")
        if (height != lastHeight + 1) {
            return
        }
        if (height == 9) {
            addReachable(coordinates)
            return
        }

        Direction.entries.forEach { direction ->
            findPathsToNines(coordinates + direction, height, heightAt)
        }
    }
}
