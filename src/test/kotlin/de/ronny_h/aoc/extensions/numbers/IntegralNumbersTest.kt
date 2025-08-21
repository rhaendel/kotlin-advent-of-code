package de.ronny_h.aoc.extensions.numbers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class IntegralNumbersTest : StringSpec({

    "isIntegral is true for integral numbers" {
        forAll(
            row(0.0),
            row(7.000000000),
            row(42.0),
            row(70000001.0),
        ) { value ->
            value.isIntegral() shouldBe true
        }
    }

    "isIntegral is false for non-integral numbers" {
        forAll(
            row(0.5),
            row(0.0000001),
            row(7.0000001),
            row(7.9),
        ) { value ->
            value.isIntegral() shouldBe false
        }
    }

    "for an Int, isInt returns true" {
        "0".isInt() shouldBe true
        "42".isInt() shouldBe true
        "-42".isInt() shouldBe true
    }

    "for something that is not an Int, isInt returns false" {
        "true".isInt() shouldBe false
        "some random stuff".isInt() shouldBe false
    }

    "the sum of the first n natural numbers" {
        forAll(
            row(0, 0),
            row(1, 1),
            row(2, 3),
            row(3, 6),
            row(4, 10),
            row(5, 15),
            row(6, 21),
        ) { n, sum ->
            sumOfFirstNaturalNumbers(n) shouldBe sum
        }
    }

    "Long values that are small enough can be converted to Int" {
        forAll(
            row(0L, 0),
            row(42L, 42),
            row(Int.MAX_VALUE.toLong(), Int.MAX_VALUE),
            row(Int.MIN_VALUE.toLong(), Int.MIN_VALUE),
        ) { longValue, intValue ->
            longValue.toIntChecked() shouldBe intValue
        }
    }

    "Long values that are too big cannot be converted to Int" {
        forAll(
            row(Int.MAX_VALUE.toLong() + 1),
            row(Int.MIN_VALUE.toLong() - 1),
        ) { longValue ->
            shouldThrow<IllegalArgumentException> {
                longValue.toIntChecked()
            }
        }
    }
})
