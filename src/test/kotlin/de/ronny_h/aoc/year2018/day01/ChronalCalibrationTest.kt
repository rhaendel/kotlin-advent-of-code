package de.ronny_h.aoc.year2018.day01

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val ChronalCalibrationTest by testSuite {

    val input = """
        +1
        -2
        +3
        +1
    """.asList()

    test("part 1: the resulting frequency after all of the changes in frequency have been applied") {
        ChronalCalibration().part1(input) shouldBe 3
    }

    test("part 2: the first frequency the device reaches twice") {
        ChronalCalibration().part2(input) shouldBe 2
    }
}
