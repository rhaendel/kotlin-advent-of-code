package de.ronny_h.aoc.year2018.day11

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ChronalChargeTest by testSuite {

    data class Row(val x: Int, val y: Int, val serialNumber: Int, val powerLevel: Int)

    testSuite("the power level of the fuel cell at coordinates in a grid with a given serial number") {
        listOf(
            Row(3, 5, 8, 4),
            Row(122, 79, 57, -5),
            Row(217, 196, 39, 0),
            Row(101, 153, 71, 4),
        ).forEach { (x, y, serialNumber, powerLevel) ->
            test("$x, $y, $serialNumber, $powerLevel") {
                PowerCellGrid(serialNumber)[x, y] shouldBe powerLevel
            }
        }
    }

    test("part 1: For grid serial number 18, the largest total 3x3 square has a top-left corner of 33,45") {
        ChronalCharge().part1(listOf("18")) shouldBe "33,45"
    }

    test("part 1: For grid serial number 42, the largest 3x3 square's top-left is 21,61") {
        ChronalCharge().part1(listOf("42")) shouldBe "21,61"
    }

    test("part 2: For grid serial number 18, the largest total square is 16x16 with a top-left corner of 90,269") {
        PowerCellGrid(18).findSquareWithLargestTotal(15..17) shouldBe "90,269,16"
    }
}
