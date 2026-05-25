package de.ronny_h.aoc.extensions.numbers

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

val DigitsTest by testSuite {
    testSuite(
        "Long Digits are counted",
        mapOf(
            0L to 1, 123L to 3, 1234567890L to 10
        ),
    ) { number, digits ->
        number.digitCount() shouldBe digits
    }

    testSuite(
        "Int Digits are counted",
        mapOf(
            0 to 1, 123 to 3, 1234567890 to 10
        ),
    ) { number, digits ->
        number.digitCount() shouldBe digits
    }

    testSuite(
        "The ones digit is returned",
        mapOf(
            0L to 0,
            123L to 3,
            15887950L to 0,
            16495136L to 6,
            527345L to 5,
            -123L to 3,
            -15887950L to 0,
            -16495136L to 6,
            -527345L to 5,
        ),
    ) { number, ones ->
        number.onesDigit() shouldBe ones
    }

    testSuite(
        "The nth digit is returned",
        listOf(
            Triple(0, 1, 0),
            Triple(0, 2, 0),
            Triple(54321, 1, 1),
            Triple(54321, 2, 2),
            Triple(54321, 3, 3),
            Triple(54321, 4, 4),
            Triple(54321, 5, 5),
            Triple(54321, 6, 0),
        )
    ) { (number, n, digit) ->
        number.digit(n) shouldBe digit
    }

    test("toBoolean converts a char digit to Boolean") {
        '0'.toBoolean() shouldBe false
        '1'.toBoolean() shouldBe true
    }

    test("toBoolean throws an Exception for illegal digits") {
        shouldThrow<IllegalStateException> {
            '2'.toBoolean()
        }
    }

    test("toDigit converts a Boolean to a digit String") {
        true.toDigit() shouldBe "1"
        false.toDigit() shouldBe "0"
    }

    testSuite(
        "the digits of an Int",
        mapOf(
            0 to listOf(0),
            1 to listOf(1),
            123 to listOf(3, 2, 1),
            1234567 to listOf(7, 6, 5, 4, 3, 2, 1),
        ),
    ) { int, digits ->
        int.digits().toList() shouldBe digits
    }

    testSuite(
        "the reversed digits of an Int",
        mapOf(
            0 to listOf(0),
            1 to listOf(1),
            123 to listOf(1, 2, 3),
            1234567 to listOf(1, 2, 3, 4, 5, 6, 7),
        ),
    ) { int, digits ->
        int.digitsReversed().toList() shouldBe digits
    }
}
