package de.ronny_h.aoc.year24.day21

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class KeypadConundrumTest : StringSpec({
    val input = """
        029A
        980A
        179A
        456A
        379A
    """.asList()

    "make the 1st robot type 029A" {
        val numericKeypad = Keypad(numericKeypadLayout)
        numericKeypad.moveTo('0') shouldBe "<A"
        numericKeypad.moveTo('2') shouldBe "^A"
        numericKeypad.moveTo('9') shouldBe "^^>A"
        numericKeypad.moveTo('A') shouldBe "vvvA"
    }

    "make the 2nd robot type ^^>A" {
        val directionalKeypad = Keypad(directionalKeypadLayout)
        directionalKeypad.moveTo('^') shouldBe "<A"
        directionalKeypad.moveTo('^') shouldBe "A"
        directionalKeypad.moveTo('>') shouldBe ">vA"
        directionalKeypad.moveTo('A') shouldBe "^A"
    }

    "part 1: The sum of the complexities of the five codes" {
        KeypadConundrum().part1(input) shouldBe 126384
    }
})
