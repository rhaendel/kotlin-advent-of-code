package de.ronny_h.aoc.year2025.day04

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PrintingDepartmentTest : StringSpec({

    val input = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.asList()

    "part 1: the number of rolls of paper that can be accessed by a forklift" {
        PrintingDepartment().part1(input) shouldBe 13
    }

    "part 2: the number of rolls of paper in total that can be removed by forklifts" {
        PrintingDepartment().part2(input) shouldBe 43
    }
})
