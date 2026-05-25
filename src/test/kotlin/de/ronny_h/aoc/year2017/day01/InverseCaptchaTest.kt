package de.ronny_h.aoc.year2017.day01

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val InverseCaptchaTest by testSuite {

    testSuite("part 1: all the examples given") {
        mapOf(
            "1122" to 3,
            "1111" to 4,
            "1234" to 0,
            "91212129" to 9,
        ).forEach { (sequence, sum) ->
            test("$sequence, $sum") {
                InverseCaptcha().part1(listOf(sequence)) shouldBe sum
            }
        }
    }

    testSuite("part 2: all the examples given") {
        mapOf(
            "1212" to 6,
            "1221" to 0,
            "123425" to 4,
            "123123" to 12,
            "12131415" to 4,
        ).forEach { (sequence, sum) ->
            test("$sequence, $sum") {
                InverseCaptcha().part2(listOf(sequence)) shouldBe sum
            }
        }
    }
}
