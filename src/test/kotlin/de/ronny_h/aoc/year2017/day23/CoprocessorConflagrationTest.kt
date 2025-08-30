package de.ronny_h.aoc.year2017.day23

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2017.day18.Instruction.*
import de.ronny_h.aoc.year2017.day18.Value.Number
import de.ronny_h.aoc.year2017.day18.Value.Register
import de.ronny_h.aoc.year2017.day18.parseInstructions
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CoprocessorConflagrationTest : StringSpec({

    val input = """
        set h 7
        jnz b 2
        jnz 1 1
        mul b 4
        sub c -1
    """.asList()

    "input can be parsed" {
        input.parseInstructions(null, null) shouldBe listOf(
            SetValue("h", Number(7)),
            JumpIfNotZero(Register("b"), Number(2)),
            JumpIfNotZero(Number(1), Number(1)),
            Multiply("b", Number(4)),
            Sub("c", Number(-1))
        )
    }

    "part 1: there should be 1 multiplication executed in the test input" {
        CoprocessorConflagration().part1(input) shouldBe 1
    }

    "part 2: the number of non-prime numbers between 106700 and 123700 in steps of 17" {
        CoprocessorConflagration().part2(input) shouldBe 905
    }
})
