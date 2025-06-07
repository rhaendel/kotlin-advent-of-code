package de.ronny_h.aoc.year24.day17

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ChronospatialComputerTest : StringSpec({
    val input = """
        Register A: 729
        Register B: 0
        Register C: 0
        
        Program: 0,1,5,4,3,0
    """.asList()

    "part 1: The program's output" {
        ChronospatialComputer().part1(input) shouldBe "4,6,3,5,6,3,5,2,1,0"
    }
})
