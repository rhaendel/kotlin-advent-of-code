import de.ronny_h.extensions.PrefixTree

fun main() {
    val day = "Day19"

    fun part1(input: List<String>): Int {
        val towels = input.first().split(", ")
        val designs = input.drop(2)

        println("${towels.size} towels, ${designs.size} designs")

        return designs.count { it.isPossibleWith(towels) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    println("$day part 1")

    val testInput = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.trimIndent().split('\n')
    printAndCheck(testInput, ::part1, 6)

    val input = readInput(day)
    printAndCheck(input, ::part1, 251)


    println("$day part 2")

    printAndCheck(testInput, ::part2, 0)
    printAndCheck(input, ::part2, 0)
}

private fun String.isPossibleWith(towels: List<String>) = PrefixTree().insert(this, towels)
