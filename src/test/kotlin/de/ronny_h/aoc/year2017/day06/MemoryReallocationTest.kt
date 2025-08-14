package de.ronny_h.aoc.year2017.day06

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MemoryReallocationTest : StringSpec({

    "maxBy on an indexed list returns the index of the first max value" {
        listOf(3, 2, 3).withIndex().maxBy { it.value } shouldBe IndexedValue(0, 3)
    }

    "redistribute 0, 2, 7, and 0" {
        listOf(0, 2, 7, 0).redistribute() shouldBe listOf(2, 4, 1, 2)
    }

    "part 1: the number of redistribution cycles until a configuration repeats" {
        val input = listOf("0 2 7 0")
        MemoryReallocation().part1(input) shouldBe 5
    }

    "part 2: the size of the loop" {
        val input = listOf("0 2 7 0")
        MemoryReallocation().part2(input) shouldBe 4
    }
})
