package de.ronny_h.aoc.year2025.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2025.day04.PaperGrid.Companion.EMPTY

fun main() = PrintingDepartment().run(1397, 8758)

class PrintingDepartment : AdventOfCode<Int>(2025, 4) {
    override fun part1(input: List<String>): Int = PaperGrid(input).accessiblePaperRollsCoordinates().count()

    override fun part2(input: List<String>): Int {
        val grid = PaperGrid(input)
        var total = 0
        while (true) {
            grid.accessiblePaperRollsCoordinates().toList().apply {
                if (isEmpty()) return total
                forEach { grid[it] = EMPTY }
                total += size
            }
        }
    }
}

class PaperGrid(input: List<String>) : SimpleCharGrid(input, EMPTY) {

    companion object {
        private const val PAPER = '@'
        const val EMPTY = '.'
    }

    fun accessiblePaperRollsCoordinates() = forEachCoordinates { position, element ->
        if (element == PAPER && position.neighboursIncludingDiagonals().count { this[it] == PAPER } < 4) {
            position
        } else {
            null
        }
    }.filterNotNull()
}
