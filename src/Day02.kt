import kotlin.math.abs

fun main() {
    fun parseReports(input: List<String>) = input
        .filter { it.isNotBlank() }
        .map { line ->
            line.trim()
                .split(" ")
                .map { it.toInt() }
        }

    fun calcDifferences(report: List<Int>) = report.zipWithNext { a, b -> b - a }

    fun isSafeReport(reports: List<Int>): Boolean {
        val diffs = calcDifferences(reports)
        return diffs.fold(true to diffs.first()) { acc, diff ->
            if (abs(diff) !in 1..3) {
                // difference too big or equal values following each other
                false to diff
            } else if (acc.second * diff < 0) {
                // change of sign -> not all either increasing or decreasing
                false to diff
            } else {
                acc.first to diff
            }
        }.component1()
    }

    fun part1(input: List<String>) = parseReports(input)
        .map(::isSafeReport)
        .count { it }

    fun isSafeReportWithOneLevelRemoved(report: List<Int>): Boolean {
        for (i in report.indices) {
            val dampened = report.toMutableList().apply { removeAt(i) }
            if (isSafeReport(dampened)) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>) = parseReports(input)
        .map { isSafeReport(it) || isSafeReportWithOneLevelRemoved(it) }
        .count { it }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5")) == 2)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)

    check(part2(listOf("1 2 6 7", "5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5")) == 5)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
