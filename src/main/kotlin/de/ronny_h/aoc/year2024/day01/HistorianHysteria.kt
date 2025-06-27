package de.ronny_h.aoc.year2024.day01

import de.ronny_h.aoc.AdventOfCode
import kotlin.math.abs

fun main() = HistorianHysteria().run(3714264, 18805872)

class HistorianHysteria() : AdventOfCode<Int>(2024, 1) {
    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Int {
        val (teamOne, teamTwo) = input
            .filter { it.isNotBlank() }
            .map {
                val pair = it.split("   ")
                pair.first().toInt() to pair.last().toInt()
            }
            .unzip()
        return teamOne.sumOf { one -> one * teamTwo.count { it == one } }
    }
}
