package de.ronny_h.aoc.year2025.day09

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MovieTheaterTest : StringSpec({

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

    "input can be parsed to tiles" {
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

    "part 1: the largest area of any rectangle one can make" {
        MovieTheater().part1(input) shouldBe 50
    }

    "part 2: the largest area of any rectangle one can make using only red and green tiles" {
        MovieTheater().part2(input) shouldBe 24
    }
})
