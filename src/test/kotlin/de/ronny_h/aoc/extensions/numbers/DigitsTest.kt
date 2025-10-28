package de.ronny_h.aoc.extensions.numbers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DigitsTest : StringSpec({
    "Long Digits are counted" {
        forAll(
            row(0L, 1), row(123L, 3), row(1234567890L, 10)
        ) { number, digits ->
            number.digitCount() shouldBe digits
        }
    }

    "Int Digits are counted" {
        forAll(
            row(0, 1), row(123, 3), row(1234567890, 10)
        ) { number, digits ->
            number.digitCount() shouldBe digits
        }
    }

    "The ones digit is returned" {
        forAll(
            row(0, 0),
            row(123L, 3),
            row(15887950, 0),
            row(16495136, 6),
            row(527345, 5),
            row(-123L, 3),
            row(-15887950, 0),
            row(-16495136, 6),
            row(-527345, 5),
        ) { number, ones ->
            number.onesDigit() shouldBe ones
        }
    }

    "The nth digit is returned" {
        forAll(
            row(0, 1, 0),
            row(0, 2, 0),
            row(54321, 1, 1),
            row(54321, 2, 2),
            row(54321, 3, 3),
            row(54321, 4, 4),
            row(54321, 5, 5),
            row(54321, 6, 0),
        ) { number, n, digit ->
            number.digit(n) shouldBe digit
        }
    }

    "toBoolean converts a char digit to Boolean" {
        '0'.toBoolean() shouldBe false
        '1'.toBoolean() shouldBe true
    }

    "toBoolean throws an Exception for illegal digits" {
        shouldThrow<IllegalStateException> {
            '2'.toBoolean()
        }
    }

    "toDigit converts a Boolean to a digit String" {
        true.toDigit() shouldBe "1"
        false.toDigit() shouldBe "0"
    }

    "the digits of an Int" {
        forAll(
            row(0, listOf(0)),
            row(1, listOf(1)),
            row(123, listOf(3, 2, 1)),
            row(1234567, listOf(7, 6, 5, 4, 3, 2, 1)),
        ) { int, digits ->
            int.digits().toList() shouldBe digits
        }
    }

    "the reversed digits of an Int" {
        forAll(
            row(0, listOf(0)),
            row(1, listOf(1)),
            row(123, listOf(1, 2, 3)),
            row(1234567, listOf(1, 2, 3, 4, 5, 6, 7)),
        ) { int, digits ->
            int.digitsReversed().toList() shouldBe digits
        }
    }
})
