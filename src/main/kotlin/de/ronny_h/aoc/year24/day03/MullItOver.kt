package de.ronny_h.aoc.year24.day03

import de.ronny_h.aoc.extensions.printAndCheck
import de.ronny_h.aoc.extensions.readInput
import kotlin.text.get

fun main() {
    val day = "Day03"
    val input = readInput(day)
    val mullItOver = MullItOver()

    println("$day part 1")
    printAndCheck(input, mullItOver::part1, 174960292)

    println("$day part 2")
    printAndCheck(input, mullItOver::part2, 56275602)
}

class MullItOver {
    private val mulRegex = """(?<mul>mul\((\d{1,3}),(\d{1,3})\))"""
    private val doRegex = """(?<do>do\(\))"""
    private val dontRegex = """(?<dont>don't\(\))"""

    private fun multiply(match: MatchResult): Int {
        val (_, left, right) = match.destructured
        return left.toInt() * right.toInt()
    }

    private fun findAndMultiply(line: String) = mulRegex
        .toRegex()
        .findAll(line)
        .sumOf(::multiply)

    fun part1(input: List<String>): Int {
        return input.sumOf(::findAndMultiply)
    }

    fun part2(input: List<String>): Int {
        var doIt = true

        return input
            .flatMap { line ->
                "$mulRegex|$doRegex|$dontRegex"
                    .toRegex()
                    .findAll(line)
            }.sumOf { match ->
                match.groups["do"]?.let { doIt = true }
                match.groups["dont"]?.let { doIt = false }
                match.groups["mul"]?.let { if (doIt) multiply(match) else 0 } ?: 0
            }
    }
}
