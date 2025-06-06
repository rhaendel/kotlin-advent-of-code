package de.ronny_h.aoc.year24.day02

import printAndCheck
import readInput
import kotlin.math.abs

fun main() {
    val day = "Day02"

    fun parseReports(input: List<String>) = input
        .filter { it.isNotBlank() }
        .map { line ->
            line.trim()
                .split(" ")
                .map { it.toInt() }
        }

    fun calcDifferences(report: List<Int>) = report.zipWithNext { a, b -> b - a }

    fun isSafeReport(reports: List<Int>): Boolean {
        val diffs = calcDifferences(reports)
        return diffs.fold(true to diffs.first()) { acc, diff ->
            if (abs(diff) !in 1..3) {
                // difference too big or equal values following each other
                false to diff
            } else if (acc.second * diff < 0) {
                // change of sign -> not all either increasing or decreasing
                false to diff
            } else {
                acc.first to diff
            }
        }.component1()
    }

    fun part1(input: List<String>) = parseReports(input)
        .map(::isSafeReport)
        .count { it }

    fun isSafeReportWithOneLevelRemoved(report: List<Int>): Boolean {
        for (i in report.indices) {
            val dampened = report.toMutableList().apply { removeAt(i) }
            if (isSafeReport(dampened)) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>) = parseReports(input)
        .map { isSafeReport(it) || isSafeReportWithOneLevelRemoved(it) }
        .count { it }

    println("$day part 1")

    printAndCheck(
        listOf("5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5"),
        ::part1, 2
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 2)

    val input = readInput(day)
    printAndCheck(input, ::part1, 670)


    println("$day part 2")

    printAndCheck(listOf("1 2 6 7", "5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5"), ::part2, 5)
    printAndCheck(testInput, ::part2, 4)
    printAndCheck(input, ::part2, 700)
}
