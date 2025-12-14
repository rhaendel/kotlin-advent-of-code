package de.ronny_h.aoc.year2025.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.animation.gray
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2025.day04.PaperGrid.Companion.EMPTY
import de.ronny_h.aoc.year2025.day04.PaperGrid.Companion.PAPER
import java.awt.Color.white

fun main() = PrintingDepartment().run(1397, 8758)

class PrintingDepartment : AdventOfCode<Int>(2025, 4) {
    override fun part1(input: List<String>): Int = PaperGrid(input).accessiblePaperRollsCoordinates().count()

    // find an animation at: /animations/2025-04_PrintingDepartment.gif
    override fun part2(input: List<String>): Int {
        val grid = PaperGrid(input)
//        grid.recorder = AnimationRecorder()
        val result = grid.removeAllAccessiblePaperRolls()
        grid.recorder?.saveTo(
            "animations/$year-${paddedDay()}_${javaClass.simpleName}.gif",
            mapOf(
                PAPER to white,
                EMPTY to gray,
            ),
        )
        return result
    }

    private fun PaperGrid.removeAllAccessiblePaperRolls(): Int {
        var total = 0
        while (true) {
            accessiblePaperRollsCoordinates().toList().apply {
                if (isEmpty()) return total
                forEach { set(it, EMPTY) }
                total += size
            }
        }
    }
}

class PaperGrid(input: List<String>) : SimpleCharGrid(input, EMPTY) {

    companion object {
        const val PAPER = '@'
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
