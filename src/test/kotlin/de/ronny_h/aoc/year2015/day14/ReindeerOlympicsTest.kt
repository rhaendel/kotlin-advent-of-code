package de.ronny_h.aoc.year2015.day14

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ReindeerOlympicsTest : StringSpec({

    val input = listOf(
        "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.",
    )
    val parsedInput = listOf(
        Reindeer(14, 10, 127),
        Reindeer(16, 11, 162),
    )

    "input can be parsed" {
        input.parse() shouldBe parsedInput
    }

    "part 1: max Reindeer distance in 1000 seconds" {
        parsedInput.maxReindeerDistanceIn(1000) shouldBe 1120
    }

    "part 2: points of the winner in 1000 seconds" {
        parsedInput.pointsOfWinnerIn(1000) shouldBe 689
    }
})
