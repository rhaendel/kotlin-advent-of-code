package de.ronny_h.aoc.year2017.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2017.day11.HexagonalGrid.Direction.*
import kotlin.math.abs
import kotlin.math.max

fun main() = HexEd().run(812, 1603)

class HexEd : AdventOfCode<Int>(2017, 11) {
    override fun part1(input: List<String>): Int {
        val hexgrid = HexagonalGrid()
        input.single()
            .parseDirections()
            .forEach { hexgrid.goInDirection(it) }
        return hexgrid.start.distanceTo(hexgrid.currentPosition)
    }

    override fun part2(input: List<String>): Int {
        val hexgrid = HexagonalGrid()
        return input.single()
            .parseDirections()
            .maxOf {
                hexgrid.goInDirection(it)
                hexgrid.start distanceTo hexgrid.currentPosition
            }
    }
}

fun String.parseDirections(): List<HexagonalGrid.Direction> = split(",").map {
    when (it) {
        "n" -> NORTH
        "ne" -> NORTH_EAST
        "se" -> SOUTH_EAST
        "s" -> SOUTH
        "sw" -> SOUTH_WEST
        "nw" -> NORTH_WEST
        else -> error("illegal direction: $it")
    }
}

/**
 * We define coordinates for the hexagonal grid with columns and rows where columns that contain only even rows
 * alternate with columns containing only odd rows.
 *
 * *Example:* From a hextile in the middle with coordinates (0,0), the adjacent hextiles' coordinates are:
 * ```
 *    +-+ -2,0 +-+
 *       \ n  /
 *  -1,-1 +--+ -1,1
 *    nw /    \ ne
 *    +-+ 0,0  +-+
 *    sw \    / se
 *   1,-1 +--+ 1,1
 *       / s  \
 *    +-+ 2,0  +-+
 * ```
 * */
class HexagonalGrid {

    data class Hextile(val row: Int, val col: Int) {

        init {
            check(if (abs(col) % 2 == 0) abs(row) % 2 == 0 else true) { "Invalid Hextile($row, $col): In even columns only even row numbers are allowed." }
            check(if (abs(col) % 2 == 1) abs(row) % 2 == 1 else true) { "Invalid Hextile($row, $col): In odd columns only odd row numbers are allowed." }
        }

        operator fun plus(direction: Direction) = Hextile(row + direction.row, col + direction.col)

        infix fun distanceTo(other: Hextile): Int {
            // if hextiles would be in the same col:
            val verticalDistance = abs((row - other.row) / 2)
            // if hextiles would be in the "same" row:
            val horizontalDistance = abs(col - other.col)
            // in different rows/columns, per column, a distance of 1 in vertical direction can be made up
            return horizontalDistance + max(verticalDistance - max(horizontalDistance - verticalDistance, 0), 0)
        }
    }

    enum class Direction(val row: Int, val col: Int) {
        NORTH(-2, 0),
        NORTH_EAST(-1, +1),
        SOUTH_EAST(+1, +1),
        SOUTH(+2, 0),
        SOUTH_WEST(+1, -1),
        NORTH_WEST(-1, -1),
    }

    val start = Hextile(0, 0)
    var currentPosition = start

    fun goInDirection(direction: Direction) {
        currentPosition += direction
    }
}
