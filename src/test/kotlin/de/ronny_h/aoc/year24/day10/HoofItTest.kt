package de.ronny_h.aoc.year24.day10

import de.ronny_h.aoc.extensions.asList
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class HoofItTest : StringSpec({
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

    "part 1: The sum of the scores of all trailheads" {
        forall(
            row(smallInput1, 1),
            row(mediumInput, 36),
        ) { input, result ->
            HoofIt().part1(input) shouldBe result
        }
    }

    "part 2: The sum of the ratings of all trailheads" {
        forall(
            row(smallInput2, 3),
            row(mediumInput, 81),
        ) { input, result ->
            HoofIt().part2(input) shouldBe result
        }
    }
})
