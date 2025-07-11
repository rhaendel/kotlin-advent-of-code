package de.ronny_h.aoc.year2024.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = CeresSearch().run(2500, 1933)

class CeresSearch : AdventOfCode<Int>(2024, 4) {
    override fun part1(input: List<String>): Int {
        return XMasGrid(input).searchForXMAS()
    }

    override fun part2(input: List<String>): Int {
        return XMasGrid(input).searchForMASCross()
    }
}

class XMasGrid(input: List<String>) : Grid<Char>(input, ' ') {

    private val word = "XMAS".toCharArray()
    override fun Char.toElementType() = this

    fun searchForXMAS() = forEachIndex(::searchForXMASAt).sum()

    fun searchForMASCross() = forEachIndex(::searchForMASCrossAt).sum()

    private fun searchForXMASAt(row: Int, col: Int): Int {
        var sum = 0
        sum += searchForXMAS(row, col, { a, _ -> a }, { a, b -> a + b }) // →
        sum += searchForXMAS(row, col, { a, _ -> a }, { a, b -> a - b }) // ←
        sum += searchForXMAS(row, col, { a, b -> a + b }, { a, _ -> a }) // ↓
        sum += searchForXMAS(row, col, { a, b -> a - b }, { a, _ -> a }) // ↑
        sum += searchForXMAS(row, col, { a, b -> a + b }, { a, b -> a + b }) // ↘
        sum += searchForXMAS(row, col, { a, b -> a + b }, { a, b -> a - b }) // ↙
        sum += searchForXMAS(row, col, { a, b -> a - b }, { a, b -> a - b }) // ↖
        sum += searchForXMAS(row, col, { a, b -> a - b }, { a, b -> a + b }) // ↗
        return sum
    }

    private fun searchForXMAS(row: Int, col: Int, rowOp: (Int, Int) -> Int, colOp: (Int, Int) -> Int): Int {
        var matchIndex = 0
        for (index in word.indices) {
            if (get(rowOp(row, index), colOp(col, index)) == word[matchIndex]) {
                if (matchIndex == word.lastIndex) {
                    return 1
                }
                matchIndex++
            } else {
                return 0
            }
        }
        return 0
    }

    private fun searchForMASCrossAt(row: Int, col: Int): Int {
        if (get(row, col) != 'A') {
            return 0
        }
        if ((mainDiagIsMAS(row, col) || mainDiagIsSAM(row, col)) &&
            (secDiagIsMAS(row, col) || secDiagIsSAM(row, col))
        ) {
            return 1
        }
        return 0
    }

    private fun mainDiagIsMAS(row: Int, col: Int) = get(row - 1, col - 1) == 'M' && get(row + 1, col + 1) == 'S'

    private fun mainDiagIsSAM(row: Int, col: Int) = get(row - 1, col - 1) == 'S' && get(row + 1, col + 1) == 'M'

    private fun secDiagIsMAS(row: Int, col: Int) = get(row - 1, col + 1) == 'M' && get(row + 1, col - 1) == 'S'

    private fun secDiagIsSAM(row: Int, col: Int) = get(row - 1, col + 1) == 'S' && get(row + 1, col - 1) == 'M'

}
