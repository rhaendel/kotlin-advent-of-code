package de.ronny_h.aoc.year24.day10

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.printAndCheck
import de.ronny_h.aoc.extensions.readInput

fun main() {
    val day = "Day10"
    val input = readInput(day)
    val hoofIt = HoofIt()

    println("$day part 1")
    printAndCheck(input, hoofIt::part1, 816)

    println("$day part 2")
    printAndCheck(input, hoofIt::part2, 1960)
}

class HoofIt {
    fun part1(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.score }

    fun part2(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.rating }
}

private class TopographicMap(input: List<String>) : Grid<Int>(input, Int.MIN_VALUE) {

    override fun Char.toElementType() = if (isDigit()) digitToInt() else nullElement

    private fun heights(): Sequence<Pair<Coordinates, Int>> = forEachElement { row, col, height ->
        Coordinates(row, col) to height
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
