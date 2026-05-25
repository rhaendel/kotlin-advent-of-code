package de.ronny_h.aoc.year2017.day17

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val SpinlockTest by testSuite {

    test("part 1: the value after the last value written (2017)") {
        Spinlock().part1(listOf("3")) shouldBe 638
    }

    test("part 2: the value on index 1 (the one after value 0) the moment 50000000 is inserted") {
        Spinlock().part2(listOf("3")) shouldBe 1222153
    }
}
