package de.ronny_h.aoc.extensions.numbers

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

val IntegralNumbersTest by testSuite {

    testSuite(
        "isIntegral is true for integral numbers",
        listOf(0.0, 7.000000000, 42.0, 70000001.0),
    ) { value ->
        value.isIntegral() shouldBe true
    }

    testSuite(
        "isIntegral is false for non-integral numbers",
        listOf(0.5, 0.0000001, 7.0000001, 7.9),
    ) { value ->
        value.isIntegral() shouldBe false
    }

    test("for an Int, isInt returns true") {
        "0".isInt() shouldBe true
        "42".isInt() shouldBe true
        "-42".isInt() shouldBe true
    }

    test("for something that is not an Int, isInt returns false") {
        "true".isInt() shouldBe false
        "some random stuff".isInt() shouldBe false
    }

    testSuite(
        "the sum of the first n natural numbers",
        mapOf(
            0 to 0,
            1 to 1,
            2 to 3,
            3 to 6,
            4 to 10,
            5 to 15,
            6 to 21,
        ),
    ) { n, sum ->
        sumOfFirstNaturalNumbers(n) shouldBe sum
    }

    testSuite(
        "Long values that are small enough can be converted to Int",
        mapOf(
            0L to 0,
            42L to 42,
            Int.MAX_VALUE.toLong() to Int.MAX_VALUE,
            Int.MIN_VALUE.toLong() to Int.MIN_VALUE,
        ),
    ) { longValue, intValue ->
        longValue.toIntChecked() shouldBe intValue
    }

    testSuite(
        "Long values that are too big cannot be converted to Int",
        listOf(Int.MAX_VALUE.toLong() + 1, Int.MIN_VALUE.toLong() - 1),
    ) { longValue ->
        shouldThrow<IllegalArgumentException> {
            longValue.toIntChecked()
        }
    }

    testSuite(
        "The square of Ints",
        mapOf(
            0 to 0,
            -1 to 1,
            1 to 1,
            -100 to 10_000,
            100 to 10_000,
            -10_000 to 100_000_000,
            10_000 to 100_000_000,
        ),
    ) { a, result ->
        a.squared() shouldBe result
    }

    testSuite(
        "The square of Longs",
        mapOf(
            0L to 0L,
            -1L to 1L,
            1L to 1L,
            -100L to 10_000L,
            100L to 10_000L,
            -1_000_000_000L to 1_000_000_000_000_000_000L,
            1_000_000_000L to 1_000_000_000_000_000_000L,
        ),
    ) { a, result ->
        a.squared() shouldBe result
    }

    testSuite(
        "The power of Ints",
        listOf(
            Triple(0, 0, 1),
            Triple(1, 1, 1),
            Triple(1, 2, 1),
            Triple(10, 0, 1),
            Triple(10, 1, 10),
            Triple(10, 2, 100),
            Triple(10, 5, 100000),
        ),
    ) { (number, power, result) ->
        number.pow(power) shouldBe result
    }
}
