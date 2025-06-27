package de.ronny_h.aoc.year2024.day11

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PlutonianPebblesTest : StringSpec({
    val smallInput = """
        125 17
    """.asList()

    "part 1: The number of stones after blinking 25 times" {
        PlutonianPebbles().part1(smallInput) shouldBe 55312
    }

    "part 2: The number of stones after blinking 75 times" {
        PlutonianPebbles().part2(smallInput) shouldBe 65601038650482
    }
})
