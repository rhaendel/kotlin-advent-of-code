package de.ronny_h.aoc.year2017.day18

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.numbers.isInt
import de.ronny_h.aoc.extensions.numbers.toIntChecked
import de.ronny_h.aoc.year2017.day18.Instruction.*

fun main() = Duet().run(9423, 0)

class Duet : AdventOfCode<Long>(2017, 18) {
    override fun part1(input: List<String>): Long = SoundPlayer(input.parseInstructions()).run()

    override fun part2(input: List<String>): Long {
        return 0
    }
}

fun List<String>.parseInstructions(): List<Instruction> = map {
    val parameters = it.substring(4)
    when (it.substring(0, 3)) {
        "snd" -> Sound(parameters)
        "set" -> {
            val (register, value) = parameters.split(" ")
            SetValue(register, value.toValue())
        }

        "add" -> {
            val (register, value) = parameters.split(" ")
            Add(register, value.toValue())
        }

        "mul" -> {
            val (register, value) = parameters.split(" ")
            Multiply(register, value.toValue())
        }

        "mod" -> {
            val (register, value) = parameters.split(" ")
            Modulo(register, value.toValue())
        }

        "rcv" -> {
            Recover(parameters)
        }

        "jgz" -> {
            val (register, value) = parameters.split(" ")
            JumpIfGreaterZero(register, value.toValue())
        }

        else -> error("unknown instruction: $it")
    }
}

fun String.toValue(): Value = if (isInt()) {
    Value.Number(toLong())
} else {
    Value.Register(this)
}

sealed interface Value {
    fun toNumber(registers: Map<String, Long>): Long

    data class Register(private val register: String) : Value {
        override fun toNumber(registers: Map<String, Long>): Long = registers.getValue(register)
    }

    data class Number(val value: Long) : Value {
        override fun toNumber(registers: Map<String, Long>): Long = value
    }
}

sealed interface Instruction {
    fun executeOn(registers: MutableMap<String, Long>): Long

    companion object {
        private const val LAST_PLAYED_SOUND_REGISTER = "lastPlayedSound"
    }

    data class Sound(private val register: String) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            val frequency = registers.getValue(register)
            println("playing frequency $frequency")
            registers[LAST_PLAYED_SOUND_REGISTER] = frequency
            return frequency
        }
    }

    data class SetValue(private val register: String, val value: Value) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Add(private val register: String, val value: Value) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) + value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Multiply(private val register: String, val value: Value) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) * value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Modulo(private val register: String, val value: Value) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) % value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Recover(val register: String) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            if (registers.getValue(register) != 0L) {
                val frequency = registers.getValue(LAST_PLAYED_SOUND_REGISTER)
                println("recover last played sound's frequency: $frequency")
                return frequency
            }
            return 0
        }
    }

    data class JumpIfGreaterZero(private val register: String, val value: Value) : Instruction {
        override fun executeOn(registers: MutableMap<String, Long>): Long {
            if (registers.getValue(register) > 0) {
                return value.toNumber(registers)
            }
            return 1
        }
    }
}

class SoundPlayer(private val instructions: List<Instruction>) {
    private val registers = mutableMapOf<String, Long>().withDefault { 0 }
    private var instructionPointer = 0L

    fun run(): Long {
        while (instructionPointer in instructions.indices) {
            val instruction = instructions[instructionPointer.toIntChecked()]
            val result = instruction.executeOn(registers)
            if (instruction is Recover && registers.getValue(instruction.register) != 0L) {
                return result
            }
            instructionPointer += if (instruction is JumpIfGreaterZero) {
                result
            } else {
                1L
            }
        }
        return -1L
    }
}
