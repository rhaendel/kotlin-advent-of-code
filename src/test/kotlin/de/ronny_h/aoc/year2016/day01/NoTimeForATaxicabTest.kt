package de.ronny_h.aoc.year2016.day01

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NoTimeForATaxicabTest : StringSpec({

    "part 1, small example" {
        NoTimeForATaxicab().part1(listOf("R2, L3")) shouldBe 5
    }

    "part 1, medium example" {
        NoTimeForATaxicab().part1(listOf("R2, R2, R2")) shouldBe 2
    }

    "part 1, large example" {
        NoTimeForATaxicab().part1(listOf("R5, L5, R5, R3")) shouldBe 12
    }

    "part 1, negative x, positive y coordinate" {
        NoTimeForATaxicab().part1(listOf("L1, R1")) shouldBe 2
    }

    "part 2: the first location visited twice" {
        NoTimeForATaxicab().part2(listOf("R8, R4, R4, R8")) shouldBe 4
    }
})
