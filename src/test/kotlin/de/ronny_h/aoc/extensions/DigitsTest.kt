package de.ronny_h.aoc.extensions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DigitsTest : StringSpec({
    "Digits are counted" {
        forAll(
            row(0L, 1), row(123L, 3), row(1234567890, 10)
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
})
