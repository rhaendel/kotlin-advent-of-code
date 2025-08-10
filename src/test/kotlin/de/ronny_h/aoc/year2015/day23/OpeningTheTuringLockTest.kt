package de.ronny_h.aoc.year2015.day23

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2015.day23.Instruction.*
import de.ronny_h.aoc.year2015.day23.Instruction.Companion.instructionOf
import de.ronny_h.aoc.year2015.day23.Register.A
import de.ronny_h.aoc.year2015.day23.Register.B
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class OpeningTheTuringLockTest : StringSpec({

    "input can be parsed" {
        """
            hlf a
            tpl b
            inc a
            jmp +2
            jie a, -2
            jio b, +2
        """.asList().parseProgram() shouldBe listOf(
            Half(A),
            Triple(B),
            Increment(A),
            Jump(2),
            JumpIfEven(A, -2),
            JumpIfOne(B, 2),
        )
    }

    "hlf sets register to half its current value" {
        val registers = mutableMapOf(A to 4u)
        val offset = instructionOf("hlf", "a").executeWith(registers)

        registers.getValue(A) shouldBe 2u
        offset shouldBe 1
    }

    "tpl sets register to triple its current value" {
        val registers = mutableMapOf(A to 4u)
        val offset = instructionOf("tpl", "a").executeWith(registers)

        registers.getValue(A) shouldBe 12u
        offset shouldBe 1
    }

    "inc increments the register by 1" {
        val registers = mutableMapOf(A to 4u)
        val offset = instructionOf("inc", "a").executeWith(registers)

        registers.getValue(A) shouldBe 5u
        offset shouldBe 1
    }

    "jmp jumps by the given offset" {
        val registers = mutableMapOf(A to 4u)
        val offset = instructionOf("jmp", "-7").executeWith(registers)

        registers.getValue(A) shouldBe 4u
        offset shouldBe -7
    }

    "jie jumps only if the register is even" {
        forAll(
            row(mutableMapOf(A to 4u), -7, -7),
            row(mutableMapOf(A to 3u), -7, 1),
        ) { registers, offset, expectedJump ->
            val oldA = registers.getValue(A)
            val jump = instructionOf("jie", "a, $offset").executeWith(registers)

            registers.getValue(A) shouldBe oldA
            jump shouldBe expectedJump
        }
    }

    "jio jumps only if the register is one" {
        forAll(
            row(mutableMapOf(A to 4u), -7, 1),
            row(mutableMapOf(A to 3u), -7, 1),
            row(mutableMapOf(A to 1u), -7, -7),
        ) { registers, offset, expectedJump ->
            val oldA = registers.getValue(A)
            val jump = instructionOf("jio", "a, $offset").executeWith(registers)

            registers.getValue(A) shouldBe oldA
            jump shouldBe expectedJump
        }
    }

    "part 1: the example program sets b to 2" {
        val input = """
            inc b
            jio b, +2
            tpl b
            inc b
        """.asList()
        OpeningTheTuringLock().part1(input) shouldBe 2
    }

    "part 2: the example program sets b to 1 when a is initialized with 1" {
        val input = """
            inc a
            jio a, +2
            inc b
            inc a
        """.asList()
        OpeningTheTuringLock().part2(input) shouldBe 1
    }
})
