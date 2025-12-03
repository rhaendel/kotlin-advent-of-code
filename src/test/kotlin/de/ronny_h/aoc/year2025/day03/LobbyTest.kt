package de.ronny_h.aoc.year2025.day03

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class LobbyTest : StringSpec({

    val input = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.asList()

    "joltage" {
        forAll(
            row(listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1), 98),
            row(listOf(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9), 89),
            row(listOf(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8), 78),
            row(listOf(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1), 92),
        ) { bank, expected ->
            bank.joltage() shouldBe expected
        }
    }

    "joltageWithSafetyOverride" {
        forAll(
            row(listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1), 987654321111),
            row(listOf(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9), 811111111119),
            row(listOf(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8), 434234234278),
            row(listOf(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1), 888911112111),
        ) { bank, expected ->
            bank.joltageWithSafetyOverride() shouldBe expected
        }
    }

    "part 1: the total output joltage" {
        Lobby().part1(input) shouldBe 357
    }

    "part 2: the total output joltage with safety override" {
        Lobby().part2(input) shouldBe 3121910778619
    }
})
