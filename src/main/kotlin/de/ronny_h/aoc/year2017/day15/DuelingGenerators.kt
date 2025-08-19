package de.ronny_h.aoc.year2017.day15

import de.ronny_h.aoc.AdventOfCode

fun main() = DuelingGenerators().run(594, 328)

class DuelingGenerators : AdventOfCode<Int>(2017, 15) {
    override fun part1(input: List<String>): Int = judge(
        Generator(seed(input, 'A', 0), 16807),
        Generator(seed(input, 'B', 1), 48271),
    )

    override fun part2(input: List<String>): Int = judge(
        Generator(seed(input, 'A', 0), 16807, 4),
        Generator(seed(input, 'B', 1), 48271, 8),
        5_000_000,
    )

    private fun seed(input: List<String>, generator: Char, index: Int): Long = input[index]
        .substringAfter("Generator $generator starts with ").toLong()

    private fun judge(
        generatorA: Generator,
        generatorB: Generator,
        iterations: Int = 40_000_000,
    ): Int {
        val bitmask = "00000000000000001111111111111111".toLong(2)

        var equalLowestBitsCount = 0
        repeat(iterations) {
            val lowestABits = generatorA.next() and bitmask
            val lowestBBits = generatorB.next() and bitmask
            if (lowestABits == lowestBBits) {
                equalLowestBitsCount++
            }
        }
        return equalLowestBitsCount
    }
}

class Generator(seed: Long, private val factor: Int, private val provideOnlyMultiplesOf: Int = 1) {
    private var value = seed
    private val divider = 2147483647

    fun next(): Long {
        do {
            value = (value * factor) % divider
        } while (value % provideOnlyMultiplesOf != 0L)
        return value
    }
}
