package de.ronny_h.aoc.year2015.day18

import de.ronny_h.aoc.year2015.day18.LikeAGIFForYourYard.Companion.animateSteps
import de.ronny_h.aoc.year2015.day18.LikeAGIFForYourYard.Companion.animateStepsWithCornersStuckOn
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LikeAGIFForYourYardTest : StringSpec({

    val input = listOf(
        ".#.#.#",
        "...##.",
        "#....#",
        "..#...",
        "#.#..#",
        "####..",
    )

    "part 1: the number of lights on after one and four iterations" {
        val grid = GameOfLightGrid(input)
        grid.animateSteps(1).countLightsOn() shouldBe 11
        grid.animateSteps(4).countLightsOn() shouldBe 4
    }

    "part 2: the number of lights on after one and five iterations with corners stuck on" {
        val grid = GameOfLightGrid(input)
        grid.animateStepsWithCornersStuckOn(1).countLightsOn() shouldBe 18
        grid.animateStepsWithCornersStuckOn(5).countLightsOn() shouldBe 17
    }
})
