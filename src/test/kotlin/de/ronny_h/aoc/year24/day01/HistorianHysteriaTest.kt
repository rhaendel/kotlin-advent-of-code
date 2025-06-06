package de.ronny_h.aoc.year24.day01

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class HistorianHysteriaTest : StringSpec({

    val smallInput = listOf(
        "7   1",
        "0   0",
        "1   6",
    )

    val mediumInput = listOf(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3",
    )

    "part 1 - small lists with distance 1" {
        HistorianHysteria().part1(smallInput) shouldBe 1
    }

    "part 1 - medium lists with distance 11" {
        HistorianHysteria().part1(mediumInput) shouldBe 11
    }

    "part 2 - small lists with distance 1" {
        HistorianHysteria().part2(smallInput) shouldBe 1
    }

    "part 2 - medium lists with distance 31" {
        HistorianHysteria().part2(mediumInput) shouldBe 31
    }
})
