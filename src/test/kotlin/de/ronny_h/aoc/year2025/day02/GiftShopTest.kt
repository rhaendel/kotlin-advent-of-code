package de.ronny_h.aoc.year2025.day02

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val GiftShopTest by testSuite {

    val input = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.asList()

    test("id ranges can be parsed") {
        val input = listOf("11-22,95-115,998-1012,1188511880-1188511890")
        input.parseIdRanges() shouldBe listOf(11L..22L, 95L..115L, 998L..1012L, 1188511880L..1188511890L)
    }

    testSuite(
        "isSequenceRepeatedTwice",
        mapOf(
            11L to true,
            22L to true,
            123L to false,
            1234L to false,
            1188511885L to true,
        ),
    ) { id, isInvalid ->
        id.isSequenceRepeatedTwice() shouldBe isInvalid
    }

    testSuite(
        "isSequenceRepeatedAtLeastTwice",
        mapOf(
            11L to true,
            22L to true,
            111L to true,
            123L to false,
            1234L to false,
            1111111L to true,
            123123123L to true,
            1188511885L to true,
        ),
    ) { id, isInvalid ->
        id.isSequenceRepeatedAtLeastTwice() shouldBe isInvalid
    }

    test("part 1: the sum of all invalid IDs with sequences repeated exactly twice") {
        GiftShop().part1(input) shouldBe 1227775554L
    }

    test("part 2: the sum of all invalid IDs with sequences repeated at least twice") {
        GiftShop().part2(input) shouldBe 4174379265L
    }
}
