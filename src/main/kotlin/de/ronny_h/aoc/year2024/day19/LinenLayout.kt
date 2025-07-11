package de.ronny_h.aoc.year2024.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.PrefixTree

fun main() = LinenLayout().run(251, 616957151871345)

class LinenLayout : AdventOfCode<Long>(2024, 19) {
    override fun part1(input: List<String>): Long {
        val towels = input.parseTowels()
        val designs = input.parseDesigns()
        println("${towels.size} towels, ${designs.size} designs")

        return designs.count { it.isPossibleWith(towels) }.toLong()
    }

    override fun part2(input: List<String>): Long {
        val towels = input.parseTowels()
        val designs = input.parseDesigns()
        println("${towels.size} towels, ${designs.size} designs")

        return designs.sumOf { it.countPossibilitiesWith(towels) }
    }

    private fun List<String>.parseTowels(): Set<String> = this.first().split(", ").toSet()
    private fun List<String>.parseDesigns(): List<String> = drop(2)

    private fun String.isPossibleWith(towels: Set<String>): Boolean = (this.countPossibilitiesWith(towels) > 0)
    private fun String.countPossibilitiesWith(towels: Set<String>): Long = PrefixTree().insert(this, towels)
}
