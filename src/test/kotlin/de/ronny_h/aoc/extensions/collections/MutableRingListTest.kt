package de.ronny_h.aoc.extensions.collections

import de.ronny_h.aoc.extensions.collections.MutableRingList.Companion.mutableRingListOf
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MutableRingListTest : StringSpec({

    "a MutableRingList<Char> can be created from a variable amounts of Chars and toString() generates a nice representation" {
        mutableRingListOf('a', 'b', 'c', 'd', 'e').toString() shouldBe "[a, b, c, d, e]"
    }

    "a MutableRingList<Char> can be created from a String and toJoinedString() recreates that String" {
        mutableRingListOf("abcde").toJoinedString() shouldBe "abcde"
    }

    "get(index) returns the element at index" {
        mutableRingListOf("abcde").get(2) shouldBe 'c'
        mutableRingListOf("abcde")[2] shouldBe 'c'
    }

    "set(index, value) sets the value at index" {
        val list = mutableRingListOf("abcde")

        list.set(2, 'z')
        list[2] shouldBe 'z'

        list[0] = 'y'
        list[0] shouldBe 'y'
    }

    "shiftRight() moves elements from the end to the front" {
        mutableRingListOf("abcde").shiftRight(1).toJoinedString() shouldBe "eabcd"

        mutableRingListOf("abcde").shiftRight(2).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(1).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftRight(3).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(2).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftRight(5).toJoinedString() shouldBe "abcde"
    }

    "get after shiftRight returns the shifted elements" {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] shouldBe 'e'
        list[4] shouldBe 'd'
    }

    "set after shiftRight sets the element at the shifted position" {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] = 'x'
        list[0] shouldBe 'x'
        list[4] shouldBe 'd'
    }

    "toList returns the modified state" {
        val list = mutableRingListOf("abcde")
            .shiftRight(2)
            .swap(0, 1)
        list.toList() shouldBe listOf('e', 'd', 'a', 'b', 'c')
    }

    "swap(indexA, indexB) exchanges the elements at the given indexes" {
        mutableRingListOf("abcde").swap(3, 4).toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap(0, 4).toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap(3, 3).toJoinedString() shouldBe "abcde"
    }

    "swap(elemA, elemB) exchanges the elements by their name" {
        mutableRingListOf("abcde").swap('d', 'e').toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap('a', 'e').toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap('c', 'c').toJoinedString() shouldBe "abcde"
    }
})
