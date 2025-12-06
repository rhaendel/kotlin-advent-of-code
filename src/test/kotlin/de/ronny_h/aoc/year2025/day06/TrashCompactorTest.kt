package de.ronny_h.aoc.year2025.day06

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TrashCompactorTest : StringSpec({

    val input = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.asList()

    "problems can be parsed vertically" {
        """
        123 328
         45 64 
          6 98 
        *   +
        """.asList().parseProblemsVertically() shouldBe listOf(
            Problem(listOf(123, 45, 6), multiply),
            Problem(listOf(328, 64, 98), add),
        )
    }

    "problems can be parsed by column right to left" {
        input.parseProblemsByColumnsRightToLeft() shouldBe listOf(
            Problem(listOf(4, 431, 623), add),
            Problem(listOf(175, 581, 32), multiply),
            Problem(listOf(8, 248, 369), add),
            Problem(listOf(356, 24, 1), multiply),
        )
    }

    "part 1: the total of the answers to the problems parsed vertically" {
        TrashCompactor().part1(input) shouldBe 4277556
    }

    "part 2: the total of the answers to the problems parsed by column right to left" {
        TrashCompactor().part2(input) shouldBe 3263827
    }
})
