package de.ronny_h.aoc.year2024.day08

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val ResonantCollinearityTest by testSuite {
    val smallInput = """
        A...b
        ....b
        ..A..
        ..cc.
        .....
    """.asList()
    val mediumInput = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.asList()

    testSuite(
        "part 1: Unique antinode locations within the bounds of the map",
        mapOf(
            smallInput to 4,
            mediumInput to 14,
        ),
    ) { input, result ->
        ResonantCollinearity().part1(input) shouldBe result
    }

    testSuite(
        "part 2: Unique antinode locations within the bounds of the map including resonant harmonics",
        mapOf(
            smallInput to 11,
            mediumInput to 34,
        ),
    ) { input, result ->
        ResonantCollinearity().part2(input) shouldBe result
    }
}
