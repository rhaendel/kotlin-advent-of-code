package de.ronny_h.aoc.year2015.day08

import de.ronny_h.aoc.AdventOfCode

fun main() = Matchsticks().run(1350, 2085)

class Matchsticks : AdventOfCode<Int>(2015, 8) {
    override fun part1(input: List<String>): Int =
        input.sumOf { numberOfCharactersOfCode(it) - numberOfCharactersInMemory(it) }

    override fun part2(input: List<String>): Int =
        input.sumOf { numberOfCharactersEncoded(it) - numberOfCharactersOfCode(it) }

    fun numberOfCharactersOfCode(string: String) = string.length
    fun numberOfCharactersInMemory(string: String) = string.unescape().length
    fun numberOfCharactersEncoded(string: String) = string.length +
            string.count { it == '"' } +
            string.count { it == '\\' } +
            2
}

private val startQuotationRegex = "^\"".toRegex()
private val endQuotationRegex = "\"$".toRegex()
private val asciiCharRegex = "\\\\x[0-9A-Fa-f][0-9A-Fa-f]".toRegex()

fun String.unescape(): String = replace(startQuotationRegex, "")
    .replace(endQuotationRegex, "")
    .replace(asciiCharRegex, """_""")
    .replace("""\"""", """"""")
    .replace("""\\""", """\""")
