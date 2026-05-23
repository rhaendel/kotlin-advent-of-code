package de.ronny_h.aoc.year2015.day01

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val NotQuiteLispTest by testSuite {
    testSuite("part 1: find the right floor by counting parenthesis") {
        mapOf(
            "(())" to 0,
            "()()" to 0,
            "(((" to 3,
            "(()(()(" to 3,
            "))(((((" to 3,
            ")())())" to -3,
        ).forEach { (input, result) ->
            test("$input -> $result") {
                NotQuiteLisp().part1(listOf(input)) shouldBe result
            }
        }
    }

    test("part 2: the fist position leading to a basement floor") {
        NotQuiteLisp().part2(listOf(")")) shouldBe 1
        NotQuiteLisp().part2(listOf("()())")) shouldBe 5
    }
}
