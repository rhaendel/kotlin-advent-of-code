package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UtilsTest : StringSpec({

    "readInput() reads a file into a list of String - one line each" {
        val input = readInput("ForTests")
        input[0] shouldBe "line one"
        input[1] shouldBe "line two"
        input[2] shouldBe "line three"
        input[3] shouldBe ""
        input[4] shouldBe "line five"
    }

    "md5() converts a String to an MD5 hash" {
        "This is just a test".md5() shouldBe "df0a9498a65ca6e20dc58022267f339a"
    }

    "printAndCheck calls the block with the input list as parameter and succeeds on String expectation met" {
        val input = listOf("input")
        var parameter: List<String> = emptyList()
        printAndCheck(input, { it: List<String> -> parameter = it; "output" }, "output")
        parameter shouldBe input
    }
    "printAndCheck calls the block with the input list as parameter and succeeds on Int expectation met" {
        val input = listOf("input")
        var parameter: List<String> = emptyList()
        printAndCheck(input, { it: List<String> -> parameter = it; 7 } as (List<String>) -> Int, 7)
        parameter shouldBe input
    }
    "printAndCheck calls the block with the input list as parameter and succeeds on Long expectation met" {
        val input = listOf("input")
        var parameter: List<String> = emptyList()
        printAndCheck(input, { it: List<String> -> parameter = it; 7L } as (List<String>) -> Long, 7L)
        parameter shouldBe input
    }
})
