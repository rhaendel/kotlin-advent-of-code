package de.ronny_h.aoc.year2015.day16

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AuntSueTest : StringSpec({

    val input = listOf(
        "Sue 1: cars: 9, akitas: 3, goldfish: 0",
        "Sue 2: akitas: 9, children: 3, samoyeds: 9",
        "Sue 3: pomeranians: 3, perfumes: 1, vizslas: 0",
        "Sue 4: goldfish: 0, vizslas: 0, samoyeds: 2",
        "Sue 5: cats: 7, trees: 0, vizslas: 1",
    )

    "Sues can be parsed" {
        input.parseSues() shouldBe listOf(
            Sue(1, mapOf("cars" to 9, "akitas" to 3, "goldfish" to 0)),
            Sue(2, mapOf("akitas" to 9, "children" to 3, "samoyeds" to 9)),
            Sue(3, mapOf("pomeranians" to 3, "perfumes" to 1, "vizslas" to 0)),
            Sue(4, mapOf("goldfish" to 0, "vizslas" to 0, "samoyeds" to 2)),
            Sue(5, mapOf("cats" to 7, "trees" to 0, "vizslas" to 1)),
        )
    }

    "part 1: The sue that matches all given attributes" {
        AuntSue().part1(input) shouldBe 3
    }

    "part 2: The sue that matches all given attributes respecting the outdated retroencabulator" {
        AuntSue().part2(input) shouldBe 4
    }
})
