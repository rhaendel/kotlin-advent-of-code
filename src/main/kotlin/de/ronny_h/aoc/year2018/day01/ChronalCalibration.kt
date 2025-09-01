package de.ronny_h.aoc.year2018.day01

import de.ronny_h.aoc.AdventOfCode

fun main() = ChronalCalibration().run(500, 709)

class ChronalCalibration : AdventOfCode<Int>(2018, 1) {
    override fun part1(input: List<String>): Int = input.toListOfInt().sum()

    private fun List<String>.toListOfInt(): List<Int> = map {
        if (it.startsWith('+')) it.substring(1).toInt() else it.toInt()
    }

    override fun part2(input: List<String>): Int {
        val seenFrequencies = mutableSetOf<Int>()
        return generateSequence { input.toListOfInt() }
            .flatten()
            .runningFold(0) { frequency, change -> frequency + change }
            .find { !seenFrequencies.add(it) }!!
    }
}
