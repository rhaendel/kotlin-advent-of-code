package de.ronny_h.aoc.year2015.day23

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2015.day23.Instruction.Companion.instructionOf
import de.ronny_h.aoc.year2015.day23.Register.A
import de.ronny_h.aoc.year2015.day23.Register.B

fun main() = OpeningTheTuringLock().run(184, 231)

class OpeningTheTuringLock : AdventOfCode<Int>(2015, 23) {
    override fun part1(input: List<String>): Int = Computer(input.parseProgram()).run()
    override fun part2(input: List<String>): Int = Computer(input.parseProgram(), 1u).run()
}

class Computer(val program: List<Instruction>, initA: UInt = 0u) {
    private var instructionPointer = 0
    private val registers = mutableMapOf(A to initA, B to 0u)

    fun run(): Int {
        while (instructionPointer in program.indices) {
            instructionPointer += program[instructionPointer].executeWith(registers)
        }
        return registers.getValue(B).toInt()
    }
}

fun List<String>.parseProgram(): List<Instruction> = map {
    val split = it.split(' ', limit = 2)
    instructionOf(split.first(), split[1])
}

enum class Register {
    A, B
}

sealed interface Instruction {
    /**
     * Executes this instruction on the given registers (by modifying their values).
     *
     * @return The instruction pointer offset.
     */
    fun executeWith(registers: MutableMap<Register, UInt>): Int

    data class Half(val register: Register) : Instruction {
        constructor(parameter: String) : this(parseRegister(parameter))

        override fun executeWith(registers: MutableMap<Register, UInt>): Int {
            registers[register] = registers.getValue(register) / 2u
            return 1
        }
    }

    data class Triple(val register: Register) : Instruction {
        constructor(parameter: String) : this(parseRegister(parameter))

        override fun executeWith(registers: MutableMap<Register, UInt>): Int {
            registers[register] = registers.getValue(register) * 3u
            return 1
        }
    }

    data class Increment(val register: Register) : Instruction {
        constructor(parameter: String) : this(parseRegister(parameter))

        override fun executeWith(registers: MutableMap<Register, UInt>): Int {
            registers[register] = registers.getValue(register) + 1u
            return 1
        }
    }

    data class Jump(val offset: Int) : Instruction {
        constructor(parameter: String) : this(parseOffset(parameter))

        override fun executeWith(registers: MutableMap<Register, UInt>): Int = offset
    }

    data class JumpIfEven(val register: Register, val offset: Int) : Instruction {
        constructor(parameter: String) : this(
            parseRegister(parameter.split(", ").first()),
            parseOffset(parameter.split(", ").last()),
        )

        override fun executeWith(registers: MutableMap<Register, UInt>): Int =
            if (registers.getValue(register) % 2u == 0u) {
                offset
            } else {
                1
            }
    }

    data class JumpIfOne(val register: Register, val offset: Int) : Instruction {
        constructor(parameter: String) : this(
            parseRegister(parameter.split(", ").first()),
            parseOffset(parameter.split(", ").last()),
        )

        override fun executeWith(registers: MutableMap<Register, UInt>): Int =
            if (registers.getValue(register) == 1u) {
                offset
            } else {
                1
            }
    }

    companion object {
        fun instructionOf(keyword: String, parameters: String) = when (keyword) {
            "hlf" -> Half(parameters)
            "tpl" -> Triple(parameters)
            "inc" -> Increment(parameters)
            "jmp" -> Jump(parameters)
            "jie" -> JumpIfEven(parameters)
            "jio" -> JumpIfOne(parameters)
            else -> error("unknown keyword: $keyword")
        }

        private fun parseRegister(parameter: String): Register {
            require(!parameter.contains(" "))
            require(!parameter.contains(","))
            return when (parameter) {
                "a" -> A
                "b" -> B
                else -> error("unknown parameter: $parameter")
            }
        }

        private fun parseOffset(parameter: String): Int {
            require(!parameter.contains(" "))
            require(!parameter.contains(","))
            val sign = when (parameter.first()) {
                '+' -> 1
                '-' -> -1
                else -> error("+ or - expected, but got: $parameter")
            }
            return sign * parameter.substring(1).toInt()
        }
    }
}
