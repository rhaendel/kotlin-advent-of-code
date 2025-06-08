package de.ronny_h.aoc.year24.day19

import de.ronny_h.aoc.extensions.PrefixTree
import de.ronny_h.aoc.extensions.printAndCheck
import de.ronny_h.aoc.extensions.readInput

fun main() {
    val day = "Day19"
    val input = readInput(day)
    val linenLayout = LinenLayout()

    println("$day part 1")
    printAndCheck(input, linenLayout::part1, 251)

    println("$day part 2")
    printAndCheck(input, linenLayout::part2, 616957151871345)
}

class LinenLayout {
    fun part1(input: List<String>): Int {
        val towels = input.parseTowels()
        val designs = input.parseDesigns()
        println("${towels.size} towels, ${designs.size} designs")

        return designs.count { it.isPossibleWith(towels) }
    }

    fun part2(input: List<String>): Long {
        val towels = input.parseTowels()
        val designs = input.parseDesigns()
        println("${towels.size} towels, ${designs.size} designs")

        return designs.sumOf { it.countPossibilitiesWith(towels) }
    }

    private fun List<String>.parseTowels(): List<String> = this.first().split(", ")
    private fun List<String>.parseDesigns(): List<String> = drop(2)

    private fun String.isPossibleWith(towels: List<String>): Boolean = (this.countPossibilitiesWith(towels) > 0)
    private fun String.countPossibilitiesWith(towels: List<String>): Long = PrefixTree().insert(this, towels)
}
