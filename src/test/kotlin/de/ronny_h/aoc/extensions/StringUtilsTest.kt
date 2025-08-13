package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class StringUtilsTest : StringSpec({

    "asList with a one-line String returns that String" {
        "line one".asList() shouldBe listOf("line one")
    }

    "asList with an empty String returns a list containing an empty String" {
        "".asList() shouldBe listOf("")
    }

    "asList converts a multiline String to a list of Strings with trimmed margin" {
        """
            line one
            line two
            line three
        """.asList() shouldBe listOf("line one", "line two", "line three")
    }

    "asList preserves empty lines" {
        """
            line one

            line two
            line three

        """.asList() shouldBe listOf("line one", "", "line two", "line three", "")
    }

    "asList does not trim single lines" {
        """
            line one
            line two   
            line three
        """.asList() shouldBe listOf("line one", "line two   ", "line three")
    }

    "isAnagramOf" {
        forAll(
            row("a", "a", true),
            row("a", "aa", false),
            row("a", "ab", false),
            row("ab", "ba", true),
            row("ab", "ac", false),
            row("abcdefg", "gfedcba", true),
            row("abcddddefg", "gdfdedcdba", true),
        ) { word1, word2, isAnagram ->
            word1 isAnagramOf word2 shouldBe isAnagram
        }
    }
})
