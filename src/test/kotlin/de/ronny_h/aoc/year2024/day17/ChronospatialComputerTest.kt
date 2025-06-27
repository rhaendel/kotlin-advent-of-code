package de.ronny_h.aoc.year2024.day17

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ChronospatialComputerTest : StringSpec({
    val input1 = """
        Register A: 729
        Register B: 0
        Register C: 0
        
        Program: 0,1,5,4,3,0
    """.asList()

    "part 1: The program's output" {
        ChronospatialComputer().part1(input1) shouldBe "4,6,3,5,6,3,5,2,1,0"
    }

    val input2 = """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,3,5,4,3,0
    """.asList()

    "part 2: The value of A so that the program outputs itself can be found brute force for a small program" {
        ChronospatialComputer().part2BruteForce(input2) shouldBe "117440"
    }

    "part 2: For a larger program a different approach is needed" {
        val program = listOf(2,4,1,1,7,5,1,5,4,0,5,5,0,3,3,0)
        val registerABinary = findSmallestRegisterAValueToOutputTheProgram(program)
        val registerA = registerABinary.toLong(2)

        val input = """
            Register A: $registerA
            Register B: 0
            Register C: 0
    
            Program: ${program.joinToString(",")}
        """.asList()

        ThreeBitComputer(input).runProgram() shouldBe program
    }
})
