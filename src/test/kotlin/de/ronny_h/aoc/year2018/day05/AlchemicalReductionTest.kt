package de.ronny_h.aoc.year2018.day05

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class AlchemicalReductionTest : StringSpec({

    "simple reactions" {
        forAll(
            row("aA", ""),
            row("abBA", ""),
            row("abBAc", "c"),
            row("cabBA", "c"),
            row("abAB", "abAB"),
            row("aabAAB", "aabAAB"),
            row("dabAcCaCBAcCcaDA", "dabCBAcaDA"),
        ) { chain, result ->
            chain.react() shouldBe result
        }
    }

    "part 1: the number of units after all possible reactions" {
        val input = listOf("dabAcCaCBAcCcaDA")
        AlchemicalReduction().part1(input) shouldBe 10
    }

    "part 2: the number of units after all possible reactions with the most problem-causing unit removed" {
        val input = listOf("dabAcCaCBAcCcaDA")
        AlchemicalReduction().part2(input) shouldBe 4
    }
})
