package de.ronny_h.aoc.year2018.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid
import de.ronny_h.aoc.extensions.numbers.digit

fun main() = ChronalCharge().run("34,13", "280,218,11")

class ChronalCharge : AdventOfCode<String>(2018, 11) {
    override fun part1(input: List<String>): String =
        PowerCellGrid(input.single().toInt()).find3x3SquareWithLargestTotal()

    override fun part2(input: List<String>): String =
        PowerCellGrid(input.single().toInt()).findSquareWithLargestTotal()
}

class PowerCellGrid(serialNumber: Int) : Grid<Int>(300, 300, -1000) {
    private val cache = mutableMapOf<Configuration, Int>()

    init {
        forEachIndex { y, x ->
            val rackId = x + 10
            val powerLevel = rackId * y + serialNumber
            val hundredsDigit = (powerLevel * rackId).digit(3)
            this[y, x] = hundredsDigit - 5
            cache[Configuration(Coordinates(y, x), 1)] = this[y, x]
        }.last()
    }

    fun findSquareWithLargestTotal(): String {
        val max = (1..300)
            .map {
                if (it % 10 == 0) println("calc for square size $it")
                findSquareWithLargestTotalForSize(it)
            }
            .maxBy { it.total }
            .configuration
        return "${max.coordinates.col},${max.coordinates.row},${max.size}"  // X,Y,size
    }

    fun find3x3SquareWithLargestTotal(): String {
        val maxCoordinates = findSquareWithLargestTotalForSize(3).configuration.coordinates
        return "${maxCoordinates.col},${maxCoordinates.row}"  // X,Y
    }

    private data class Configuration(val coordinates: Coordinates, val size: Int)
    private data class Square(val configuration: Configuration, val total: Int)

    private fun findSquareWithLargestTotalForSize(squareSize: Int): Square =
        forEachCoordinates(width - squareSize) { coordinates ->
            val config = Configuration(coordinates, squareSize)
            val total = if (squareSize == 1) {
                cache.getValue(config)
            } else {
                val total = cache[Configuration(coordinates, squareSize - 1)]?.let { cached ->
                    val additionalColumnTotal =
                        (0..<squareSize).sumOf { this[coordinates.row + it, coordinates.col + squareSize - 1] }
                    val additionalRowTotal =
                        (0..<squareSize - 1).sumOf { this[coordinates.row + squareSize - 1, coordinates.col + it] }
                    cached + additionalColumnTotal + additionalRowTotal
                } ?: sumOfSquare(coordinates, squareSize)
                cache[config] = total
                total
            }
            Square(config, total)
        }
            .maxBy { it.total }

    private fun <R> forEachCoordinates(upperIndex: Int, action: (position: Coordinates) -> R): Sequence<R> = sequence {
        for (row in 0..upperIndex) {
            for (col in 0..upperIndex) {
                yield(action(Coordinates(row, col)))
            }
        }
    }

    private fun sumOfSquare(coordinates: Coordinates, squareSize: Int): Int = (0..<squareSize).sumOf { r ->
        (0..<squareSize).sumOf { c ->
            this[coordinates.row + r, coordinates.col + c]
        }
    }

    override fun Char.toElementType(): Int = digitToInt()
}
