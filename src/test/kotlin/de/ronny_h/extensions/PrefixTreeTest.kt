package de.ronny_h.extensions

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PrefixTreeTest : StringSpec({

    "an empty word always can be inserted" {
        val tree = PrefixTree()
        tree.insert("", listOf("a", "b", "c")) shouldBe true
    }

    "a word equal to a token can be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("abc")) shouldBe true
    }

    "a word consisting of single-character tokens can be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "b", "c")) shouldBe true
    }

    "a word consisting with a missing token can not be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "c")) shouldBe false
    }

    "a word constructed from token of different lengths can be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("ab", "c")) shouldBe true
    }

    "a word constructed from multiple combinations of tokens can be inserted" {
        val tree = PrefixTree()
        tree.insert("abc", listOf("a", "ab", "bc", "c")) shouldBe true
    }
})
