import de.ronny_h.extensions.Grid

fun main() {
    val day = "Day04"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        return XMasGrid(input).searchForXMAS()
    }

    printAndCheck(
        listOf(
            "..X.......",
            ".SAMX....S",
            ".A..A...A.",
            "XMAS.S.M..",
            ".X....X..."
        ),
        ::part1, 5
    )

    val test1Input = readInput("${day}_test1")
    printAndCheck(test1Input, ::part1, 18)

    val input = readInput(day)
    printAndCheck(input, ::part1, 2500)


    println("$day part 2")

    fun part2(input: List<String>): Int {
        return XMasGrid(input).searchForMASCross()
    }

    printAndCheck(
        listOf(
            "M.S",
            ".A.",
            "M.S"
        ),
        ::part2, 1
    )

    val test2Input = readInput("${day}_test2")
    printAndCheck(test2Input, ::part2, 9)
    printAndCheck(input, ::part2, 1933)
}

class XMasGrid(input: List<String>) : Grid(input) {

    private val word = "XMAS".toCharArray()
    override val nullElement = ' '

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
            (secDiagIsMAS(row, col) || secDiagIsSAM(row, col))
        ) {
            return 1
        }
        return 0
    }

    private fun mainDiagIsMAS(row: Int, col: Int) = charAt(row - 1, col - 1) == 'M' && charAt(row + 1, col + 1) == 'S'

    private fun mainDiagIsSAM(row: Int, col: Int) = charAt(row - 1, col - 1) == 'S' && charAt(row + 1, col + 1) == 'M'

    private fun secDiagIsMAS(row: Int, col: Int) = charAt(row - 1, col + 1) == 'M' && charAt(row + 1, col - 1) == 'S'

    private fun secDiagIsSAM(row: Int, col: Int) = charAt(row - 1, col + 1) == 'S' && charAt(row + 1, col - 1) == 'M'

}
