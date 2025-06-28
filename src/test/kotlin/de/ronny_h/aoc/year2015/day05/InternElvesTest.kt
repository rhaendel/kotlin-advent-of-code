package de.ronny_h.aoc.year2015.day05

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class InternElvesTest : StringSpec({

    val internElves = InternElves()

    "part 1: A nice string contains at least three vowels" {
        internElves.hasThreeVowels("aei") shouldBe true
        internElves.hasThreeVowels("ugknbfddgicrmopn") shouldBe true
    }

    "part 1: A nice string contains at least one letter that appears twice in a row" {
        internElves.hasALetterTwiceInARow("ugknbfddgicrmopn") shouldBe true
    }

    "part 1: A nice string does not contain the strings ab, cd, pq, or xy" {
        internElves.doesNotContainBadStrings("ugknbfddgicrmopn") shouldBe true
    }

    "part 1: The number of nice strings" {
        internElves.part1(listOf("ugknbfddgicrmopn")) shouldBe 1
        internElves.part1(listOf("aaa")) shouldBe 1
        internElves.part1(listOf("jchzalrnumimnmhp", "haegwjzuvuyypxyu", "dvszwmarrgswjxmb")) shouldBe 0
    }

    "part 2: A nice string contains a pair of any two letters that appears at least twice in the string without overlapping" {
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("xyxy") shouldBe true
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("aabcdefgaa") shouldBe true
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("aaa") shouldBe false
    }

    "part 2: A nice string contains at least one letter which repeats with exactly one letter between them" {
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("xyx") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abcdefeghi") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abcdefeghi") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("aaa") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abc") shouldBe false
    }

    "part 2: The number of nice strings" {
        internElves.part2(listOf("qjhvhtzxzqqjkmpb")) shouldBe 1
        internElves.part2(listOf("xxyxx")) shouldBe 1
        internElves.part2(listOf("uurcxstgmygtbstg", "uurcxstgmygtbstg")) shouldBe 0
    }
})
