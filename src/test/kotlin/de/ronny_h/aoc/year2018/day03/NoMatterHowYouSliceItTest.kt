package de.ronny_h.aoc.year2018.day03

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val NoMatterHowYouSliceItTest by testSuite {

    val input = """
        #1 @ 1,3: 4x4
        #2 @ 3,1: 4x4
        #3 @ 5,5: 2x2
    """.asList()

    test("input can be parsed") {
        input.parseClaims() shouldBe listOf(
            Claim(1, 1, 3, 4, 4),
            Claim(2, 3, 1, 4, 4),
            Claim(3, 5, 5, 2, 2),
        )
    }

    test("part 1: the number of square inches of fabric within two or more claims") {
        NoMatterHowYouSliceIt().part1(input) shouldBe 4
    }

    test("part 2: the ID of the claim that doesn't overlap") {
        NoMatterHowYouSliceIt().part2(input) shouldBe 3
    }
}
