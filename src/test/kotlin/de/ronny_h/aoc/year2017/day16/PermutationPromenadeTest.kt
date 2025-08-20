package de.ronny_h.aoc.year2017.day16

import de.ronny_h.aoc.year2017.day16.DanceMove.*
import de.ronny_h.aoc.year2017.day16.MutableRingList.Companion.mutableRingListOf
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PermutationPromenadeTest : StringSpec({

    val input = listOf("s1,x3/4,pe/b")

    "input can be parsed" {
        input.parseDanceMoves() shouldBe listOf(Spin(1), Exchange(3, 4), Partner('e', 'b'))
    }

    "a MutableRingList<Char> can be created from a variable amounts of Chars and toString() generates a nice representation" {
        mutableRingListOf('a', 'b', 'c', 'd', 'e').toString() shouldBe "[a, b, c, d, e]"
    }

    "a MutableRingList<Char> can be created from a String and toJoinedString() recreates that String" {
        mutableRingListOf("abcde").toJoinedString() shouldBe "abcde"
    }

    "MutableRingList: get" {
        mutableRingListOf("abcde").get(2) shouldBe 'c'
        mutableRingListOf("abcde")[2] shouldBe 'c'
    }

    "MutableRingList: set" {
        val list = mutableRingListOf("abcde")

        list.set(2, 'z')
        list[2] shouldBe 'z'

        list[0] = 'y'
        list[0] shouldBe 'y'
    }

    "MutableRingList: programs can be spun" {
        mutableRingListOf("abcde").shiftRight(1).toJoinedString() shouldBe "eabcd"

        mutableRingListOf("abcde").shiftRight(2).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(1).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftRight(3).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(2).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftRight(5).toJoinedString() shouldBe "abcde"
    }

    "MutableRingList: get after spin" {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] shouldBe 'e'
        list[4] shouldBe 'd'
    }

    "MutableRingList: set after spin" {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] = 'x'
        list[0] shouldBe 'x'
        list[4] shouldBe 'd'
    }

    "MutableRingList: toList returns the modified state" {
        val list = mutableRingListOf("abcde")
            .shiftRight(2)
            .swap(0, 1)
        list.toList() shouldBe listOf('e', 'd', 'a', 'b', 'c')
    }

    "programs can be exchanged at positions" {
        mutableRingListOf("abcde").swap(3, 4).toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap(0, 4).toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap(3, 3).toJoinedString() shouldBe "abcde"
    }

    "programs can be exchanged by their name" {
        mutableRingListOf("abcde").swap('d', 'e').toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap('a', 'e').toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap('c', 'c').toJoinedString() shouldBe "abcde"
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
