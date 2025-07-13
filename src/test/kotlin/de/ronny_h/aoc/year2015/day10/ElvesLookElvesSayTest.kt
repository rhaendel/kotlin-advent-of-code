package de.ronny_h.aoc.year2015.day10

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ElvesLookElvesSayTest : StringSpec({

    val day10 = ElvesLookElvesSay()

    "1 becomes 11" {
        day10.lookAndSay("1") shouldBe "11"
    }

    "11 becomes 21" {
        day10.lookAndSay("11") shouldBe "21"
    }

    "21 becomes 1211" {
        day10.lookAndSay("21") shouldBe "1211"
    }

    "1211 becomes 111221" {
        day10.lookAndSay("1211") shouldBe "111221"
    }

    "111221 becomes 312211" {
        day10.lookAndSay("111221") shouldBe "312211"
    }

    "part 1: The length of 40 times lookAndSay for 1" {
        day10.part1(listOf("1")) shouldBe 82350
    }

    "part 2: The length of 50 times lookAndSay for 1" {
        day10.part2(listOf("1")) shouldBe 1166642
    }
})
