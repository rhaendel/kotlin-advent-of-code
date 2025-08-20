package de.ronny_h.aoc.year2017.day16

import de.ronny_h.aoc.year2017.day16.DanceMove.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PermutationPromenadeTest : StringSpec({

    val input = listOf("s1,x3/4,pe/b")

    "input can be parsed" {
        input.parseDanceMoves() shouldBe listOf(Spin(1), Exchange(3, 4), Partner('e', 'b'))
    }

    "part 1: The order of the programs after their dance" {
        PermutationPromenade().doTheDance("abcde", input) shouldBe "baedc"
        PermutationPromenade().part1(input) shouldBe "paedcbfghijklmno"
    }

    "part 2: The order of the programs after one billion repetitions of their dance" {
        PermutationPromenade().doTheDance("abcde", input, 1_000_000_000) shouldBe "abcde"
        PermutationPromenade().part2(input) shouldBe "ghidjklmnopabcef"
    }
})
