package de.ronny_h.aoc.year2015.day20

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val InfiniteElvesAndInfiniteHousesTest by testSuite {
    testSuite("the divisor function for some small numbers") {
        mapOf(
            1 to 1,
            2 to 3,
            3 to 4,
            4 to 7,
            5 to 6,
            6 to 12,
            7 to 8,
            8 to 15,
            9 to 13,
        ).forEach { (number, presents) ->
            test("$number, $presents") {
                numberOfPresentsForHouse(number) shouldBe presents
            }
        }
    }

    test("part 1: the lowest house number to get at least 120 presents") {
        InfiniteElvesAndInfiniteHouses().part1(listOf("120")) shouldBe 6
    }

    test("part 2: the lowest house number to get at least 120 presents with 11 presents each") {
        InfiniteElvesAndInfiniteHouses().part2(listOf("77")) shouldBe 4
    }
}
