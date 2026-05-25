package de.ronny_h.aoc.year2025.day03

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val LobbyTest by testSuite {

    val input = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.asList()

    testSuite(
        "joltage",
        mapOf(
            listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1) to 98,
            listOf(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9) to 89,
            listOf(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8) to 78,
            listOf(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1) to 92,
        ),
    ) { bank, expected ->
        bank.joltage(2) shouldBe expected
    }

    testSuite(
        "joltageWithSafetyOverride",
        mapOf(
            listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1) to 987654321111,
            listOf(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9) to 811111111119,
            listOf(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8) to 434234234278,
            listOf(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1) to 888911112111,
        ),
    ) { bank, expected ->
        bank.joltage(12) shouldBe expected
    }

    test("part 1: the total output joltage") {
        Lobby().part1(input) shouldBe 357
    }

    test("part 2: the total output joltage with safety override") {
        Lobby().part2(input) shouldBe 3121910778619
    }
}
