package de.ronny_h.aoc.year2015.day20

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class InfiniteElvesAndInfiniteHousesTest : StringSpec({
    "the divisor function for some small numbers" {
        forAll(
            row(1, 1),
            row(2, 3),
            row(3, 4),
            row(4, 7),
            row(5, 6),
            row(6, 12),
            row(7, 8),
            row(8, 15),
            row(9, 13),
        ) { number, presents ->
            numberOfPresentsForHouse(number) shouldBe presents
        }
    }

    "part 1: the lowest house number to get at least 120 presents" {
        InfiniteElvesAndInfiniteHouses().part1(listOf("120")) shouldBe 6
    }

    "part 2: the lowest house number to get at least 120 presents with 11 presents each" {
        InfiniteElvesAndInfiniteHouses().part2(listOf("77")) shouldBe 4
    }
})
