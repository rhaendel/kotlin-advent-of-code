package de.ronny_h.aoc.extensions

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val StringUtilsTest by testSuite {

    test("asList with a one-line String returns that String") {
        "line one".asList() shouldBe listOf("line one")
    }

    test("asList with an empty String returns a list containing an empty String") {
        "".asList() shouldBe listOf("")
    }

    test("asList converts a multiline String to a list of Strings with trimmed margin") {
        """
            line one
            line two
            line three
        """.asList() shouldBe listOf("line one", "line two", "line three")
    }

    test("asList preserves empty lines") {
        """
            line one

            line two
            line three

        """.asList() shouldBe listOf("line one", "", "line two", "line three", "")
    }

    test("asList does not trim single lines") {
        """
            line one
            line two   
            line three
        """.asList() shouldBe listOf("line one", "line two   ", "line three")
    }

    data class Row(val word1: String, val word2: String, val isAnagram: Boolean)

    testSuite(
        "isAnagramOf",
        listOf(
            Row("a", "a", true),
            Row("a", "aa", false),
            Row("a", "ab", false),
            Row("ab", "ba", true),
            Row("ab", "ac", false),
            Row("abcdefg", "gfedcba", true),
            Row("abcddddefg", "gdfdedcdba", true),
        ),
    ) { (word1, word2, isAnagram) ->
        word1 isAnagramOf word2 shouldBe isAnagram
    }
}
