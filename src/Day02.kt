import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val reports = input
            .filter { it.isNotBlank() }
            .map { line ->
                line.trim()
                    .split(" ")
                    .map { it.toInt() }
            }

        val allDifferences = reports.map {
            it.windowed(2).map { (a, b) -> b-a }
        }

        val reportSafeties = allDifferences.map { diffs ->
            diffs.fold(true to diffs.first()) { acc, diff ->
                if (abs(diff) !in  1..3) {
                    // difference too big or equal values following each other
                    false to diff
                } else if (acc.second * diff < 0) {
                    // change of sign -> not all either increasing or decreasing
                    false to diff
                } else {
                    acc.first to diff
                }
            }
        }

        return reportSafeties.count { it.first }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5")) == 2)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
}
