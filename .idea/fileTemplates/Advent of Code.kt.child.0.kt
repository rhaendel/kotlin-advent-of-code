package de.ronny_h.aoc.year${Year}.day$Day

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val Day${Day}Test by testSuite {

    test("part 1") {
        val input = listOf("")
        Day$Day().part1(input) shouldBe 0
    }

    test("part 2") {
        val input = listOf("")
        Day$Day().part2(input) shouldBe 0
    }
}
