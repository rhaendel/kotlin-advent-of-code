package de.ronny_h.aoc.year2015.day06

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val FireHazardTest by testSuite {

    test("turn can be parsed") {
        FireHazard().parse("turn on 1,2 through 3,4") shouldBe SwitchLight(
            Action.ON, Coordinates(2, 1), Coordinates(
                4,
                3
            )
        )
        FireHazard().parse("turn off 10,20 through 30,40") shouldBe SwitchLight(
            Action.OFF, Coordinates(20, 10), Coordinates(
                40,
                30
            )
        )
        FireHazard().parse("toggle 0,0 through 999,999") shouldBe SwitchLight(
            Action.TOGGLE, Coordinates(0, 0), Coordinates(
                999,
                999
            )
        )
    }

    test("part 1: turn on every light") {
        FireHazard().part1(listOf("turn on 0,0 through 999,999")) shouldBe 1_000_000
    }

    test("part 1: toggle the first line") {
        FireHazard().part1(listOf("toggle 0,0 through 999,0")) shouldBe 1000
    }

    test("part 2: a brightness of one") {
        FireHazard().part2(listOf("turn on 0,0 through 0,0")) shouldBe 1
    }
    test("part 2: a brightness of 2000000") {
        FireHazard().part2(listOf("toggle 0,0 through 999,999")) shouldBe 2_000_000
    }
}
