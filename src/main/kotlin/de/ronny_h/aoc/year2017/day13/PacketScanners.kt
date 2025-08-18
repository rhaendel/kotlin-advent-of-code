package de.ronny_h.aoc.year2017.day13

import de.ronny_h.aoc.AdventOfCode

fun main() = PacketScanners().run(1612, 3907994)

class PacketScanners : AdventOfCode<Int>(2017, 13) {
    override fun part1(input: List<String>): Int = input
        .parseFirewallConfig()
        .sumOf(::severity)

    private fun severity(layer: ScanningArea): Int =
        if (layer.isScannerOnTopInPicosecond(layer.depth)) {
            layer.severity()
        } else {
            0
        }

    override fun part2(input: List<String>): Int {
        val config = input
            .parseFirewallConfig()

        return generateSequence(0) { it + 1 }
            .map { delay ->
                delay to config.any { it.isScannerOnTopInPicosecond(it.depth + delay) }
            }
            .first { !it.second }
            .first
    }
}

data class ScanningArea(val depth: Int, val range: Int) {
    fun isScannerOnTopInPicosecond(ps: Int): Boolean = ps % ((range - 1) * 2) == 0
    fun severity() = depth * range
}

fun List<String>.parseFirewallConfig() = map {
    val (depth, range) = it.split(": ")
    ScanningArea(depth.toInt(), range.toInt())
}
