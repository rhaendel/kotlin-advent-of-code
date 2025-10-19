package de.ronny_h.aoc.year2018.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid
import de.ronny_h.aoc.extensions.numbers.digit

fun main() = ChronalCharge().run("34,13", "")

class ChronalCharge : AdventOfCode<String>(2018, 11) {
    // start:  11:59
    override fun part1(input: List<String>): String =
        PowerCellGrid(input.single().toInt()).find3x3SquareWithLargestTotal()

    override fun part2(input: List<String>): String =
        PowerCellGrid(input.single().toInt()).findSquareWithLargestTotal()
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

    fun findSquareWithLargestTotal(): String {
        val max = (1..300)
            .map { findSquareWithLargestTotalForSize(it) }
            .maxBy { it.total }
        return "${max.coordinates.col},${max.coordinates.row},${max.size}"  // X,Y,size
    }

    fun find3x3SquareWithLargestTotal(): String {
        val maxCoordinates = findSquareWithLargestTotalForSize(3).coordinates
        return "${maxCoordinates.col},${maxCoordinates.row}"  // X,Y
    }

    private data class Square(val coordinates: Coordinates, val size: Int, val total: Int)

    private fun findSquareWithLargestTotalForSize(squareSize: Int): Square =
        forEachCoordinates { coordinates, _ ->
            val total = (0..<squareSize).sumOf { i ->
                (0..<squareSize).sumOf { j ->
                    this[coordinates.row + i, coordinates.col + j]
                }
            }
            Square(coordinates, squareSize, total)
        }
            .maxBy { it.total }


    override fun Char.toElementType(): Int = digitToInt()
}
