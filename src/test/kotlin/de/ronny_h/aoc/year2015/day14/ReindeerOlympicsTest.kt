package de.ronny_h.aoc.year2015.day14

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ReindeerOlympicsTest by testSuite {

    val input = listOf(
        "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.",
    )
    val parsedInput = listOf(
        Reindeer(14, 10, 127),
        Reindeer(16, 11, 162),
    )

    test("input can be parsed") {
        input.parse() shouldBe parsedInput
    }

    test("part 1: max Reindeer distance in 1000 seconds") {
        parsedInput.maxReindeerDistanceIn(1000) shouldBe 1120
    }

    test("part 2: points of the winner in 1000 seconds") {
        parsedInput.pointsOfWinnerIn(1000) shouldBe 689
    }
}
