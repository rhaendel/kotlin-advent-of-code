package de.ronny_h.aoc.year2017.day25

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2017.day25.TapeDirection.LEFT
import de.ronny_h.aoc.year2017.day25.TapeDirection.RIGHT
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TheHaltingProblemTest : StringSpec({

    val input = """
        Begin in state A.
        Perform a diagnostic checksum after 6 steps.
        
        In state A:
          If the current value is 0:
            - Write the value 1.
            - Move one slot to the right.
            - Continue with state B.
          If the current value is 1:
            - Write the value 0.
            - Move one slot to the left.
            - Continue with state B.
        
        In state B:
          If the current value is 0:
            - Write the value 1.
            - Move one slot to the left.
            - Continue with state A.
          If the current value is 1:
            - Write the value 1.
            - Move one slot to the right.
            - Continue with state A.
    """.asList()

    "input can be parsed" {
        input.parseTuringMachineConfiguration() shouldBe TuringMachineConfiguration(
            "A", 6, listOf(
                Rule("A", 0, 1, RIGHT, "B"),
                Rule("A", 1, 0, LEFT, "B"),
                Rule("B", 0, 1, LEFT, "A"),
                Rule("B", 1, 1, RIGHT, "A"),
            )
        )
    }

    "part 1: the diagnostic checksum after running the blueprint" {
        TheHaltingProblem().part1(input) shouldBe 3
    }

    "part 2 does nothing" {
        TheHaltingProblem().part2(input) shouldBe 0
    }
})
