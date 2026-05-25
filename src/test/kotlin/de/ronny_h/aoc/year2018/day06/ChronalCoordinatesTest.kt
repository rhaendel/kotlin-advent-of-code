package de.ronny_h.aoc.year2018.day06

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val ChronalCoordinatesTest by testSuite {

    val input = """
        1, 1
        1, 6
        8, 3
        3, 4
        5, 5
        8, 9
    """.asList()

    test("input can be parsed") {
        input.parseCoordinates() shouldBe listOf(
            Coordinates(1, 1),
            Coordinates(1, 6),
            Coordinates(8, 3),
            Coordinates(3, 4),
            Coordinates(5, 5),
            Coordinates(8, 9),
        )
    }

    test("part 1: the size of the largest area that isn't infinite") {
        ChronalCoordinates().part1(input) shouldBe 17
    }

    test("sizeOfRegionWithDistanceLessThan 32 - the day's example") {
        AreaGrid(input.parseCoordinates()).sizeOfRegionWithDistanceLessThan(32) shouldBe 16
    }

    test("part 2: the size of the region with a distance sum less than 10000") {
        ChronalCoordinates().part2(input) shouldBe 72
    }
}
