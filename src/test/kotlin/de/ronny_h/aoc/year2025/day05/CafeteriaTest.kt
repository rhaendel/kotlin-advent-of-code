package de.ronny_h.aoc.year2025.day05

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class CafeteriaTest : StringSpec({

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

    "input can be parsed" {
        val input = """
        3-5
        10-14

        1
        5
        11
    """.asList()
        input.parseIngredients() shouldBe Ingredients(listOf(3L..5L, 10L..14L), listOf(1L, 5L, 11L))
    }

    "compact merges the ranges in the list" {
        forAll(
            row(listOf(2L..5L, 3L..4L), listOf(2L..5L)),
            row(listOf(2L..5L, 6L..7L), listOf(2L..5L, 6L..7L)),
            row(listOf(2L..5L, 8L..9L, 4L..6L), listOf(2L..6L, 8L..9L)),
            row(listOf(2L..5L, 8L..9L, 4L..8L), listOf(2L..9L)),
            row(listOf(2L..5L, 8L..9L, 11L..13L, 4L..11L), listOf(2L..13L)),
            row(listOf(0L..1L, 4L..5L, 8L..9L, 11L..13L, 15L..20L, 4L..11L), listOf(0L..1L, 4L..13L, 15L..20L)),
        ) { list, expected ->
            list.compact() shouldBe expected
        }
    }

    "part 1: the number of available ingredient IDs that are fresh" {
        Cafeteria().part1(input) shouldBe 3
    }

    "part 2: the total number of ingredient IDs considered to be fresh " {
        Cafeteria().part2(input) shouldBe 14
    }
})
