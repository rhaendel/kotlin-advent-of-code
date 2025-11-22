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

    private fun searchForXMASAt(x: Int, y: Int): Int {
        var sum = 0
        sum += searchForXMAS(x, y, { a, b -> a + b }) { a, _ -> a } // →
        sum += searchForXMAS(x, y, { a, b -> a - b }) { a, _ -> a } // ←
        sum += searchForXMAS(x, y, { a, _ -> a }) { a, b -> a + b } // ↓
        sum += searchForXMAS(x, y, { a, _ -> a }) { a, b -> a - b } // ↑
        sum += searchForXMAS(x, y, { a, b -> a + b }) { a, b -> a + b } // ↘
        sum += searchForXMAS(x, y, { a, b -> a - b }) { a, b -> a + b } // ↙
        sum += searchForXMAS(x, y, { a, b -> a - b }) { a, b -> a - b } // ↖
        sum += searchForXMAS(x, y, { a, b -> a + b }) { a, b -> a - b } // ↗
        return sum
    }

    private fun searchForXMAS(x: Int, y: Int, xOp: (Int, Int) -> Int, yOp: (Int, Int) -> Int): Int {
        var matchIndex = 0
        for (index in word.indices) {
            if (get(xOp(x, index), yOp(y, index)) == word[matchIndex]) {
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

    private fun searchForMASCrossAt(x: Int, y: Int): Int {
        if (get(x, y) != 'A') {
            return 0
        }
        if ((mainDiagIsMAS(x, y) || mainDiagIsSAM(x, y)) &&
            (secDiagIsMAS(x, y) || secDiagIsSAM(x, y))
        ) {
            return 1
        }
        return 0
    }

    private fun mainDiagIsMAS(x: Int, y: Int) = get(x - 1, y - 1) == 'M' && get(x + 1, y + 1) == 'S'

    private fun mainDiagIsSAM(x: Int, y: Int) = get(x - 1, y - 1) == 'S' && get(x + 1, y + 1) == 'M'

    private fun secDiagIsMAS(x: Int, y: Int) = get(x + 1, y - 1) == 'M' && get(x - 1, y + 1) == 'S'

    private fun secDiagIsSAM(x: Int, y: Int) = get(x + 1, y - 1) == 'S' && get(x - 1, y + 1) == 'M'

}
