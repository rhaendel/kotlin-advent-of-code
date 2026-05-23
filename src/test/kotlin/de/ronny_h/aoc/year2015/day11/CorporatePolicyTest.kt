package de.ronny_h.aoc.year2015.day11

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

val CorporatePolicyTest by testSuite {

    val day11 = CorporatePolicy()

    testSuite("Passwords must be exactly eight lowercase letters") {
        mapOf(
            "abcdefgh" to true,
            "abcdefg" to false,
            "aBcdefgh" to false,
            "abcdefghi" to false,
        ).forEach { (password, expected) ->
            test("$password, $expected") {
                day11.rule0AppliesTo(password) shouldBe expected
            }
        }
    }

    testSuite("Passwords must include one increasing straight of at least three letters") {
        mapOf(
            "abc" to true,
            "abbcdde" to true,
            "aaa" to false,
            "aabc" to true,
            "abcc" to true,
            "aabcc" to true,
            "aabbcc" to false,
        ).forEach { (password, expected) ->
            test("$password, $expected") {
                day11.rule1Applies(password) shouldBe expected
            }
        }
    }

    testSuite("Passwords may not contain the letters i, o, or l") {
        mapOf(
            "abc" to true,
            "hij" to false,
            "mno" to false,
            "lmn" to false,
        ).forEach { (password, expected) ->
            test("$password, $expected") {
                day11.rule2Applies(password) shouldBe expected
            }
        }
    }

    testSuite("Passwords must contain at least two different, non-overlapping pairs of letters") {
        mapOf(
            "abcd" to false,
            "aabb" to true,
            "aabbcc" to true,
            "aaaa" to true,
            "aaab" to false,
        ).forEach { (password, expected) ->
            test("$password, $expected") {
                day11.rule3Applies(password) shouldBe expected
            }
        }
    }

    test("hijklmmn") {
        val password = "hijklmmn"
        day11.rule1Applies(password) shouldBe true
        day11.rule2Applies(password) shouldBe false
    }

    test("abbceffg") {
        val password = "abbceffg"
        day11.rule3Applies(password) shouldBe true
        day11.rule1Applies(password) shouldBe false
    }

    test("increment a") {
        "a".inc() shouldBe "b"
    }

    test("increment az with carry-over") {
        "az".inc() shouldBe "ba"
    }

    test("increment aaazzz with carry-over") {
        "aaazzz".inc() shouldBe "aabaaa"
    }

    test("zzzz cannot be incremented without making the String longer") {
        val exception = shouldThrow<StringOverflowException> {
            "zzzz".inc()
        }
        exception.message shouldBe "String 'zzzz' cannot be incremented without making it longer."
    }

    test("part 1: The next password after abcdefgh is abcdffaa") {
        day11.part1(listOf("abcdefgh")) shouldBe "abcdffaa"
    }

    test("part 1: The next password after ghijklmn is ghjaabcc") {
        day11.part1(listOf("ghijklmn")) shouldBe "ghjaabcc"
    }

    test("part 2 is two times part 1") {
        val input = listOf("ghijklmn")
        day11.part2(input) shouldBe "ghjbbcdd"
    }
}
