package de.ronny_h.aoc.year2017.day18

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2017.day18.Instruction.*
import de.ronny_h.aoc.year2017.day18.Value.Number
import de.ronny_h.aoc.year2017.day18.Value.Register
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DuetTest : StringSpec({

    val input = """
        set a 1
        add a 2
        mul a a
        mod a 5
        snd a
        set a 0
        rcv a
        jgz a -1
        set a 1
        jgz a -2
    """.asList()

    "instructions can be parsed" {
        input.parseInstructions() shouldBe listOf(
            SetValue("a", Number(1)),
            Add("a", Number(2)),
            Multiply("a", Register("a")),
            Modulo("a", Number(5)),
            Sound("a"),
            SetValue("a", Number(0)),
            Recover("a"),
            JumpIfGreaterZero("a", Number(-1)),
            SetValue("a", Number(1)),
            JumpIfGreaterZero("a", Number(-2)),
        )
    }

    "part 1: the value of the recovered frequency the first time a rcv instruction is executed with a non-zero value" {
        Duet().part1(input) shouldBe 4
    }

    "part 2" {
        val input = listOf("")
        Duet().part2(input) shouldBe 0
    }
})
