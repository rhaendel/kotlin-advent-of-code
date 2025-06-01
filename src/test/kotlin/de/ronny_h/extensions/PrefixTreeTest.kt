package de.ronny_h.extensions

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PrefixTreeTest : StringSpec({

    "an empty word always can be inserted once" {
        val tree = PrefixTree()
        tree.insert("", listOf("a", "b", "c")) shouldBe 1
    }

    "a word equal to a token can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("abc")) shouldBe 1
    }

    "a word consisting of single-character tokens can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "b", "c")) shouldBe 1
    }

    "a word consisting with a missing token can not be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "c")) shouldBe 0
    }

    "a word constructed from token of different lengths can be inserted once" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("ab", "c")) shouldBe 1
    }

    "a word constructed from multiple combinations of tokens can be inserted twice" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "ab", "bc", "c")) shouldBe 2
    }
})
