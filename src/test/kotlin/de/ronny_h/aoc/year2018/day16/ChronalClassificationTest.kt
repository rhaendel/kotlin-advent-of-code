package de.ronny_h.aoc.year2018.day16

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ChronalClassificationTest : StringSpec({

    val input = """
            Before: [3, 2, 1, 1]
            9 2 1 2
            After:  [3, 2, 2, 1]

            Before: [3, 2, 1, 1]
            9 2 1 2
            After:  [3, 2, 2, 1]



            7 3 2 0
            7 2 1 1
            7 1 0 3
            8 1 0 1
        """.asList()

    "input can be parsed" {
        input.parseCPUSamples() shouldBe listOf(
            CPUSample(listOf(3, 2, 1, 1), 9, 2, 1, 2, listOf(3, 2, 2, 1)),
            CPUSample(listOf(3, 2, 1, 1), 9, 2, 1, 2, listOf(3, 2, 2, 1)),
        )
    }

    "the given sample behaves like three opcodes" {
        WristDevice().behaveLikeNumberOfOpcodes(
            listOf(
                CPUSample(listOf(3, 2, 1, 1), 9, 2, 1, 2, listOf(3, 2, 2, 1))
            )
        ) shouldBe listOf(3)
    }

    "part 1: both of the given samples behave like three opcodes" {
        ChronalClassification().part1(input) shouldBe 2
    }

    "part 2" {
        val input = listOf("")
        ChronalClassification().part2(input) shouldBe 0
    }
})
