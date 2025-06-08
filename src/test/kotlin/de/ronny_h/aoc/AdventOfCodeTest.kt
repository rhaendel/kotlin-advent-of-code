package de.ronny_h.aoc

import io.kotest.core.spec.style.StringSpec
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

class AdventOfCodeTest : StringSpec({
    val adventOfCode = object : AdventOfCode<Int>(2024, 99) {
        override fun part1(input: List<String>) = input.size
        override fun part2(input: List<String>) = input.size
    }

    "The day's file can be read and has the right number of lines" {
        adventOfCode.run(5, 5)
    }

    "Failure of part one is detected" {
        val exception = shouldThrow<IllegalStateException> {
            adventOfCode.run(4, 5)
        }
        exception.message shouldBe "Check failed."
    }

    "Failure of part two is detected" {
        val exception = shouldThrow<IllegalStateException> {
            adventOfCode.run(5, 4)
        }
        exception.message shouldBe "Check failed."
    }
})
