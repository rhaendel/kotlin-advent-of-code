package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PrefixTreeTest : StringSpec({

    "an empty word always can be inserted once" {
        val tree = PrefixTree()
        tree.insert("", setOf("a", "b", "c")) shouldBe 1
    }

    "a word equal to a token can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", setOf("abc")) shouldBe 1
    }

    "a word consisting of single-character tokens can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "b", "c")) shouldBe 1
    }

    "a word with a missing token can not be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "c")) shouldBe 0
    }

    "a word constructed from tokens of different lengths can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", setOf("ab", "c")) shouldBe 1
    }

    "a word constructed from multiple combinations of tokens can be inserted twice" {
        val tree = PrefixTree()
        tree.insert("abc", setOf("a", "ab", "bc", "c")) shouldBe 2
    }
})
