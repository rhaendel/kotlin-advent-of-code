package de.ronny_h.aoc.year2025.day02

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class GiftShopTest : StringSpec({

    val input = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.asList()

    "id ranges can be parsed" {
        val input = listOf("11-22,95-115,998-1012,1188511880-1188511890")
        input.parseIdRanges() shouldBe listOf(11L..22L, 95L..115L, 998L..1012L, 1188511880L..1188511890L)
    }

    "isSequenceRepeatedTwice" {
        forAll(
            row(11L, true),
            row(22L, true),
            row(123L, false),
            row(1234L, false),
            row(1188511885L, true),
        ) { id, isInvalid ->
            id.isSequenceRepeatedTwice() shouldBe isInvalid
        }
    }

    "isSequenceRepeatedAtLeastTwice" {
        forAll(
            row(11L, true),
            row(22L, true),
            row(111L, true),
            row(123L, false),
            row(1234L, false),
            row(1111111L, true),
            row(123123123L, true),
            row(1188511885L, true),
        ) { id, isInvalid ->
            id.isSequenceRepeatedAtLeastTwice() shouldBe isInvalid
        }
    }

    "part 1: the sum of all invalid IDs with sequences repeated exactly twice" {
        GiftShop().part1(input) shouldBe 1227775554L
    }

    "part 2: the sum of all invalid IDs with sequences repeated at least twice" {
        GiftShop().part2(input) shouldBe 4174379265L
    }
})
