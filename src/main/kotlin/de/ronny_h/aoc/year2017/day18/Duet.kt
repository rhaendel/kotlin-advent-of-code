package de.ronny_h.aoc.year2017.day18

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.numbers.isInt
import de.ronny_h.aoc.extensions.numbers.toIntChecked
import de.ronny_h.aoc.year2017.day18.Instruction.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

fun main() = Duet().run(9423, 7620)

class Duet : AdventOfCode<Long>(2017, 18) {
    override fun part1(input: List<String>): Long = runBlocking {
        return@runBlocking Program(input).run()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun part2(input: List<String>): Long = runBlocking {
        val channel0 = Channel<Long>(UNLIMITED)
        val channel1 = Channel<Long>(UNLIMITED)
        val program0 = Program(input, channel0, channel1, mapOf(P_REGISTER to 0, PROGRAM_NUMBER_REGISTER to 0))
        val program1 = Program(input, channel1, channel0, mapOf(P_REGISTER to 1, PROGRAM_NUMBER_REGISTER to 1))

        val job0 = launch { program0.run() }
        val job1 = launch { program1.run() }

        launch {
            runDeadlockDetection(program0, program1, channel0, channel1)
            job0.cancelAndJoin()
            job1.cancelAndJoin()
        }

        job0.join()
        println("program 0 completed")
        job1.join()
        println("program 1 completed")

        return@runBlocking program1.getNumberOfSends()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun runDeadlockDetection(
        program0: Program,
        program1: Program,
        channel0: Channel<Long>,
        channel1: Channel<Long>,
    ) {
        while (true) {
            delay(100)
            if (program0.isReceiving && program1.isReceiving && channel0.isEmpty && channel1.isEmpty) {
                println("deadlock detected!")
                return
            }
        }
    }
}

fun List<String>.parseInstructions(
    sendChannel: SendChannel<Long>?,
    receiveChannel: ReceiveChannel<Long>?
): List<Instruction> = map {
    val parameters = it.substring(4)
    when (it.substring(0, 3)) {
        "snd" -> sendChannel?.let { Send(parameters.toValue(), sendChannel) } ?: Sound(parameters)
        "set" -> {
            val (register, value) = parameters.split(" ")
            SetValue(register, value.toValue())
        }

        "add" -> {
            val (register, value) = parameters.split(" ")
            Add(register, value.toValue())
        }

        "sub" -> {
            val (register, value) = parameters.split(" ")
            Sub(register, value.toValue())
        }

        "mul" -> {
            val (register, value) = parameters.split(" ")
            Multiply(register, value.toValue())
        }

        "mod" -> {
            val (register, value) = parameters.split(" ")
            Modulo(register, value.toValue())
        }

        "rcv" -> receiveChannel?.let { Receive(parameters, receiveChannel) } ?: Recover(parameters)

        "jgz" -> {
            val (value, offset) = parameters.split(" ")
            JumpIfGreaterZero(value.toValue(), offset.toValue())
        }

        "jnz" -> {
            val (value, offset) = parameters.split(" ")
            JumpIfNotZero(value.toValue(), offset.toValue())
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

private const val P_REGISTER = "p"
private const val PROGRAM_NUMBER_REGISTER = "programNumber"
private const val LAST_PLAYED_SOUND_REGISTER = "lastPlayedSound"
private const val NUMBER_OF_SENDS_REGISTER = "numberOfSends"
private const val NUMBER_OF_MULTIPLY_REGISTER = "numberOfMultiply"

sealed interface Instruction {
    suspend fun executeOn(registers: MutableMap<String, Long>): Long

    data class Sound(private val register: String) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            val frequency = registers.getValue(register)
            registers[LAST_PLAYED_SOUND_REGISTER] = frequency
            return frequency
        }
    }

    data class Send(private val value: Value, private val channel: SendChannel<Long>) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            val toSend = value.toNumber(registers)
            channel.send(toSend)
            val numberOfSends = registers.getValue(NUMBER_OF_SENDS_REGISTER) + 1
            registers[NUMBER_OF_SENDS_REGISTER] = numberOfSends
//            println("${registers[PROGRAM_NUMBER_REGISTER]}: sent $toSend [$numberOfSends]")
            return toSend
        }
    }

    data class SetValue(private val register: String, val value: Value) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Add(private val register: String, val value: Value) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) + value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Sub(private val register: String, val value: Value) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) - value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Multiply(private val register: String, val value: Value) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) * value.toNumber(registers)
            val numberOfMultiplies = registers.getValue(NUMBER_OF_MULTIPLY_REGISTER) + 1
            registers[NUMBER_OF_MULTIPLY_REGISTER] = numberOfMultiplies
            return registers.getValue(register)
        }
    }

    data class Modulo(private val register: String, val value: Value) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            registers[register] = registers.getValue(register) % value.toNumber(registers)
            return registers.getValue(register)
        }
    }

    data class Recover(val register: String) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            if (registers.getValue(register) != 0L) {
                return registers.getValue(LAST_PLAYED_SOUND_REGISTER)
            }
            return 0
        }
    }

    data class Receive(private val register: String, private val channel: ReceiveChannel<Long>) : Instruction {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            val value = channel.receive()
//            println("${registers[PROGRAM_NUMBER_REGISTER]}: received $value")
            registers[register] = value
            return value
        }
    }

    sealed interface Jump : Instruction

    data class JumpIfGreaterZero(private val value: Value, private val offset: Value) : Jump {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            if (value.toNumber(registers) > 0) {
                return offset.toNumber(registers)
            }
            return 1
        }
    }

    data class JumpIfNotZero(private val value: Value, private val offset: Value) : Jump {
        override suspend fun executeOn(registers: MutableMap<String, Long>): Long {
            if (value.toNumber(registers) != 0L) {
                return offset.toNumber(registers)
            }
            return 1
        }
    }
}

class Program(
    input: List<String>,
    sendChannel: Channel<Long>? = null,
    receiveChannel: Channel<Long>? = null,
    presetRegisters: Map<String, Long> = emptyMap()
) {
    var isReceiving = false
        private set

    private val instructions = input.parseInstructions(sendChannel, receiveChannel)
    private val registers = presetRegisters.toMutableMap().withDefault { 0 }
    private var instructionPointer = 0L

    suspend fun run(): Long {
        println("program ${registers.getValue(PROGRAM_NUMBER_REGISTER)} started")
        while (instructionPointer in instructions.indices) {
            val instruction = instructions[instructionPointer.toIntChecked()]
            if (instruction is Receive) {
                isReceiving = true
            }
            val result = instruction.executeOn(registers)
            isReceiving = false
            if (instruction is Recover && registers.getValue(instruction.register) != 0L) {
                println("program ${registers[PROGRAM_NUMBER_REGISTER]}: instruction \"Recover\" with register not zero -> terminating")
                return result
            }
            instructionPointer += if (instruction is Jump) {
                result
            } else {
                1L
            }
        }
        println("program ${registers.getValue(PROGRAM_NUMBER_REGISTER)}: instruction pointer ran out of bounds: $instructionPointer -> terminating")
        return -1L
    }

    fun getNumberOfSends(): Long = registers.getValue(NUMBER_OF_SENDS_REGISTER)
    fun getNumberOfMultiplies(): Long = registers.getValue(NUMBER_OF_MULTIPLY_REGISTER)
    fun getRegisterValue(register: String): Long = registers.getValue(register)
}
