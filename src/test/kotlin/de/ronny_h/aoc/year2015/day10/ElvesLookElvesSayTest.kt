package de.ronny_h.aoc.year2015.day10

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ElvesLookElvesSayTest by testSuite {

    val day10 = ElvesLookElvesSay()

    test("1 becomes 11") {
        day10.lookAndSay("1") shouldBe "11"
    }

    test("11 becomes 21") {
        day10.lookAndSay("11") shouldBe "21"
    }

    test("21 becomes 1211") {
        day10.lookAndSay("21") shouldBe "1211"
    }

    test("1211 becomes 111221") {
        day10.lookAndSay("1211") shouldBe "111221"
    }

    test("111221 becomes 312211") {
        day10.lookAndSay("111221") shouldBe "312211"
    }

    test("part 1: The length of 40 times lookAndSay for 1") {
        day10.part1(listOf("1")) shouldBe 82350
    }

    test("part 2: The length of 50 times lookAndSay for 1") {
        day10.part2(listOf("1")) shouldBe 1166642
    }
}
