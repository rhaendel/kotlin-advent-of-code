package de.ronny_h.aoc.year2015.day15

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ScienceForHungryPeopleTest by testSuite {

    val input = listOf(
        "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
        "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3",
    )

    test("input can be parsed") {
        input.parseIngredients() shouldBe listOf(Ingredient(-1, -2, 6, 3, 8), Ingredient(2, 3, -2, -1, 3))
    }

    test("part 1: The total score of the highest-scoring cookie with two ingredients") {
        ScienceForHungryPeople().part1(input) shouldBe 62842880
    }

    test("part 2: The total score of the highest-scoring cookie with two ingredients with a calorie total of 500") {
        ScienceForHungryPeople().part2(input) shouldBe 57600000
    }
}
