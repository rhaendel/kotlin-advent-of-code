fun main() {
    println("part 1")

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

    val test1Input = readInput("Day04_test1")
    printAndCheck(part1(test1Input), 18)

    val input = readInput("Day04")
    printAndCheck(part1(input), 2500)


    println("part 2")

    fun part2(input: List<String>): Int {
        return Grid(input).searchForMASCross()
    }

    printAndCheck(
        part2(
            listOf(
                "M.S",
                ".A.",
                "M.S"
            )
        ), 1
    )

    val test2Input = readInput("Day04_test2")
    printAndCheck(part2(test2Input), 9)
    printAndCheck(part2(input), 1933)
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

    fun searchForXMAS() = sumForEachInGrid(::searchForXMASAt)

    fun searchForMASCross() = sumForEachInGrid(::searchForMASCrossAt)

    private fun sumForEachInGrid(countAt: (Int, Int) -> Int): Int {
        var sum = 0
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                sum += countAt(row, col)
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

    private fun searchForMASCrossAt(row: Int, col: Int): Int {
        if (charAt(row, col) != 'A') {
            return 0
        }
        if ((mainDiagIsMAS(row, col) || mainDiagIsSAM(row, col)) &&
            (secDiagIsMAS(row, col) || secDiagIsSAM(row, col))) {
            return 1
        }
        return 0
    }

    private fun mainDiagIsMAS(row: Int, col: Int) = charAt(row - 1, col - 1) == 'M' && charAt(row + 1, col + 1) == 'S'

    private fun mainDiagIsSAM(row: Int, col: Int) = charAt(row - 1, col - 1) == 'S' && charAt(row + 1, col + 1) == 'M'

    private fun secDiagIsMAS(row: Int, col: Int) = charAt(row - 1, col + 1) == 'M' && charAt(row + 1, col - 1) == 'S'

    private fun secDiagIsSAM(row: Int, col: Int) = charAt(row - 1, col + 1) == 'S' && charAt(row + 1, col - 1) == 'M'

}
