package de.ronny_h.aoc.year2025.day09

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val MovieTheaterTest by testSuite {

    val input = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.asList()

    test("input can be parsed to tiles") {
        """
            7,1
            11,1
            11,7
        """.asList().parseTiles() shouldBe listOf(
            Coordinates(7, 1),
            Coordinates(11, 1),
            Coordinates(11, 7),
        )

    }

    test("part 1: the largest area of any rectangle one can make") {
        MovieTheater().part1(input) shouldBe 50
    }

    test("part 2: the largest area of any rectangle one can make using only red and green tiles") {
        MovieTheater().part2(input) shouldBe 24
    }
}
