package de.ronny_h.aoc.extensions.collections

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.collections.MutableRingList.Companion.mutableRingListOf
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

val MutableRingListTest by testSuite {

    test("a MutableRingList<Char> can be created from a variable amount of Chars and toString() generates a nice representation") {
        mutableRingListOf('a', 'b', 'c', 'd', 'e').toString() shouldBe "[a, b, c, d, e]"
    }

    test("a MutableRingList<Char> can be created from a String and toJoinedString() recreates that String") {
        mutableRingListOf("abcde").toJoinedString() shouldBe "abcde"
    }

    testSuite(
        "a MutableRingList can be created with an initializer function",
        mapOf(
            0 to "",
            1 to "0",
            6 to "012345",
        )
    ) { size, string ->
        val list = MutableRingList(size) { it }
        list.size shouldBe size
        list.toJoinedString() shouldBe string
    }

    testSuite(
        "the size of a MutableRingList",
        mapOf(
            "" to 0,
            "a" to 1,
            "abcdef" to 6,
        )
    ) { data, size ->
        mutableRingListOf(data).size shouldBe size
    }

    test("get(index) returns the element at index") {
        mutableRingListOf("abcde").get(2) shouldBe 'c'
        mutableRingListOf("abcde")[2] shouldBe 'c'
    }

    test("set(index, value) sets the value at index") {
        val list = mutableRingListOf("abcde")

        list.set(2, 'z')
        list[2] shouldBe 'z'

        list[0] = 'y'
        list[0] shouldBe 'y'
    }

    test("insert() inserts the value at the currently first position") {
        val list = mutableRingListOf("abc")

        list.insert('x')
        list.toJoinedString() shouldBe "xabc"

        list.shiftLeft(2)
        list.insert('y')
        list.toJoinedString() shouldBe "ybcxa"
    }

    test("removeFirst removes the first element and shifts the remaining ones 1 position left") {
        val list = mutableRingListOf("abcde")

        val removed = list.removeFirst()

        removed shouldBe 'a'
        list.toJoinedString() shouldBe "bcde"
    }

    test("removeFirst on a list shifted left") {
        val list = mutableRingListOf("abcde")

        list.shiftLeft(2)
        val removed = list.removeFirst()

        removed shouldBe 'c'
        list.toJoinedString() shouldBe "deab"
    }

    test("removeFirst on a list shifted right") {
        val list = mutableRingListOf("abcde")

        list.shiftRight(2)
        val removed = list.removeFirst()

        removed shouldBe 'd'
        list.toJoinedString() shouldBe "eabc"
    }

    test("removeFirst that makes a list of size 1 empty") {
        val list = mutableRingListOf("a")

        list.size shouldBe 1
        list.removeFirst() shouldBe 'a'
        list.size shouldBe 0
        list.toJoinedString() shouldBe ""

        list.add('b')
        list.size shouldBe 1
        list.toJoinedString() shouldBe "b"
    }

    test("removeFirst fails on an empty list") {
        shouldThrow<IndexOutOfBoundsException> {
            mutableRingListOf("").removeFirst()
        }
    }

    test("shiftRight() moves elements from the end to the front") {
        mutableRingListOf("abcde").shiftRight(1).toJoinedString() shouldBe "eabcd"

        mutableRingListOf("abcde").shiftRight(2).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(1).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftRight(3).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftRight(1).shiftRight(2).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftRight(5).toJoinedString() shouldBe "abcde"

        mutableRingListOf("abcde").shiftRight(7).toJoinedString() shouldBe "deabc"
    }

    test("shiftLeft() moves elements from the front to the end") {
        mutableRingListOf("abcde").shiftLeft(1).toJoinedString() shouldBe "bcdea"

        mutableRingListOf("abcde").shiftLeft(2).toJoinedString() shouldBe "cdeab"
        mutableRingListOf("abcde").shiftLeft(1).shiftLeft(1).toJoinedString() shouldBe "cdeab"

        mutableRingListOf("abcde").shiftLeft(3).toJoinedString() shouldBe "deabc"
        mutableRingListOf("abcde").shiftLeft(1).shiftLeft(2).toJoinedString() shouldBe "deabc"

        mutableRingListOf("abcde").shiftLeft(5).toJoinedString() shouldBe "abcde"

        mutableRingListOf("abcde").shiftLeft(7).toJoinedString() shouldBe "cdeab"
    }

    test("get after shiftRight returns the shifted elements") {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] shouldBe 'e'
        list[4] shouldBe 'd'
    }

    test("set after shiftRight sets the element at the shifted position") {
        val list = mutableRingListOf("abcde").shiftRight(1)
        list[0] = 'x'
        list[0] shouldBe 'x'
        list[4] shouldBe 'd'
    }

    test("toList returns the modified state") {
        val list = mutableRingListOf("abcde")
            .shiftRight(2)
            .swap(0, 1)
        list.toList() shouldBe listOf('e', 'd', 'a', 'b', 'c')
    }

    test("swap(indexA, indexB) exchanges the elements at the given indexes") {
        mutableRingListOf("abcde").swap(3, 4).toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap(0, 4).toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap(3, 3).toJoinedString() shouldBe "abcde"
    }

    test("swap(elemA, elemB) exchanges the elements by their name") {
        mutableRingListOf("abcde").swap('d', 'e').toJoinedString() shouldBe "abced"
        mutableRingListOf("abcde").swap('a', 'e').toJoinedString() shouldBe "ebcda"
        mutableRingListOf("abcde").swap('c', 'c').toJoinedString() shouldBe "abcde"
    }

    test("swap with a non-existent value terminates with an error") {
        val exception = shouldThrow<IllegalArgumentException> {
            mutableRingListOf("abc").swap('a', 'd')
        }
        exception.message shouldBe "d is not in the list"
    }

    test("reversSubList on an unmodified ringList") {
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(1, 3).toList() shouldBe listOf(1, 4, 3, 2, 5, 6)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(1, 4).toList() shouldBe listOf(1, 5, 4, 3, 2, 6)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(4, 4).toList() shouldBe listOf(6, 5, 3, 4, 2, 1)
        mutableRingListOf(1, 2, 3, 4, 5, 6).reverseSubList(0, 6).toList() shouldBe listOf(6, 5, 4, 3, 2, 1)
    }

    test("reversSubList on a shifted ringList") {
        mutableRingListOf(1, 2, 3, 4, 5, 6).shiftRight(2)
            .reverseSubList(1, 4).toList() shouldBe listOf(5, 3, 2, 1, 6, 4)
        mutableRingListOf(1, 2, 3, 4, 5, 6).shiftRight(2)
            .reverseSubList(4, 4).toList() shouldBe listOf(4, 3, 1, 2, 6, 5)
    }
}
