import kotlin.math.pow

fun main() {
    val day = "Day07"

    println("$day part 1")

    fun parseEquations(input: List<String>) = input.map { line ->
        val (result, numbers) = line.split(": ")
        Equation(result.toLong(), numbers.split(" ").map(String::toLong))
    }

    fun part1(input: List<String>) = parseEquations(input)
        .map { it.checkSolvability(listOf(Long::plus, Long::times)) }
        .filter { it.solvable }
        .sumOf { it.result }

    printAndCheck(
        """
            190: 10 19
            3267: 81 40 27
            292: 11 6 16 20
        """.trimIndent().lines(),
        ::part1, 3749
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 3749)

    val input = readInput(day)
    printAndCheck(input, ::part1, 1399219271639)
}

private data class Equation(val result: Long, val numbers: List<Long>, var solvable: Boolean = false) {

    fun checkSolvability(operators: List<(Long, Long) -> Long>): Equation {
        val sequence = operatorIndexSequenceFor(numbers.size - 1).iterator()
        while (sequence.hasNext()) {
            val result = numbers.drop(1).fold(numbers.first()) { acc, number ->
                operators[sequence.next()](acc, number)
            }
            if (result == this.result) {
                solvable = true
                return this
            }
        }
        return this
    }

    /**
     * Returns a sequence of `0`s and `1`s corresponding to the 0-padded binary
     * representation of a counter value starting at 0.
     *
     * Examples:
     * - `operatorIndexSequenceFor(1)` returns `[0,1]`
     * - `operatorIndexSequenceFor(2)` returns `[0,0,0,1,1,0,1,1]` (counting from 0 `00` to 4 `11`)
     */
    private fun operatorIndexSequenceFor(digits: Int) = sequence {
        for (count in 0..<(2.0.pow(digits).toInt())) {
            val binary = count.toString(2).fillLeadingZeros(digits)
            for (i in 0..<digits) {
                yield(binary[i].digitToInt())
            }
        }
    }

    private fun String.fillLeadingZeros(digits: Int) = "0".repeat(digits - length) + this

}
