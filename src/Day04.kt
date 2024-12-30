fun main() {

    fun part1(input: List<String>): Int {
        return Plane(input).search()
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

class Plane(input: List<String>) {

    private val word = "XMAS".toCharArray()

    private val plane = Array(input.size) { CharArray(input[0].length) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> plane[row][col] = char }
        }
    }

    private fun charAt(row: Int, col: Int): Char {
        if (row < 0 || col < 0 || row >= plane.size || col >= plane[0].size) {
            return ' '
        }
        return plane[row][col]
    }

    fun search(): Int {
        var sum = 0
        for (row in plane.indices) {
            for (col in plane[0].indices) {
                sum += searchAt(col, row)
            }
        }
        return sum
    }

    private fun searchAt(col: Int, row: Int): Int {
        var sum = 0
        sum += search(row, col, { a, _ -> a }, { a, b -> a + b }) // →
        sum += search(row, col, { a, _ -> a }, { a, b -> a - b }) // ←
        sum += search(row, col, { a, b -> a + b }, { a, _ -> a }) // ↓
        sum += search(row, col, { a, b -> a - b }, { a, _ -> a }) // ↑
        sum += search(row, col, { a, b -> a + b }, { a, b -> a + b }) // ↘
        sum += search(row, col, { a, b -> a + b }, { a, b -> a - b }) // ↙
        sum += search(row, col, { a, b -> a - b }, { a, b -> a - b }) // ↖
        sum += search(row, col, { a, b -> a - b }, { a, b -> a + b }) // ↗
        return sum
    }

    private fun search(row: Int, col: Int, rowOp: (Int, Int) -> Int, colOp: (Int, Int) -> Int): Int {
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
