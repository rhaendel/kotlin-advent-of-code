package de.ronny_h.aoc.year2017.day01

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class InverseCaptchaTest : StringSpec({

    "part 1: all the examples given" {
        forAll(
            row("1122", 3),
            row("1111", 4),
            row("1234", 0),
            row("91212129", 9),
        ) { sequence, sum ->
            InverseCaptcha().part1(listOf(sequence)) shouldBe sum
        }
    }

    "part 2: all the examples given" {
        forAll(
            row("1212", 6),
            row("1221", 0),
            row("123425", 4),
            row("123123", 12),
            row("12131415", 4),
        ) { sequence, sum ->
            InverseCaptcha().part2(listOf(sequence)) shouldBe sum
        }
    }
})
