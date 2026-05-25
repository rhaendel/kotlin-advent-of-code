package de.ronny_h.aoc.year2025.day05

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val CafeteriaTest by testSuite {

    val input = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.asList()

    test("input can be parsed") {
        val input = """
        3-5
        10-14

        1
        5
        11
    """.asList()
        input.parseIngredients() shouldBe Ingredients(listOf(3L..5L, 10L..14L), listOf(1L, 5L, 11L))
    }

    testSuite("compact merges the ranges in the list",
        mapOf(
            listOf(2L..5L, 3L..4L) to listOf(2L..5L),
            listOf(2L..5L, 6L..7L) to listOf(2L..5L, 6L..7L),
            listOf(2L..5L, 8L..9L, 4L..6L) to listOf(2L..6L, 8L..9L),
            listOf(2L..5L, 8L..9L, 4L..8L) to listOf(2L..9L),
            listOf(2L..5L, 8L..9L, 11L..13L, 4L..11L) to listOf(2L..13L),
            listOf(0L..1L, 4L..5L, 8L..9L, 11L..13L, 15L..20L, 4L..11L) to listOf(0L..1L, 4L..13L, 15L..20L),
        ),) { list, expected ->
            list.compact() shouldBe expected
        }

    test("part 1: the number of available ingredient IDs that are fresh") {
        Cafeteria().part1(input) shouldBe 3
    }

    test("part 2: the total number of ingredient IDs considered to be fresh ") {
        Cafeteria().part2(input) shouldBe 14
    }
}
