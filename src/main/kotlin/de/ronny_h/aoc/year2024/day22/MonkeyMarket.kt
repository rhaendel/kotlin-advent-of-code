package de.ronny_h.aoc.year2024.day22

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.numbers.onesDigit

fun main() = MonkeyMarket().run(17965282217, 2152)

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

    data class Buyer(val prices: List<Int>, val priceChanges: List<Int>)

    override fun part2(input: List<String>): Long {
        val buyers = input
            .map(String::toLong)
            .map {
                val prices = listPrices(it, 2000)
                Buyer(prices, prices.changes())
            }
        println("${buyers.size} buyers")

        val bananas = sumUpBananasForSequences(buyers)

        val (sequence, maxBananas) = bananas.maxBy { it.value }
        println("sequence with max value: $sequence")
        val numberOfMax = bananas.filter { it.value == maxBananas }
        println("sequences with the same value: $numberOfMax from ${bananas.size} sequences total")
        return maxBananas
    }

    fun sumUpBananasForSequences(buyers: List<Buyer>): Map<List<Int>, Long> {
        val bananas = mutableMapOf<List<Int>, Long>()
        buyers.forEach {
            var index = 4
            val seenForThatBuyer = mutableSetOf<List<Int>>()
            it.priceChanges.windowed(4) { ephemeralSequence ->
                val sequence = ephemeralSequence.toList()
                // the hiding spot is sold on the first occurrence of a sequence -> don't add up values for later ones
                if (seenForThatBuyer.add(sequence)) {
                    bananas[sequence] = bananas.getOrElse(sequence) { 0 } + it.prices[index]
                }
                index++
            }
        }
        return bananas
    }

    fun listPrices(seed: Long, n: Int): List<Int> {
        var secret = seed
        return listOf(seed.onesDigit()) + List(n) {
            secret = nextSecretNumber(secret)
            secret.onesDigit()
        }
    }
}

fun List<Int>.changes(): List<Int> = windowed(2)
    .map { (a, b) -> b - a }
