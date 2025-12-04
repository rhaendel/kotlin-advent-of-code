package de.ronny_h.aoc.year2025.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2025.day04.PaperGrid.Companion.EMPTY
import de.ronny_h.aoc.year2025.day04.PaperGrid.Companion.MARKED_PAPER

fun main() = PrintingDepartment().run(1397, 8758)

class PrintingDepartment : AdventOfCode<Int>(2025, 4) {
    override fun part1(input: List<String>): Int = PaperGrid(input).countAndMarkAccessiblePaperRolls()

    override fun part2(input: List<String>): Int {
        var paperGrid = PaperGrid(input)
        var total = 0
        var accessiblePaperRolls = paperGrid.countAndMarkAccessiblePaperRolls()
        while (accessiblePaperRolls > 0) {
            total += accessiblePaperRolls
            paperGrid = paperGrid.copyReplacing(MARKED_PAPER, EMPTY)
            accessiblePaperRolls = paperGrid.countAndMarkAccessiblePaperRolls()
        }
        return total
    }

}

class PaperGrid(input: List<String>) : SimpleCharGrid(input, EMPTY) {

    companion object {
        private const val PAPER = '@'
        const val MARKED_PAPER = 'x'
        const val EMPTY = '.'
    }

    fun countAndMarkAccessiblePaperRolls() =
        forEachCoordinates { position, element ->
            if (element.isAPaperRoll() && position.neighboursIncludingDiagonals()
                    .count { this[it].isAPaperRoll() } < 4
            ) {
                this[position] = MARKED_PAPER
                1
            } else {
                0
            }
        }.sum()

    fun copyReplacing(oldElement: Char, newElement: Char): PaperGrid {
        val newGridString = toString().replace(oldElement, newElement)
        return PaperGrid(newGridString.asList())
    }

    private fun Char.isAPaperRoll(): Boolean = this == PAPER || this == MARKED_PAPER
}
