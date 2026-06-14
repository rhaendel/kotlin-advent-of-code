package de.ronny_h.aoc.year2018.day21

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ChronalConversionTest by testSuite {

    test("part 1: The lowest value for register 0 that halts the program with fewest instructions") {
        ChronalConversion().part1(emptyList()) shouldBe 9566170
    }

    test("part 2: The lowest value for register 0 that halts the program with most instructions") {
        ChronalConversion().part2(emptyList()) shouldBe 13192622
    }
}
