package de.ronny_h.aoc.year2018.day21

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val ChronalConversionTest by testSuite {

    test("part 1: The lowest value for register 0 that halts the program") {
        // just feed in the bitwise operations on numbers check
        val input = """
            #ip 5
            seti 123 0 4
            bani 4 456 4
            eqri 4 72 4
            addr 4 5 5
        """.asList()

        ChronalConversion().part1(input) shouldBe 9566170
    }

    test("part 2") {
        val input = listOf("")
        ChronalConversion().part2(input) shouldBe 0
    }
}
