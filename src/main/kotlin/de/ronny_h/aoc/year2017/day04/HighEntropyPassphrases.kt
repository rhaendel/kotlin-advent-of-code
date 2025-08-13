package de.ronny_h.aoc.year2017.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.combinations
import de.ronny_h.aoc.extensions.isAnagramOf

fun main() = HighEntropyPassphrases().run(455, 186)

class HighEntropyPassphrases : AdventOfCode<Int>(2017, 4) {
    override fun part1(input: List<String>): Int = input.count { passphrase ->
        val words = passphrase.split(" ")
        words.toSet().size == words.size
    }

    override fun part2(input: List<String>): Int = input.count { passphrase ->
        val words = passphrase.split(" ")
        words.combinations().none { it.first isAnagramOf it.second }
    }
}
