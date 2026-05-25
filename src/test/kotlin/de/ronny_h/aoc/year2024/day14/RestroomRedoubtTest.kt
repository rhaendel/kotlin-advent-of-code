package de.ronny_h.aoc.year2024.day14

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val RestroomRedoubtTest by testSuite {
    val input = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.asList()

    test("part 1: The safety factor after 100 seconds with a small room") {
        RestroomRedoubt().part1Small(input) shouldBe 12
    }

    test("part 1: The safety factor after 100 seconds") {
        RestroomRedoubt().part1(input) shouldBe 21
    }
}
