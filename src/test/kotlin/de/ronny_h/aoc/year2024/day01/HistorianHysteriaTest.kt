package de.ronny_h.aoc.year2024.day01

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val HistorianHysteriaTest by testSuite {
    val smallInput = """
        7   1
        0   0
        1   6
    """.asList()
    val mediumInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.asList()

    test("part 1 - small lists with distance 1") {
        HistorianHysteria().part1(smallInput) shouldBe 1
    }

    test("part 1 - medium lists with distance 11") {
        HistorianHysteria().part1(mediumInput) shouldBe 11
    }

    test("part 2 - small lists with distance 1") {
        HistorianHysteria().part2(smallInput) shouldBe 1
    }

    test("part 2 - medium lists with distance 31") {
        HistorianHysteria().part2(mediumInput) shouldBe 31
    }
}
