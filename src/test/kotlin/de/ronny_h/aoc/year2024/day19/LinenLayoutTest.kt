package de.ronny_h.aoc.year2024.day19

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val LinenLayoutTest by testSuite {
    val input = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.asList()

    test("part 1: Number of possible designs") {
        LinenLayout().part1(input) shouldBe 6
    }

    test("part 2: Number of different ways to achieve all possible designs") {
        LinenLayout().part2(input) shouldBe 16
    }
}
