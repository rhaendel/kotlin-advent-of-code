package de.ronny_h.aoc.extensions

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
})
