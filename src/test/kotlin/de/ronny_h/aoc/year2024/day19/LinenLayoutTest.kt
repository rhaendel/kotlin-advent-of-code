package de.ronny_h.aoc.year2024.day19

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LinenLayoutTest : StringSpec({
    val input = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.asList()

    "part 1: Number of possible designs" {
        LinenLayout().part1(input) shouldBe 6
    }

    "part 2: Number of different ways to achieve all possible designs" {
        LinenLayout().part2(input) shouldBe 16
    }
})
