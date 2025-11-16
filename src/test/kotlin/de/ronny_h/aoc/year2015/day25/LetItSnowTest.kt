package de.ronny_h.aoc.year2015.day25

import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class LetItSnowTest : StringSpec({

    "the input can be parsed" {
        listOf("To continue, please consult the code grid in the manual.  Enter the code at row 1, column 2.")
            .parseCoordinates() shouldBe Coordinates(2, 1)
    }

    "the next code can be calculated" {
        forAll(
            row(20151125, 31916031),
            row(31916031, 18749137),
            row(33071741, 17552253),
            row(1534922, 31663883),
        ) { code, next ->
            nextCodeFor(code) shouldBe next
        }
    }

    "number of sequence at coordinates" {
        forAll(
            row(Coordinates(1, 1), 1),
            row(Coordinates(1, 2), 2),
            row(Coordinates(2, 1), 3),
            row(Coordinates(1, 3), 4),
            row(Coordinates(2, 2), 5),
            row(Coordinates(3, 1), 6),
            row(Coordinates(6, 1), 21),
        ) { coordinates, number ->
            numberOfSequenceAt(coordinates) shouldBe number
        }
    }

    "part 1: The code at row 6 and column 6" {
        val input =
            listOf("To continue, please consult the code grid in the manual.  Enter the code at row 6, column 6.")
        LetItSnow().part1(input) shouldBe 27995004
    }
})
