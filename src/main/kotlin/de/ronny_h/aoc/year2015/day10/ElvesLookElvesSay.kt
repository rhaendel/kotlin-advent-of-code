package de.ronny_h.aoc.year2015.day10

import de.ronny_h.aoc.AdventOfCode

fun main() = ElvesLookElvesSay().run(329356, 4666278)

class ElvesLookElvesSay : AdventOfCode<Int>(2015, 10) {

    override fun part1(input: List<String>) = input.first().lookAndSay(40)
    override fun part2(input: List<String>) = input.first().lookAndSay(50)

    private fun String.lookAndSay(times: Int): Int {
        var sequence = this
        repeat(times) {
            sequence = lookAndSay(sequence)
        }
        return sequence.length
    }

    fun lookAndSay(sequence: String): String {
        var char = sequence.first()
        var count = 0
        return sequence.map {
            if (it == char) {
                count++
                ""
            } else {
                val s = "$count$char"
                char = it
                count = 1
                s
            }
        }.joinToString("") + "$count$char"
    }
}
