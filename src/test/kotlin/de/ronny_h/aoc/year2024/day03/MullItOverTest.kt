package de.ronny_h.aoc.year2024.day03

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MullItOverTest : StringSpec({
    val smallInput1 = listOf("blamul(2,3)*x_mul(2,2)+mul(32,64]t")
    val smallInput2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    val mediumInput = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

    "part 1 - adding up all small input's mul instructions results in ten" {
        MullItOver().part1(smallInput1) shouldBe 10
    }

    "part 1 - adding up all medium input's mul instructions results in ten" {
        MullItOver().part1(mediumInput) shouldBe 161
    }

    "part 2 - adding up all small input's mul instructions with conditional statements results in 48" {
        MullItOver().part2(smallInput2) shouldBe 48
    }

    "part 2 - adding up all medium input's mul instructions with conditional statements results in 48" {
        MullItOver().part2(mediumInput) shouldBe 161
    }
})
