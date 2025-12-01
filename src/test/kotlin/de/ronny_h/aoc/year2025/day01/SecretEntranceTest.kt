package de.ronny_h.aoc.year2025.day01

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2025.day01.RotationDirection.LEFT
import de.ronny_h.aoc.year2025.day01.RotationDirection.RIGHT
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SecretEntranceTest : StringSpec({

    "input can be parsed" {
        val input = """
            L68
            L30
            R48
        """.asList()

        input.parse() shouldBe listOf(
            Rotation(LEFT, 68),
            Rotation(LEFT, 30),
            Rotation(RIGHT, 48),
        )
    }

    val input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.asList()

    "part 1: the password to open the door" {
        SecretEntrance().part1(input) shouldBe 3
    }

    "part 2: the password to open the door with method 0x434C49434B" {
        SecretEntrance().part2(input) shouldBe 6
    }

    "part 2 with 2x overflow" {
        SecretEntrance().part2(listOf("L200")) shouldBe 2
    }

    "part 2 with 2x underflow" {
        SecretEntrance().part2(listOf("R200")) shouldBe 2
    }

    "part 2 with an exact 0 and 2x overflow" {
        SecretEntrance().part2(listOf("L50", "L200")) shouldBe 3
    }

    "part 2 with an exact 0 and 2x underflow" {
        SecretEntrance().part2(listOf("L50", "R200")) shouldBe 3
    }

    "part 2 with an exact 0 and 2x overflow not landing on 0" {
        SecretEntrance().part2(listOf("L50", "L199")) shouldBe 2
    }

    "part 2 with an exact 0 and 2x underflow not landing on 0" {
        SecretEntrance().part2(listOf("L50", "R199")) shouldBe 2
    }
})
