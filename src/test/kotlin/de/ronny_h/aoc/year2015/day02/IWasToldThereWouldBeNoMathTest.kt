package de.ronny_h.aoc.year2015.day02

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val IWasToldThereWouldBeNoMathTest by testSuite {

    test("part 1: A present with dimensions 2x3x4 requires 58 square feet of wrapping paper") {
        val input = listOf("2x3x4")
        IWasToldThereWouldBeNoMath().part1(input) shouldBe 58
    }
    test("part 1: A present with dimensions 1x1x10 requires 43 square feet of wrapping paper") {
        val input = listOf("1x1x10")
        IWasToldThereWouldBeNoMath().part1(input) shouldBe 43
    }

    test("part 2: A present with dimensions 2x3x4 requires 34 feet of ribbon") {
        val intput = listOf("2x3x4")
        IWasToldThereWouldBeNoMath().part2(intput) shouldBe 34
    }
    test("part 2: A present with dimensions 1x1x10 requires 14 feet of ribbon") {
        val intput = listOf("1x1x10")
        IWasToldThereWouldBeNoMath().part2(intput) shouldBe 14
    }
}
