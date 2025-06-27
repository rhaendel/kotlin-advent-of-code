package de.ronny_h.aoc.year2015.day01

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class NotQuiteLispTest : StringSpec({
    "part 1: find the right floor by counting parenthesis" {
        forAll(
            row("(())", 0),
            row("()()", 0),
            row("(((", 3),
            row("(()(()(", 3),
            row("))(((((", 3),
            row(")())())", -3),
        ) { input, result ->
            NotQuiteLisp().part1(listOf(input)) shouldBe result
        }
    }

    "part 2: the fist position leading to a basement floor" {
        NotQuiteLisp().part2(listOf(")")) shouldBe 1
        NotQuiteLisp().part2(listOf("()())")) shouldBe 5
    }
})
