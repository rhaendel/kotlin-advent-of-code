import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val teamOne = input
            .filter { it.isNotBlank() }
            .map { it.split("   ").first().toInt() }
            .sorted()
        val teamTwo = input
            .filter { it.isNotBlank() }
            .map { it.split("   ").last().toInt() }
            .sorted()
        val distances = teamOne.zip(teamTwo).map { abs(it.first - it.second) }
        return distances.sum()
    }

    fun part2(input: List<String>): Int {
        val (teamOne, teamTwo) = input
            .filter { it.isNotBlank() }
            .map {
                val pair = it.split("   ")
                pair.first().toInt() to pair.last().toInt()
            }
            .unzip()
        return teamOne.sumOf { one -> one * teamTwo.count { it == one } }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("7   1", "0   0", "1   6")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    check(part2(listOf("7   1", "0   0", "1   6")) == 1)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
