package de.ronny_h.aoc.year2024.day10

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val HoofItTest by testSuite {
    val smallInput1 = """
        0123
        1234
        8765
        9876
    """.asList()
    val mediumInput = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.asList()
    val smallInput2 = """
        .....0.
        ..4321.
        ..5..2.
        ..6543.
        ..7..4.
        ..8765.
        ..9....
    """.asList()

    testSuite(
        "part 1: The sum of the scores of all trailheads",
        mapOf(
            smallInput1 to 1,
            mediumInput to 36,
        ),
    ) { input, result ->
        HoofIt().part1(input) shouldBe result
    }


    testSuite(
        "part 2: The sum of the ratings of all trailheads",
        mapOf(
            smallInput2 to 3,
            mediumInput to 81,
        ),
    ) { input, result ->
        HoofIt().part2(input) shouldBe result
    }
}
