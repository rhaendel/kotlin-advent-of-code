package de.ronny_h.aoc.year2017.day08

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2017.day08.Condition.*
import de.ronny_h.aoc.year2017.day08.Operation.Decrease
import de.ronny_h.aoc.year2017.day08.Operation.Increase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class IHeardYouLikeRegistersTest : StringSpec({

    val input = """
        b inc 5 if a > 1
        a inc 1 if b < 5
        c dec -10 if a >= 1
        c inc -20 if c == 10
    """.asList()

    "input can be parsed" {
        input.parseInstructions() shouldBe listOf(
            Instruction("b", Increase, 5, "a", GreaterThan, 1),
            Instruction("a", Increase, 1, "b", LessThan, 5),
            Instruction("c", Decrease, -10, "a", GreaterThanOrEqual, 1),
            Instruction("c", Increase, -20, "c", Equal, 10),
        )
    }

    "part 1: the largest value in any register after completing the instructions" {
        IHeardYouLikeRegisters().part1(input) shouldBe 1
    }

    "part 2: the highest value held in any register during this process" {
        IHeardYouLikeRegisters().part2(input) shouldBe 10
    }
})
