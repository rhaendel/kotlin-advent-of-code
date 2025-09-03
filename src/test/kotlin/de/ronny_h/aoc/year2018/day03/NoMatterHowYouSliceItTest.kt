package de.ronny_h.aoc.year2018.day03

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NoMatterHowYouSliceItTest : StringSpec({

    val input = """
        #1 @ 1,3: 4x4
        #2 @ 3,1: 4x4
        #3 @ 5,5: 2x2
    """.asList()

    "input can be parsed" {
        input.parseClaims() shouldBe listOf(
            Claim(1, 1, 3, 4, 4),
            Claim(2, 3, 1, 4, 4),
            Claim(3, 5, 5, 2, 2),
        )
    }

    "part 1: the number of square inches of fabric within two or more claims" {
        NoMatterHowYouSliceIt().part1(input) shouldBe 4
    }

    "part 2: the ID of the claim that doesn't overlap" {
        NoMatterHowYouSliceIt().part2(input) shouldBe 3
    }
})
