package de.ronny_h.aoc.year2017.day05

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AMazeOfTwistyTrampolinesTest : StringSpec({

    val input = listOf(
        "0",
        "3",
        "0",
        "1",
        "-3",
    )

    "part 1: number of steps to reach the exit when always increasing the jump offset by 1" {
        AMazeOfTwistyTrampolines().part1(input) shouldBe 5
    }

    "part 2: number of steps to reach the exit when decreasing the jump offset by 1 if the offset was 3 or more" {
        AMazeOfTwistyTrampolines().part2(input) shouldBe 10
    }
})
