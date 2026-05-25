package de.ronny_h.aoc.year2015.day17

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val NoSuchThingAsTooMuchTest by testSuite {

    val input = listOf(20, 15, 10, 5, 5)

    test("part 1: number of combinations to store 25 litres of eggnog") {
        differentCombinationsToStore(input, 25) shouldBe 4
    }

    test("part 2: number of combinations to store 25 litres of eggnog with minimal number of containers") {
        differentWaysToStoreWithMinimalNumberOfContainers(input, 25) shouldBe 3
    }
}
