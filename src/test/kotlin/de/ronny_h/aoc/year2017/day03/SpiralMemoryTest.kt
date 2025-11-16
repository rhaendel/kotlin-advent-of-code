package de.ronny_h.aoc.year2017.day03

import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SpiralMemoryTest : StringSpec({

    "square numbers and their coordinates" {
        forAll(
            row(1, Coordinates(0, 0)),
            row(2, Coordinates(1, 0)),
            row(12, Coordinates(2, -1)),
            row(23, Coordinates(0, 2)),
            row(25, Coordinates(2, 2)),
        ) { number, coordinates ->
            coordinatesOf(number) shouldBe coordinates
        }
    }

    "part 1: square numbers and their taxi distance to number 1" {
        val spiralMemory = SpiralMemory()
        forAll(
            row(1, 0),
            row(2, 1),
            row(12, 3),
            row(23, 2),
            row(25, 4),
            row(1024, 31),
        ) { number, distance ->
            spiralMemory.part1(listOf("$number")) shouldBe distance
        }
    }

    fun sumOfAdjacentFilledNumbersOf(number: Int): Int = sequenceOfCoordinatesAndSums()
        .take(number)
        .last()
        .second

    "the sum of the adjacent filled numbers" {
        forAll(
            row(1, 1),
            row(2, 1),
            row(3, 2),
            row(4, 4),
            row(5, 5),
            row(23, 806),
        ) { number, sum ->
            sumOfAdjacentFilledNumbersOf(number) shouldBe sum
        }
    }

    "part 2: the first value written that is larger than the input" {
        SpiralMemory().part2(listOf("5")) shouldBe 10
    }
})
