package de.ronny_h.aoc.year2018.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Grid
import de.ronny_h.aoc.extensions.numbers.digit

fun main() = ChronalCharge().run("34,13", "")

class ChronalCharge : AdventOfCode<String>(2018, 11) {
    // start:  11:59
    override fun part1(input: List<String>): String {
        val grid = PowerCellGrid(input.single().toInt())
        return grid.find3x3SquareWithLargestTotal()
    }

    override fun part2(input: List<String>): String {
        return ""
    }
}

class PowerCellGrid(serialNumber: Int) : Grid<Int>(300, 300, -1000) {
    init {
        forEachIndex { y, x ->
            val rackId = x + 10
            var powerLevel = rackId * y + serialNumber
            powerLevel *= rackId
            val hundredsDigit = powerLevel.digit(3)
            this[y, x] = hundredsDigit - 5
        }.last()
    }

    fun find3x3SquareWithLargestTotal(): String {
        val maxCoordinates = forEachCoordinates { coordinates, _ ->
            val total = (0..2).sumOf { i ->
                (0..2).sumOf { j ->
                    this[coordinates.row + i, coordinates.col + j]
                }
            }
            coordinates to total
        }
            .maxBy { it.second }
            .first
        return "${maxCoordinates.col},${maxCoordinates.row}"  // X,Y
    }

    override fun Char.toElementType(): Int = digitToInt()
}
