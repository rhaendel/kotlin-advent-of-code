package de.ronny_h.aoc.year24.day22

import de.ronny_h.aoc.AdventOfCode

fun main() = MonkeyMarket().run(17965282217, 0)

class MonkeyMarket : AdventOfCode<Long>(2024, 22) {

    fun nextSecretNumber(seed: Long): Long {
        var secret = (seed * 64) xor seed
        secret %= 16777216
        secret = (secret / 32) xor secret
        secret %= 16777216
        secret = (secret * 2048) xor secret
        secret %= 16777216
        return secret
    }

    override fun part1(input: List<String>): Long = input
        .map(String::toLong)
        .sumOf {
            var secret = it
            repeat(2000) {
                secret = nextSecretNumber(secret)
            }
            secret
        }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}
