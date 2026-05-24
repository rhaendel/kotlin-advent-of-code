package de.ronny_h.aoc.year2017.day06

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val MemoryReallocationTest by testSuite {

    test("maxBy on an indexed list returns the index of the first max value") {
        listOf(3, 2, 3).withIndex().maxBy { it.value } shouldBe IndexedValue(0, 3)
    }

    test("redistribute 0, 2, 7, and 0") {
        listOf(0, 2, 7, 0).redistribute() shouldBe listOf(2, 4, 1, 2)
    }

    test("part 1: the number of redistribution cycles until a configuration repeats") {
        val input = listOf("0 2 7 0")
        MemoryReallocation().part1(input) shouldBe 5
    }

    test("part 2: the size of the loop") {
        val input = listOf("0 2 7 0")
        MemoryReallocation().part2(input) shouldBe 4
    }
}
