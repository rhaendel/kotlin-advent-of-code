package de.ronny_h.aoc.year2015.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.md5

fun main() = IdealStockingStuffer().run(282749, 9962624)

class IdealStockingStuffer : AdventOfCode<Int>(2015, 4) {
    override fun part1(input: List<String>): Int = lowestNumberProducingMD5StartingWith("00000", input.first())

    override fun part2(input: List<String>): Int = lowestNumberProducingMD5StartingWith("000000", input.first())

    private fun lowestNumberProducingMD5StartingWith(prefix: String, secretKey: String): Int {
        var number = 0
        while (!"$secretKey$number".md5().startsWith(prefix)) {
            number++
        }
        return number
    }
}
