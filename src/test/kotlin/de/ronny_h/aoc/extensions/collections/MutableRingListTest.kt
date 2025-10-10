package de.ronny_h.aoc.extensions.collections

import de.ronny_h.aoc.extensions.collections.MutableRingList.Companion.mutableRingListOf
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MutableRingListTest : StringSpec({

    "a MutableRingList<Char> can be created from a variable amount of Chars and toString() generates a nice representation" {
        mutableRingListOf('a', 'b', 'c', 'd', 'e').toString() shouldBe "[a, b, c, d, e]"
    }

    "a MutableRingList<Char> can be created from a String and toJoinedString() recreates that String" {
        mutableRingListOf("abcde").toJoinedString() shouldBe "abcde"
    }

    "a MutableRingList can be created with an initializer function" {
        MutableRingList(6) { it }.toJoinedString() shouldBe "012345"
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

    "insert() inserts the value at the currently first position" {
        val list = mutableRingListOf("abc")

        list.insert('x')
        list.toJoinedString() shouldBe "xabc"

        list.shiftLeft(2)
        list.insert('y')
        list.toJoinedString() shouldBe "ybcxa"
    }

    "removeFirst removes the first element and shifts the remaining ones 1 position left" {
        val list = mutableRingListOf("abcde")

        val removed = list.removeFirst()

        removed shouldBe 'a'
        list.toJoinedString() shouldBe "bcde"
    }

    "removeFirst on a list shifted left" {
        val list = mutableRingListOf("abcde")

        list.shiftLeft(2)
        val removed = list.removeFirst()

        removed shouldBe 'c'
        list.toJoinedString() shouldBe "deab"
    }

    "removeFirst on a list shifted right" {
        val list = mutableRingListOf("abcde")

        list.shiftRight(2)
        val removed = list.removeFirst()

        removed shouldBe 'd'
        list.toJoinedString() shouldBe "eabc"
    }

    "shiftRight() moves elements from the end to the front" {
        mutableRingListOf("abcde").shiftRight(1).toJoinedString() shouldBe "eabcd"

        mutableRingListOf("abcde").shiftRight(2).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(1).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftRight(3).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(2).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftRight(5).toJoinedString() shouldBe "abcde"

        mutableRingListOf("abcde").shiftRight(7).toJoinedString() shouldBe "deabc"
    }

    "shiftLeft() moves elements from the front to the end" {
        mutableRingListOf("abcde").shiftLeft(1).toJoinedString() shouldBe "bcdea"

        mutableRingListOf("abcde").shiftLeft(2).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftLeft(1).shiftLeft(1).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftLeft(3).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftLeft(1).shiftLeft(2).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftLeft(5).toJoinedString() shouldBe "abcde"

        mutableRingListOf("abcde").shiftLeft(7).toJoinedString() shouldBe "cdeab"
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

    "reversSubList on an unmodified ringList" {
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(1, 3).toList() shouldBe listOf(1, 4, 3, 2, 5, 6)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(1, 4).toList() shouldBe listOf(1, 5, 4, 3, 2, 6)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(4, 4).toList() shouldBe listOf(6, 5, 3, 4, 2, 1)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(0, 6).toList() shouldBe listOf(6, 5, 4, 3, 2, 1)
    }

    "reversSubList on a shifted ringList" {
        mutableRingListOf(1, 2, 3, 4, 5, 6).shiftRight(2)
            .reverseSubList(1, 4).toList() shouldBe listOf(5, 3, 2, 1, 6, 4)
        mutableRingListOf(1, 2, 3, 4, 5, 6).shiftRight(2)
            .reverseSubList(4, 4).toList() shouldBe listOf(4, 3, 1, 2, 6, 5)
    }
})
