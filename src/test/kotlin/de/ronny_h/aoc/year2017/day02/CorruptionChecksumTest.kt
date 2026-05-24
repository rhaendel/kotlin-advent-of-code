package de.ronny_h.aoc.year2017.day02

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val CorruptionChecksumTest by testSuite {

    test("part 1: the checksum of the example spreadsheet") {
        val input = """
            5 1 9 5
            7 5 3
            2 4 6 8
        """.asList()
        CorruptionChecksum().part1(input) shouldBe 18
    }

    test("part 2: the sum of evenly divisible values' divide results") {
        val input = """
            5 9 2 8
            9 4 7 3
            3 8 6 5
        """.asList()
        CorruptionChecksum().part2(input) shouldBe 9
    }
}
