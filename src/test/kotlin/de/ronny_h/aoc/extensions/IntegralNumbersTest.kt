package de.ronny_h.aoc.extensions

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
})
