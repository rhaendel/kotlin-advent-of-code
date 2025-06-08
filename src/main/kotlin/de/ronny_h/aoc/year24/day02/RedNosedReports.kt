package de.ronny_h.aoc.year24.day02

import de.ronny_h.aoc.AdventOfCode
import kotlin.math.abs

fun main() = RedNosedReports().run(670, 700)

class RedNosedReports : AdventOfCode<Int>(2024, 2) {
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

    override fun part1(input: List<String>) = parseReports(input)
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

    override fun part2(input: List<String>) = parseReports(input)
        .map { isSafeReport(it) || isSafeReportWithOneLevelRemoved(it) }
        .count { it }

}
