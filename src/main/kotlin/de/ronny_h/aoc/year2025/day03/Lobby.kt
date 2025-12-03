package de.ronny_h.aoc.year2025.day03

import de.ronny_h.aoc.AdventOfCode

fun main() = Lobby().run(17324, 171846613143331)

class Lobby : AdventOfCode<Long>(2025, 3) {
    override fun part1(input: List<String>): Long = input
        .parse().sumOf(List<Int>::joltage)

    override fun part2(input: List<String>): Long = input
        .parse().sumOf(List<Int>::joltageWithSafetyOverride)
}

fun List<Int>.joltage(): Long {
    val first = subList(0, lastIndex).max()
    val firstIndex = indexOf(first)
    val second = subList(firstIndex + 1, size).max()
    return "$first$second".toLong()
}

fun List<Int>.joltageWithSafetyOverride(): Long {
    var index = -1
    val batteriesSwitchedOn = mutableListOf<Int>()
    for (remaining in 11 downTo 0) {
        val remainingBatteries = subList(index + 1, size - remaining)
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
