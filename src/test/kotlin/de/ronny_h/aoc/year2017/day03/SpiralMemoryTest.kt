package de.ronny_h.aoc.year2017.day03

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val SpiralMemoryTest by testSuite {

    testSuite("square numbers and their coordinates") {
        mapOf(
            1 to Coordinates(0, 0),
            2 to Coordinates(1, 0),
            12 to Coordinates(2, -1),
            23 to Coordinates(0, 2),
            25 to Coordinates(2, 2),
        ).forEach { (number, coordinates) ->
            test("$number, $coordinates") {
                coordinatesOf(number) shouldBe coordinates
            }
        }
    }

    testSuite("part 1: square numbers and their taxi distance to number 1") {
        val spiralMemory = SpiralMemory()
        mapOf(
            1 to 0,
            2 to 1,
            12 to 3,
            23 to 2,
            25 to 4,
            1024 to 31,
        ).forEach { (number, distance) ->
            test("$number, $distance") {
                spiralMemory.part1(listOf("$number")) shouldBe distance
            }
        }
    }

    fun sumOfAdjacentFilledNumbersOf(number: Int): Int = sequenceOfCoordinatesAndSums()
        .take(number)
        .last()
        .second

    testSuite("the sum of the adjacent filled numbers") {
        mapOf(
            1 to 1,
            2 to 1,
            3 to 2,
            4 to 4,
            5 to 5,
            23 to 806,
        ).forEach { (number, sum) ->
            test("$number, $sum") {
                sumOfAdjacentFilledNumbersOf(number) shouldBe sum
            }
        }
    }

    test("part 2: the first value written that is larger than the input") {
        SpiralMemory().part2(listOf("5")) shouldBe 10
    }
}
