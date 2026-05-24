package de.ronny_h.aoc.year2017.day10

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val KnotHashTest by testSuite {

    test("the knot hash of a small example") {
        sparseHashProduct(5, listOf(3, 4, 1, 5)) shouldBe 12
    }

    test("part 1: the knot hash of a small example") {
        val input = listOf("3,4,1,5")
        KnotHash().part1(input) shouldBe "12"
    }

    test("ASCII Codes of the input") {
        "1,2,3".toASCII() shouldBe listOf(49, 44, 50, 44, 51)
    }

    test("reduce to dense hash") {
        listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22).reduceToDenseHash() shouldBe listOf(64)
    }

    testSuite("part 2: the real knot hash") {
        mapOf(
            listOf("") to "a2582a3a0e66e6e86e3812dcb672a272",
            listOf("AoC 2017") to "33efeb34ea91902bb2f59c9920caa6cd",
            listOf("1,2,3") to "3efbe78a8d82f29979031a4aa0b16a9d",
            listOf("1,2,4") to "63960835bcdc130f0b66d7ff4f6a5a8e",
        ).forEach { (input, hash) ->
            test("$input, $hash") {
                KnotHash().part2(input) shouldBe hash
            }
        }
    }

    testSuite("the extracted knot hash function") {
        mapOf(
            "" to "a2582a3a0e66e6e86e3812dcb672a272",
            "AoC 2017" to "33efeb34ea91902bb2f59c9920caa6cd",
            "1,2,3" to "3efbe78a8d82f29979031a4aa0b16a9d",
            "1,2,4" to "63960835bcdc130f0b66d7ff4f6a5a8e",
        ).forEach { (input, hash) ->
            test("$input, $hash") {
                input.knotHash() shouldBe hash
            }
        }
    }
}
