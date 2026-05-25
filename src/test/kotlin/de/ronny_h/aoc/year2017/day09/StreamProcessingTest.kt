package de.ronny_h.aoc.year2017.day09

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val StreamProcessingTest by testSuite {

    testSuite("the number of groups") {
        mapOf(
            "{}" to 1,
            "{{{}}}" to 3,
            "{{},{}}" to 3,
            "{{{},{},{{}}}}" to 6,
            "{<{},{},{{}}>}" to 1,
            "{<a>,<a>,<a>,<a>}" to 1,
            "{{<a>},{<a>},{<a>},{<a>}}" to 5,
            "{{<!>},{<!>},{<!>},{<a>}}" to 2,
        ).forEach { (input, numberOfGroups) ->
            test("$input, $numberOfGroups") {
                input.parseGroups().count shouldBe numberOfGroups
            }
        }
    }

    testSuite("the total score for all groups in the input") {
        mapOf(
            "{}" to 1,
            "{{{}}}" to 6,
            "{{},{}}" to 5,
            "{{{},{},{{}}}}" to 16,
            "{<a>,<a>,<a>,<a>}" to 1,
            "{{<ab>},{<ab>},{<ab>},{<ab>}}" to 9,
            "{{<!!>},{<!!>},{<!!>},{<!!>}}" to 9,
            "{{<a!>},{<a!>},{<a!>},{<ab>}}" to 3,
        ).forEach { (input, score) ->
            test("$input, $score") {
                input.parseGroups().score shouldBe score
            }
        }
    }

    testSuite("the number of non-canceled characters within the garbage") {
        mapOf(
            "{<>}" to 0,
            "{<random characters>}" to 17,
            "{<<<<>}" to 3,
            "{<{!>}>}" to 2,
            "{<!!>}" to 0,
            "{<!!!>>}" to 0,
            "{<{o\"i!a,<{i<a>}" to 10,
        ).forEach { (input, garbage) ->
            test("$input, $garbage") {
                input.parseGroups().garbage shouldBe garbage
            }
        }
    }

    test("part 1: the total score for all groups in the input") {
        val input = listOf("{{<ab>},{<ab>},{<ab>},{<ab>}}")
        StreamProcessing().part1(input) shouldBe 9
    }

    test("part 2: the number of non-canceled characters within the garbage") {
        val input = listOf("{<{!>}>}")
        StreamProcessing().part2(input) shouldBe 2
    }
}
