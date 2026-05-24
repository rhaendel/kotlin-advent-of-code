package de.ronny_h.aoc.year2016.day01

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val NoTimeForATaxicabTest by testSuite {

    test("part 1, small example") {
        NoTimeForATaxicab().part1(listOf("R2, L3")) shouldBe 5
    }

    test("part 1, medium example") {
        NoTimeForATaxicab().part1(listOf("R2, R2, R2")) shouldBe 2
    }

    test("part 1, large example") {
        NoTimeForATaxicab().part1(listOf("R5, L5, R5, R3")) shouldBe 12
    }

    test("part 1, negative x, positive y coordinate") {
        NoTimeForATaxicab().part1(listOf("L1, R1")) shouldBe 2
    }

    test("part 2: the first location visited twice") {
        NoTimeForATaxicab().part2(listOf("R8, R4, R4, R8")) shouldBe 4
    }
}
