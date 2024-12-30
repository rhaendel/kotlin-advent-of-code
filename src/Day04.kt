fun main() {

    fun part1(input: List<String>): Int {
        val plane = Plane(input)
        val sum = plane.search()
        return sum
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

    private val width: Int = input[0].length
    private val height: Int = input.size
    private val plane = Array(width) { CharArray(height) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> plane[col][row] = char }
        }
    }

    private fun charAt(col: Int, row: Int): Char {
        if (col < 0 || row < 0 || col >= width || row >= height) {
            return ' '
        }
        return plane[col][row]
    }

    fun search(): Int {
        var sum = 0
        for (row in 0..<height) {
            for (col in 0..<width) {
                sum += searchAt(col, row)
            }
        }
        return sum
    }

    private fun searchAt(col: Int, row: Int): Int {
        var sum = 0
        sum += search(col, { a, b -> a + b }, row, { a, _ -> a }) // →
        sum += search(col, { a, b -> a - b }, row, { a, _ -> a }) // ←
        sum += search(col, { a, _ -> a }, row, { a, b -> a + b }) // ↓
        sum += search(col, { a, _ -> a }, row, { a, b -> a - b }) // ↑
        sum += search(col, { a, b -> a + b }, row, { a, b -> a + b }) // ↘
        sum += search(col, { a, b -> a - b }, row, { a, b -> a + b }) // ↙
        sum += search(col, { a, b -> a - b }, row, { a, b -> a - b }) // ↖
        sum += search(col, { a, b -> a + b }, row, { a, b -> a - b }) // ↗
        return sum
    }

    private fun search(col: Int, colOp: (Int, Int) -> Int, row: Int, rowOp: (Int, Int) -> Int): Int {
        var matchIndex = 0
        for (index in word.indices) {
            if (charAt(colOp(col, index), rowOp(row, index)) == word[matchIndex]) {
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
