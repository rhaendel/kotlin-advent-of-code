package de.ronny_h.aoc.year2018.day05

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val AlchemicalReductionTest by testSuite {

    testSuite("simple reactions") {
        mapOf(
            "aA" to "",
            "abBA" to "",
            "abBAc" to "c",
            "cabBA" to "c",
            "abAB" to "abAB",
            "aabAAB" to "aabAAB",
            "dabAcCaCBAcCcaDA" to "dabCBAcaDA",
        ).forEach { (chain, result) ->
            test("$chain,$result") {
                chain.react() shouldBe result
            }
        }
    }

    test("part 1: the number of units after all possible reactions") {
        val input = listOf("dabAcCaCBAcCcaDA")
        AlchemicalReduction().part1(input) shouldBe 10
    }

    test("part 2: the number of units after all possible reactions with the most problem-causing unit removed") {
        val input = listOf("dabAcCaCBAcCcaDA")
        AlchemicalReduction().part2(input) shouldBe 4
    }
}
