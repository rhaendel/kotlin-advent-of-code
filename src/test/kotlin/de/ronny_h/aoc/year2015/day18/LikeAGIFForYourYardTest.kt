package de.ronny_h.aoc.year2015.day18

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.year2015.day18.LikeAGIFForYourYard.Companion.animateSteps
import de.ronny_h.aoc.year2015.day18.LikeAGIFForYourYard.Companion.animateStepsWithCornersStuckOn
import io.kotest.matchers.shouldBe

val LikeAGIFForYourYardTest by testSuite {

    val input = listOf(
        ".#.#.#",
        "...##.",
        "#....#",
        "..#...",
        "#.#..#",
        "####..",
    )

    test("part 1: the number of lights on after one and four iterations") {
        val grid = GameOfLightGrid(input)
        grid.animateSteps(1).countLightsOn() shouldBe 11
        grid.animateSteps(4).countLightsOn() shouldBe 4
    }

    test("part 2: the number of lights on after one and five iterations with corners stuck on") {
        val grid = GameOfLightGrid(input)
        grid.animateStepsWithCornersStuckOn(1).countLightsOn() shouldBe 18
        grid.animateStepsWithCornersStuckOn(5).countLightsOn() shouldBe 17
    }
}
