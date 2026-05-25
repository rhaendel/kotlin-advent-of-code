package de.ronny_h.aoc.year2015.day19

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val MedicineForRudolphTest by testSuite {

    val replacements1 = listOf(
        "H => HO",
        "H => OH",
        "O => HH",
    )

    test("replacements can be parsed") {
        replacements1.parseReplacements() shouldBe listOf(
            Replacement("H", "HO"),
            Replacement("H", "OH"),
            Replacement("O", "HH"),
        )
    }

    test("part 1: there are 4 distinct molecules produced from HOH") {
        val input = replacements1 + listOf("", "HOH")
        MedicineForRudolph().part1(input) shouldBe 4
    }

    test("part 1: there are 7 distinct molecules produced from HOHOHO") {
        val input = replacements1 + listOf("", "HOHOHO")
        MedicineForRudolph().part1(input) shouldBe 7
    }

    val replacements2 = listOf(
        "e => H",
        "e => O",
        "H => HO",
        "H => OH",
        "O => HH",
    )

    test("part 2: there are at least 3 steps to produce HOH") {
        val input = replacements2 + listOf("", "HOH")
        MedicineForRudolph().part2(input) shouldBe 3
    }

    test("part 2: there are at least 3 steps to produce HOHOHO") {
        val input = replacements2 + listOf("", "HOHOHO")
        MedicineForRudolph().part2(input) shouldBe 6
    }
}
