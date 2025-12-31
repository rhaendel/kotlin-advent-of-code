package de.ronny_h.aoc.year2018.day19

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2018.day16.ProgramStep
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GoWithTheFlowTest : StringSpec({

    val input = """
        #ip 0
        seti 5 0 1
        seti 6 0 2
        addi 0 1 0
        addr 1 2 3
        setr 1 0 0
        seti 8 0 4
        seti 9 0 5
    """.asList()

    "input can be parsed" {
        val input = """
            #ip 0
            seti 5 0 1
            addi 0 1 0
            addr 1 2 3
            setr 1 0 0
        """.asList()
        input.parseProgram() shouldBe Program(
            0, listOf(
                ProgramStep(9, 5, 0, 1),
                ProgramStep(1, 0, 1, 0),
                ProgramStep(0, 1, 2, 3),
                ProgramStep(8, 1, 0, 0),
            )
        )
    }

    "part 1: the content of register 0 after running the program" {
        GoWithTheFlow().part1(input) shouldBe 6
    }

    "part 1: the content of register 0 after running the program with the hard-coded device, derived from the real puzzle input" {
        HardCodedWristDevice().partOne() shouldBe 1694
    }

    "part 2: the content of register 0 after running the program with initial value 1 in register 0" {
        GoWithTheFlow().part2(input) shouldBe 18964204
    }
})
