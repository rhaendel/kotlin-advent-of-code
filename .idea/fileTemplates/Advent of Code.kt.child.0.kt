package de.ronny_h.aoc.year${Year}.day$Day

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day${Day}Test : StringSpec({

    "part 1" {
        val input = listOf("")
        Day$Day().part1(input) shouldBe 0
    }

    "part 2" {
        val input = listOf("")
        Day$Day().part2(input) shouldBe 0
    }
})
