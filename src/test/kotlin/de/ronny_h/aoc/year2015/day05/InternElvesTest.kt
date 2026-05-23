package de.ronny_h.aoc.year2015.day05

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val InternElvesTest by testSuite {

    val internElves = InternElves()

    test("part 1: A nice string contains at least three vowels") {
        internElves.hasThreeVowels("aei") shouldBe true
        internElves.hasThreeVowels("ugknbfddgicrmopn") shouldBe true
    }

    test("part 1: A nice string contains at least one letter that appears twice in a row") {
        internElves.hasALetterTwiceInARow("ugknbfddgicrmopn") shouldBe true
    }

    test("part 1: A nice string does not contain the strings ab, cd, pq, or xy") {
        internElves.doesNotContainBadStrings("ugknbfddgicrmopn") shouldBe true
    }

    test("part 1: The number of nice strings") {
        internElves.part1(listOf("ugknbfddgicrmopn")) shouldBe 1
        internElves.part1(listOf("aaa")) shouldBe 1
        internElves.part1(listOf("jchzalrnumimnmhp", "haegwjzuvuyypxyu", "dvszwmarrgswjxmb")) shouldBe 0
    }

    test("part 2: A nice string contains a pair of any two letters that appears at least twice in the string without overlapping") {
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("xyxy") shouldBe true
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("aabcdefgaa") shouldBe true
        internElves.containsAPairOfLettersAtLeastTwiceWithoutOverlapping("aaa") shouldBe false
    }

    test("part 2: A nice string contains at least one letter which repeats with exactly one letter between them") {
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("xyx") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abcdefeghi") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abcdefeghi") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("aaa") shouldBe true
        internElves.containsALetterRepeatingWithExactlyOneLetterInBetween("abc") shouldBe false
    }

    test("part 2: The number of nice strings") {
        internElves.part2(listOf("qjhvhtzxzqqjkmpb")) shouldBe 1
        internElves.part2(listOf("xxyxx")) shouldBe 1
        internElves.part2(listOf("uurcxstgmygtbstg", "uurcxstgmygtbstg")) shouldBe 0
    }
}
