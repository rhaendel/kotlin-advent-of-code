package de.ronny_h.aoc.year2015.day25

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val LetItSnowTest by testSuite {

    test("the input can be parsed") {
        listOf("To continue, please consult the code grid in the manual.  Enter the code at row 1, column 2.")
            .parseCoordinates() shouldBe Coordinates(2, 1)
    }

    testSuite("the next code can be calculated") {
        mapOf(
            20151125 to 31916031,
            31916031 to 18749137,
            33071741 to 17552253,
            1534922 to 31663883,
        ).forEach { (code, next) ->
            test("$code, $next") {
                nextCodeFor(code) shouldBe next
            }
        }
    }

    testSuite("number of sequence at coordinates") {
        mapOf(
            Coordinates(1, 1) to 1,
            Coordinates(1, 2) to 2,
            Coordinates(2, 1) to 3,
            Coordinates(1, 3) to 4,
            Coordinates(2, 2) to 5,
            Coordinates(3, 1) to 6,
            Coordinates(6, 1) to 21,
        ).forEach { (coordinates, number) ->
            test("$coordinates, $number") {
                numberOfSequenceAt(coordinates) shouldBe number
            }
        }
    }

    test("part 1: The code at row 6 and column 6") {
        val input =
            listOf("To continue, please consult the code grid in the manual.  Enter the code at row 6, column 6.")
        LetItSnow().part1(input) shouldBe 27995004
    }
}
