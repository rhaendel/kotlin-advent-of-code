package de.ronny_h.aoc.year2017.day02

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.combinations

fun main() = CorruptionChecksum().run(44216, 320)

class CorruptionChecksum : AdventOfCode<Int>(2017, 2) {
    override fun part1(input: List<String>): Int = input.sumOf {
        val ints = it.parseInts()
        ints.max() - ints.min()
    }

    override fun part2(input: List<String>): Int = input.sumOf {
        it.parseInts()
            .combinations()
            .forEach { (a, b) ->
                if (a % b == 0) {
                    return@sumOf a / b
                }
            }
            .let { return 0 }
    }

    private fun String.parseInts(): List<Int> = split(" ", "\t").map { it.toInt() }
}
