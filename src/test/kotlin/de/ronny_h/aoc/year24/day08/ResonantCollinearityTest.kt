package de.ronny_h.aoc.year24.day08

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ResonantCollinearityTest : StringSpec({
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

    "part 1: Unique antinode locations within the bounds of the map" {
        forAll(
            row(smallInput, 4),
            row(mediumInput, 14),
        ) { input, result ->
            ResonantCollinearity().part1(input) shouldBe result
        }
    }

    "part 2: Unique antinode locations within the bounds of the map including resonant harmonics " {
        forAll(
            row(smallInput, 11),
            row(mediumInput, 34),
        ) { input, result ->
            ResonantCollinearity().part2(input) shouldBe result
        }
    }
})
