package de.ronny_h.aoc.year2015.day02

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class IWasToldThereWouldBeNoMathTest : StringSpec({

    "part 1: A present with dimensions 2x3x4 requires 58 square feet of wrapping paper" {
        val input = listOf("2x3x4")
        IWasToldThereWouldBeNoMath().part1(input) shouldBe 58
    }
    "part 1: A present with dimensions 1x1x10 requires 43 square feet of wrapping paper" {
        val input = listOf("1x1x10")
        IWasToldThereWouldBeNoMath().part1(input) shouldBe 43
    }

    "part 2: A present with dimensions 2x3x4 requires 34 feet of ribbon" {
        val intput = listOf("2x3x4")
        IWasToldThereWouldBeNoMath().part2(intput) shouldBe 34
    }
    "part 2: A present with dimensions 1x1x10 requires 14 feet of ribbon" {
        val intput = listOf("1x1x10")
        IWasToldThereWouldBeNoMath().part2(intput) shouldBe 14
    }
})
