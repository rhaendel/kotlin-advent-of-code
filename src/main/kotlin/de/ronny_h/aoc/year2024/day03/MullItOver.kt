package de.ronny_h.aoc.year2024.day03

import de.ronny_h.aoc.AdventOfCode
import kotlin.text.get

fun main() = MullItOver().run(174960292, 56275602)

class MullItOver : AdventOfCode<Int>(2024, 3) {
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

    override fun part1(input: List<String>): Int {
        return input.sumOf(::findAndMultiply)
    }

    override fun part2(input: List<String>): Int {
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
