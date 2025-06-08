package de.ronny_h.aoc.year24.day01

import de.ronny_h.aoc.extensions.printAndCheck
import de.ronny_h.aoc.extensions.readInput
import kotlin.math.abs

fun main() {
    val day = "Day01"
    val input = readInput(day)
    val historianHysteria = HistorianHysteria()

    println("$day part 1")
    printAndCheck(input, historianHysteria::part1, 3714264)

    println("$day part 2")
    printAndCheck(input, historianHysteria::part2, 18805872)
}

class HistorianHysteria {
    fun part1(input: List<String>): Int {
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

    fun part2(input: List<String>): Int {
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
