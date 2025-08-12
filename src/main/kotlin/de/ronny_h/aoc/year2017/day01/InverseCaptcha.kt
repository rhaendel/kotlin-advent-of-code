package de.ronny_h.aoc.year2017.day01

import de.ronny_h.aoc.AdventOfCode

fun main() = InverseCaptcha().run(1343, 1274)

class InverseCaptcha : AdventOfCode<Int>(2017, 1) {
    override fun part1(input: List<String>): Int = sumOfSameNumbersInDistance(input.first(), 1)
    override fun part2(input: List<String>): Int = sumOfSameNumbersInDistance(input.first(), input.first().length / 2)

    private fun sumOfSameNumbersInDistance(sequence: String, distance: Int): Int {
        var sum = 0
        sequence.forEachIndexed { i, n ->
            if (n == sequence[(i + distance) % sequence.length]) {
                sum += "$n".toInt()
            }
        }
        return sum
    }
}
