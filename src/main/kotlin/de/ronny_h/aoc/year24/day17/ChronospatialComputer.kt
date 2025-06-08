package de.ronny_h.aoc.year24.day17

import de.ronny_h.aoc.extensions.printAndCheck
import de.ronny_h.aoc.extensions.readInput
import kotlin.math.pow
import kotlin.properties.Delegates

fun main() {
    val day = "Day17"
    val input = readInput(day)
    val computer = ChronospatialComputer()

    println("$day part 1")
    printAndCheck(input, computer::part1, "4,1,7,6,4,1,0,2,7")
}

class ChronospatialComputer {
    fun part1(input: List<String>): String {
        return ThreeBitComputer().runProgram(input)
    }
}

/**
 * - program = list of 3-bit numbers
 * - 3 registers: A, B, C of type Int
 * - 8 instructions: 3-bit opcode + 3-bit operand
 * - instruction pointer, increases by 2 after instruction processed if not jumped
 * - past the last opcode: program halts
 * - operands: literal or combo
 *
 * combo operands:
 * - 0-3: literal value 0-3
 * - 4, 5, 6: register A, B, C
 * - 7: reserved
 *
 * instructions:
 * opcode  instruction  function
 * 0       adv          division of A and 2^<combo operand>, truncated result in A
 * 1       bxl          bitwise XOR of B and literal operand, result in B
 * 2       bst          <combo operand> modulo 8, lowest 3 bits of result in B
 * 3       jnz          if A=0: nothing, else: jumps to <literal operand>
 * 4       bxc          bitwise XOR of B and C, result in B, reads but ignores operand
 * 5       out          <combo operand> modulo 8, then output result (multiple output values separated by ',')
 * 6       bdv          division of A and 2^<combo operand>, truncated result in B
 * 7       cdv          division of A and 2^<combo operand>, truncated result in C
 */
private class ThreeBitComputer {

    private val instructionStep = 2

    private lateinit var program: List<Int>
    private var registerA by Delegates.notNull<Int>()
    private var registerB by Delegates.notNull<Int>()
    private var registerC by Delegates.notNull<Int>()
    private var instructionPointer = 0

    private val output = mutableListOf<Int>()

    fun init(input: List<String>) {
        program = input.readProgram()
        registerA = input.readRegister('A')
        registerB = input.readRegister('B')
        registerC = input.readRegister('C')
        instructionPointer = 0
        output.clear()
    }

    private val instructions: List<(Int) -> Unit> = listOf(
        { op -> registerA = (registerA / 2.0.pow(combo(op))).toInt(); next() }, // 0 adv
        { op -> registerB = (registerB xor op)                      ; next() }, // 1 bxl
        { op -> registerB = (combo(op) % 8) and 7                   ; next() }, // 2 bst; 7=111 -> take only lowest 3 bits
        { op -> if (registerA == 0) next() else instructionPointer = op },      // 3 jnz
        { _  -> registerB = (registerB xor registerC)               ; next() }, // 4 bxc
        { op -> output.add((combo(op) % 8) and 7)                   ; next() }, // 5 out
        { op -> registerB = (registerA / 2.0.pow(combo(op))).toInt(); next() }, // 6 bdv
        { op -> registerC = (registerA / 2.0.pow(combo(op))).toInt(); next() }, // 7 cdv
    )

    fun runProgram(input: List<String>): String {
        init(input)
        while (instructionPointer < program.size) {
            val instruction = program[instructionPointer]
            val op = program[instructionPointer + 1]
            instructions[instruction].invoke(op)
        }
        return output.joinToString(",")
    }

    private fun combo(op: Int) = when (op) {
        in 1..3 -> op
        4 -> registerA
        5 -> registerB
        6 -> registerC
        else -> error("unknown combo operand code '$op'")
    }

    private fun next() {
        instructionPointer += instructionStep
    }

    private fun List<String>.readRegister(register: Char) =
        first { it.startsWith("Register $register: ") }
            .substringAfter("Register $register: ")
            .toInt()

    private fun List<String>.readProgram() =
        first { it.startsWith("Program: ") }
            .substringAfter("Program: ")
            .split(",")
            .map(String::toInt)
}
