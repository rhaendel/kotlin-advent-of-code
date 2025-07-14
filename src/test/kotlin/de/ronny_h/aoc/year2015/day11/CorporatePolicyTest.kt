package de.ronny_h.aoc.year2015.day11

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class CorporatePolicyTest : StringSpec({

    val day11 = CorporatePolicy()

    "Passwords must be exactly eight lowercase letters" {
        forAll(
            row("abcdefgh", true),
            row("abcdefg", false),
            row("aBcdefgh", false),
            row("abcdefghi", false),
        ) { password, expected ->
            day11.rule0AppliesTo(password) shouldBe expected
        }
    }

    "Passwords must include one increasing straight of at least three letters" {
        forAll(
            row("abc", true),
            row("abbcdde", true),
            row("aaa", false),
            row("aabc", true),
            row("abcc", true),
            row("aabcc", true),
            row("aabbcc", false),
        ) { password, expected ->
            day11.rule1Applies(password) shouldBe expected
        }
    }

    "Passwords may not contain the letters i, o, or l" {
        forAll(
            row("abc", true),
            row("hij", false),
            row("mno", false),
            row("lmn", false),
        ) { password, expected ->
            day11.rule2Applies(password) shouldBe expected
        }
    }

    "Passwords must contain at least two different, non-overlapping pairs of letters" {
        forAll(
            row("abcd", false),
            row("aabb", true),
            row("aabbcc", true),
            row("aaaa", true),
            row("aaab", false),
        ) { password, expected ->
            day11.rule3Applies(password) shouldBe expected
        }
    }

    "hijklmmn" {
        val password = "hijklmmn"
        day11.rule1Applies(password) shouldBe true
        day11.rule2Applies(password) shouldBe false
    }

    "abbceffg" {
        val password = "abbceffg"
        day11.rule3Applies(password) shouldBe true
        day11.rule1Applies(password) shouldBe false
    }

    "increment a" {
        "a".inc() shouldBe "b"
    }

    "increment az with carry-over" {
        "az".inc() shouldBe "ba"
    }

    "increment aaazzz with carry-over" {
        "aaazzz".inc() shouldBe "aabaaa"
    }

    "zzzz cannot be incremented without making the String longer" {
        val exception = shouldThrow<StringOverflowException> {
            "zzzz".inc()
        }
        exception.message shouldBe "String 'zzzz' cannot be incremented without making it longer."
    }

    "part 1: The next password after abcdefgh is abcdffaa" {
        day11.part1(listOf("abcdefgh")) shouldBe "abcdffaa"
    }

    "part 1: The next password after ghijklmn is ghjaabcc" {
        day11.part1(listOf("ghijklmn")) shouldBe "ghjaabcc"
    }

    "part 2 is two times part 1" {
        val input = listOf("ghijklmn")
        day11.part2(input) shouldBe "ghjbbcdd"
    }
})
