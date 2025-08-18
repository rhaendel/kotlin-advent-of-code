package de.ronny_h.aoc.year2017.day10

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class KnotHashTest : StringSpec({

    "the knot hash of a small example" {
        sparseHashProduct(5, listOf(3, 4, 1, 5)) shouldBe 12
    }

    "part 1: the knot hash of a small example" {
        val input = listOf("3,4,1,5")
        KnotHash().part1(input) shouldBe "12"
    }

    "ASCII Codes of the input" {
        "1,2,3".toASCII() shouldBe listOf(49, 44, 50, 44, 51)
    }

    "reduce to dense hash" {
        listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22).reduceToDenseHash() shouldBe listOf(64)
    }

    "part 2: the real knot hash" {
        forAll(
            row(listOf(""), "a2582a3a0e66e6e86e3812dcb672a272"),
            row(listOf("AoC 2017"), "33efeb34ea91902bb2f59c9920caa6cd"),
            row(listOf("1,2,3"), "3efbe78a8d82f29979031a4aa0b16a9d"),
            row(listOf("1,2,4"), "63960835bcdc130f0b66d7ff4f6a5a8e"),
        ) { input, hash ->
            KnotHash().part2(input) shouldBe hash
        }
    }

    "the extracted knot hash function" {
        forAll(
            row("", "a2582a3a0e66e6e86e3812dcb672a272"),
            row("AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd"),
            row("1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d"),
            row("1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e"),
        ) { input, hash ->
            input.knotHash() shouldBe hash
        }
    }
})
