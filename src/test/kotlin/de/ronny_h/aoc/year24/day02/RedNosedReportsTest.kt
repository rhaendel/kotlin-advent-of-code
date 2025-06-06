package de.ronny_h.aoc.year24.day02

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class RedNosedReportsTest : StringSpec({

    val smallReports1 = listOf("5 4 5", "1 2 3", "1 1 2", "1 5 6", "7 6 5")
    val smallReports2 = listOf("1 2 6 7") + smallReports1

    val mediumReports = listOf(
       " 7 6 4 2 1",
       " 1 2 7 8 9",
       " 9 7 6 2 1",
       " 1 3 2 4 5",
       " 8 6 4 4 1",
       " 1 3 6 7 9",
    )

    "part 1 - in the small report list, two are safe" {
        RedNosedReports().part1(smallReports1) shouldBe 2
    }

    "part 1 - in the medium report list, two are safe" {
        RedNosedReports().part1(mediumReports) shouldBe 2
    }

    "part 2 - in the small list, five dampened reports are safe" {
        RedNosedReports().part2(smallReports2) shouldBe 5
    }

    "part 2 - in the medium list, four dampened reports are safe" {
        RedNosedReports().part2(mediumReports) shouldBe 4
    }
})
