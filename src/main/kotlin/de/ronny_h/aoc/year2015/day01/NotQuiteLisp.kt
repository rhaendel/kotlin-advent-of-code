package de.ronny_h.aoc.year2015.day01

import de.ronny_h.aoc.AdventOfCode

fun main() = NotQuiteLisp().run(232, 1783)

class NotQuiteLisp : AdventOfCode<Int>(2015, 1) {
    override fun part1(input: List<String>): Int {
        require(input.size == 1)
        val instructions = input.first()
        val ups = instructions.count { it == '(' }
        val downs = instructions.count { it == ')' }
        return ups - downs
    }

    override fun part2(input: List<String>): Int {
        var floor = 0
        input.first().forEachIndexed { i, char ->
            when (char) {
                '(' -> floor++
                ')' -> floor--
                else -> error("invalid instruction: $char at position $i")
            }
            if (floor < 0) {
                return i + 1
            }
        }
        return 0
    }
}
