package de.ronny_h.aoc.year2018.day06

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ChronalCoordinatesTest : StringSpec({

    val input = """
        1, 1
        1, 6
        8, 3
        3, 4
        5, 5
        8, 9
    """.asList()

    "input can be parsed" {
        input.parseCoordinates() shouldBe listOf(
            Coordinates(1, 1),
            Coordinates(6, 1),
            Coordinates(3, 8),
            Coordinates(4, 3),
            Coordinates(5, 5),
            Coordinates(9, 8),
        )
    }

    "part 1: the size of the largest area that isn't infinite" {
        ChronalCoordinates().part1(input) shouldBe 17
    }

    "sizeOfRegionWithDistanceLessThan 32 - the day's example" {
        AreaGrid(input.parseCoordinates()).sizeOfRegionWithDistanceLessThan(32) shouldBe 16
    }

    "part 2: the size of the region with a distance sum less than 10000" {
        ChronalCoordinates().part2(input) shouldBe 72
    }
})
