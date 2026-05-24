package de.ronny_h.aoc.year2017.day05

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val AMazeOfTwistyTrampolinesTest by testSuite {

    val input = listOf(
        "0",
        "3",
        "0",
        "1",
        "-3",
    )

    test("part 1: number of steps to reach the exit when always increasing the jump offset by 1") {
        AMazeOfTwistyTrampolines().part1(input) shouldBe 5
    }

    test("part 2: number of steps to reach the exit when decreasing the jump offset by 1 if the offset was 3 or more") {
        AMazeOfTwistyTrampolines().part2(input) shouldBe 10
    }
}
