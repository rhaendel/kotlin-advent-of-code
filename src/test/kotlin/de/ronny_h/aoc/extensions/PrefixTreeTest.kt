package de.ronny_h.aoc.extensions

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val PrefixTreeTest by testSuite {

    test("an empty word always can be inserted once") {
        val tree = PrefixTree()
        tree.insert("", setOf("a", "b", "c")) shouldBe 1
    }

    test("a word equal to a token can be inserted once") {
        val tree = PrefixTree()
        tree.insert("abc", setOf("abc")) shouldBe 1
    }

    test("a word consisting of single-character tokens can be inserted once") {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "b", "c")) shouldBe 1
    }

    test("a word with a missing token can not be inserted") {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "c")) shouldBe 0
    }

    test("a word constructed from tokens of different lengths can be inserted once") {
        val tree = PrefixTree()
        tree.insert("abc", setOf("ab", "c")) shouldBe 1
    }

    test("a word constructed from multiple combinations of tokens can be inserted twice") {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "ab", "bc", "c")) shouldBe 2
    }
}
