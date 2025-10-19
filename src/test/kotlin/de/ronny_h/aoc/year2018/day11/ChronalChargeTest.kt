package de.ronny_h.aoc.year2018.day11

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ChronalChargeTest : StringSpec({

    "the power level of the fuel cell at coordinates in a grid with a given serial number" {
        forAll(
            row(3, 5, 8, 4),
            row(122, 79, 57, -5),
            row(217, 196, 39, 0),
            row(101, 153, 71, 4),
        ) { x, y, serialNumber, powerLevel ->
            PowerCellGrid(serialNumber)[y, x] shouldBe powerLevel
        }
    }

    "part 1: For grid serial number 18, the largest total 3x3 square has a top-left corner of 33,45" {
        ChronalCharge().part1(listOf("18")) shouldBe "33,45"
    }

    "part 1: For grid serial number 42, the largest 3x3 square's top-left is 21,61" {
        ChronalCharge().part1(listOf("42")) shouldBe "21,61"
    }

    "part 2: For grid serial number 18, the largest total square is 16x16 with a top-left corner of 90,269" {
        PowerCellGrid(18).findSquareWithLargestTotal(15..17) shouldBe "90,269,16"
    }
})
