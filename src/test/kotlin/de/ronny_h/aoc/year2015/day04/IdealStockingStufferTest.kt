package de.ronny_h.aoc.year2015.day04

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class IdealStockingStufferTest : StringSpec({

    "part 1: The lowest number producing an MD5 hash starting with 00000" {
        IdealStockingStuffer().part1(listOf("abcdef")) shouldBe 609043
    }

    // part 2 is the same algorithm just with a larger number. Would take too long for a unit test,
})
