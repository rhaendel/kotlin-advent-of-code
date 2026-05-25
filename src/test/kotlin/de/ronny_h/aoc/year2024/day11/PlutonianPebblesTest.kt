package de.ronny_h.aoc.year2024.day11

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val PlutonianPebblesTest by testSuite {
    val smallInput = """
        125 17
    """.asList()

    test("part 1: The number of stones after blinking 25 times") {
        PlutonianPebbles().part1(smallInput) shouldBe 55312
    }

    test("part 2: The number of stones after blinking 75 times") {
        PlutonianPebbles().part2(smallInput) shouldBe 65601038650482
    }
}
