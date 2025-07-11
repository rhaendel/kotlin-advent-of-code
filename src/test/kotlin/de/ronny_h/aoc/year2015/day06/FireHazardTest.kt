package de.ronny_h.aoc.year2015.day06

import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FireHazardTest : StringSpec({

    "turn can be parsed" {
        FireHazard().parse("turn on 1,2 through 3,4") shouldBe SwitchLight(Action.ON, Coordinates(1,2), Coordinates(3,4))
        FireHazard().parse("turn off 10,20 through 30,40") shouldBe SwitchLight(Action.OFF, Coordinates(10,20), Coordinates(30,40))
        FireHazard().parse("toggle 0,0 through 999,999") shouldBe SwitchLight(Action.TOGGLE, Coordinates(0,0), Coordinates(999,999))
    }

    "part 1: turn on every light" {
        FireHazard().part1(listOf("turn on 0,0 through 999,999")) shouldBe 1_000_000
    }

    "part 1: toggle the first line" {
        FireHazard().part1(listOf("toggle 0,0 through 999,0")) shouldBe 1000
    }

    "part 2: a brightness of one" {
        FireHazard().part2(listOf("turn on 0,0 through 0,0")) shouldBe 1
    }
    "part 2: a brightness of 2000000" {
        FireHazard().part2(listOf("toggle 0,0 through 999,999")) shouldBe 2_000_000
    }
})
