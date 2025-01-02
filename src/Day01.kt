import kotlin.math.abs

fun main() {
    val day = "Day01"

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

    println("$day part 1")

    printAndCheck(
        listOf(
            "7   1",
            "0   0",
            "1   6"
        ), ::part1, 1
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 11)

    val input = readInput(day)
    printAndCheck(input, ::part1, 3714264)


    println("$day part 2")

    printAndCheck(
        listOf(
            "7   1",
            "0   0",
            "1   6"
        ), ::part2, 1
    )
    printAndCheck(testInput, ::part2, 31)
    printAndCheck(input, ::part2, 18805872)
}
