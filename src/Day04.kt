fun main() {

    fun part1(input: List<String>): Int {
        return Grid(input).searchForXMAS()
    }

    printAndCheck(
        part1(
            listOf(
                "..X.......",
                ".SAMX....S",
                ".A..A...A.",
                "XMAS.S.M..",
                ".X....X..."
            )
        ), 5
    )

    val testInput = readInput("Day04_test")
    printAndCheck(part1(testInput), 18)

    val input = readInput("Day04")
    printAndCheck(part1(input), 2500)
}

class Grid(input: List<String>) {

    private val word = "XMAS".toCharArray()
    private val grid = Array(input.size) { CharArray(input[0].length) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char }
        }
    }

    private fun charAt(row: Int, col: Int): Char {
        return grid.getOrNull(row)?.getOrNull(col) ?: ' '
    }

    fun searchForXMAS(): Int {
        var sum = 0
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                sum += searchForXMASAt(row, col)
            }
        }
        return sum
    }

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
            if (charAt(rowOp(row, index), colOp(col, index)) == word[matchIndex]) {
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
}
