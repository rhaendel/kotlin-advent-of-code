package de.ronny_h.aoc.year2025.day03

import de.ronny_h.aoc.AdventOfCode

fun main() = Lobby().run(17324, 171846613143331)

class Lobby : AdventOfCode<Long>(2025, 3) {
    override fun part1(input: List<String>): Long = input
        .parse().sumOf { it.joltage(2) }

    override fun part2(input: List<String>): Long = input
        .parse().sumOf { it.joltage(12) }
}

fun List<Int>.joltage(numberOfBatteries: Int): Long {
    var index = 0
    val batteriesSwitchedOn = mutableListOf<Int>()
    for (remaining in numberOfBatteries - 1 downTo 0) {
        val remainingBatteries = subList(index, size - remaining)
        val chosenBattery = remainingBatteries.max()
        batteriesSwitchedOn.add(chosenBattery)
        index += 1 + remainingBatteries.indexOf(chosenBattery)
    }
    return batteriesSwitchedOn.joinToString("").toLong()
}

fun List<String>.parse() = map { bank ->
    bank.map {
        it.digitToIntOrNull() ?: error("should never be null")
    }
}
