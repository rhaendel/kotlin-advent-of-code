package de.ronny_h.aoc.year2025.day09

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.symmetricalCombinations
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() = MovieTheater().run(4771532800, 1544362560)

class MovieTheater : AdventOfCode<Long>(2025, 9) {
    override fun part1(input: List<String>): Long {
        return input.parseTiles()
            .symmetricalCombinations()
            .maxOf { (a, b) -> areaOfRectangle(a, b) }
    }

    override fun part2(input: List<String>): Long {
        val redTiles = input.parseTiles()
        val polygon = Polygon(redTiles)

        return redTiles.symmetricalCombinations()
            .filter { (a, b) -> polygon.allTilesInRectangleAreRedOrGreen(a, b) }
            .maxOf { (a, b) -> areaOfRectangle(a, b) }
    }
}

class Polygon(input: List<Coordinates>) {

    private val segments = input.toLineSegments()

    fun allTilesInRectangleAreRedOrGreen(a: Coordinates, b: Coordinates): Boolean = segments.none {
        it.s.isInRectangle(a, b) || it.t.isInRectangle(a, b) || it.crossesRectangle(a, b)
    }

    private fun Coordinates.isInRectangle(a: Coordinates, b: Coordinates): Boolean =
        x in rangeBetween(a.x, b.x) && y in rangeBetween(a.y, b.y)

    private fun List<Coordinates>.toLineSegments(): List<LineSegment> = buildList {
        this@toLineSegments.windowed(2) { (a, b) ->
            add(LineSegment(a, b))
        }
        add(LineSegment(this@toLineSegments.last(), this@toLineSegments.first()))
    }
}

data class LineSegment(val s: Coordinates, val t: Coordinates) {
    private val isVertical: Boolean
        get() = s.x == t.x

    fun crossesRectangle(a: Coordinates, b: Coordinates): Boolean =
        if (isVertical) {
            t.x in rangeBetween(a.x, b.x) && segmentPointsAreOnOppositeSitesOfRectangle(a, b)
        } else {
            t.y in rangeBetween(a.y, b.y) && segmentPointsAreOnOppositeSitesOfRectangle(a, b)
        }

    private fun segmentPointsAreOnOppositeSitesOfRectangle(a: Coordinates, b: Coordinates): Boolean {
        val minX = min(a.x, b.x)
        val minY = min(a.y, b.y)
        val maxX = max(a.x, b.x)
        val maxY = max(a.y, b.y)
        return min(s.y, t.y) <= minY && max(s.y, t.y) >= maxY ||
                min(s.x, t.x) <= minX && max(s.x, t.x) >= maxX
    }
}

fun List<String>.parseTiles() = map {
    it.split(",")
        .map(String::toInt)
        .let { (x, y) -> Coordinates(x, y) }
}

fun rangeBetween(a: Int, b: Int): IntRange = if (a < b) {
    (a + 1)..<b
} else {
    (b + 1)..<a
}

private fun areaOfRectangle(a: Coordinates, b: Coordinates): Long =
    (abs(a.x - b.x) + 1).toLong() * (abs(a.y - b.y) + 1).toLong()
