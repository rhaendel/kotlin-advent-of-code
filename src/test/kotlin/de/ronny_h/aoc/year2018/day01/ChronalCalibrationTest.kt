package de.ronny_h.aoc.year2018.day01

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ChronalCalibrationTest : StringSpec({

    val input = """
        +1
        -2
        +3
        +1
    """.asList()

    "part 1: the resulting frequency after all of the changes in frequency have been applied" {
        ChronalCalibration().part1(input) shouldBe 3
    }

    "part 2: the first frequency the device reaches twice" {
        ChronalCalibration().part2(input) shouldBe 2
    }
})
