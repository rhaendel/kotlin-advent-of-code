package de.ronny_h.aoc.year24.day18

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RAMRunTest : StringSpec({
    val input = listOf(
        "5,4", "4,2", "4,5", "3,0", "2,1", "6,3", "2,4", "1,5", "0,6", "3,3", "2,6", "5,1", "1,2",
        "5,5", "2,5", "6,5", "1,4", "0,4", "6,4", "1,1", "6,1", "1,0", "0,5", "1,6", "2,0",
    )

    "part 1: The minimum number of steps needed to reach the exit after the first 1024 bytes" {
        RAMRun().part1Small(input.subList(0, 12)) shouldBe 22
    }

    "part 2: The coordinates of the first byte that will prevent the exit from being reachable" {
        RAMRun().part2Small(input) shouldBe "6,1"
    }
})
