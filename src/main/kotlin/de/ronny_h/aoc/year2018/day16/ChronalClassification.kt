package de.ronny_h.aoc.year2018.day16

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split

fun main() = ChronalClassification().run(531, 649)

class ChronalClassification : AdventOfCode<Int>(2018, 16) {
    override fun part1(input: List<String>): Int {
        val deviceAnalyzer = WristDeviceAnalyzer(input)
        return deviceAnalyzer.behaveLikeNumberOfOpcodes().filter { it > 2 }.size
    }

    override fun part2(input: List<String>): Int {
        val deviceAnalyzer = WristDeviceAnalyzer(input)
        val operations = deviceAnalyzer.deduceOpcodeMapping()
        return WristDevice(input.parseTestProgram()).runProgram(operations)
    }
}

class WristDeviceAnalyzer(input: List<String>) {
    private val samples = input.parseCPUSamples()

    fun behaveLikeNumberOfOpcodes() =
        samples.map { sample -> WristDevice.operations.count { it.producesTheRightOutput(sample) } }

    fun deduceOpcodeMapping(): List<(Int, Int, Int, MutableList<Int>) -> Unit> {
        val samplesByOpcode = samples.groupBy { it.opcode }
        val opCodesToOperationCandidates = buildList {
            for (i in WristDevice.operations.indices) {
                add(WristDevice.operations.filter { op ->
                    samplesByOpcode.getValue(i).all { op.producesTheRightOutput(it) }
                })
            }
        }

        val opCodeMapping = MutableList<((Int, Int, Int, MutableList<Int>) -> Unit)?>(16) { null }
        val alreadyMappedOperations = mutableSetOf<(Int, Int, Int, MutableList<Int>) -> Unit>()

        while (alreadyMappedOperations.size < 16) {
            opCodesToOperationCandidates.forEachIndexed { i, ops ->
                val remainingOps = ops - alreadyMappedOperations
                if (remainingOps.size == 1) {
                    opCodeMapping[i] = remainingOps.first()
                    alreadyMappedOperations.add(remainingOps.first())
                }
            }
        }
        check(opCodeMapping.all { it != null })
        return opCodeMapping.filterNotNull().toList()
    }

    private fun ((Int, Int, Int, MutableList<Int>) -> Unit).producesTheRightOutput(
        sample: CPUSample
    ): Boolean {
        val registers = sample.registersBefore.toMutableList()
        this(sample.a, sample.b, sample.c, registers)
        return registers == sample.registersAfter
    }
}

class WristDevice(
    private val program: List<ProgramStep>,
    private val registers: MutableList<Int> = mutableListOf(0, 0, 0, 0),
    private val preOperation: (instructionPointer: Int, registers: MutableList<Int>) -> Unit = { _, _ -> },
    private val postOperation: (instructionPointer: Int, registers: MutableList<Int>) -> Int = { ptr, _ -> ptr },
) {
    companion object {
        private val addr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] + registers[inB]
        }
        private val addi = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] + inB
        }
        private val mulr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] * registers[inB]
        }
        private val muli = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] * inB
        }
        private val banr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] and registers[inB]
        }
        private val bani = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] and inB
        }
        private val borr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] or registers[inB]
        }
        private val bori = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA] or inB
        }
        private val setr = { inA: Int, _: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = registers[inA]
        }
        private val seti = { inA: Int, _: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = inA
        }
        private val gtir = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (inA > registers[inB]) 1 else 0
        }
        private val gtri = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (registers[inA] > inB) 1 else 0
        }
        private val gtrr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (registers[inA] > registers[inB]) 1 else 0
        }
        private val eqir = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (inA == registers[inB]) 1 else 0
        }
        private val eqri = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (registers[inA] == inB) 1 else 0
        }
        private val eqrr = { inA: Int, inB: Int, outC: Int, registers: MutableList<Int> ->
            registers[outC] = if (registers[inA] == registers[inB]) 1 else 0
        }

        val operations =
            listOf(addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)

        val operationNames =
            listOf(
                "addr",
                "addi",
                "mulr",
                "muli",
                "banr",
                "bani",
                "borr",
                "bori",
                "setr",
                "seti",
                "gtir",
                "gtri",
                "gtrr",
                "eqir",
                "eqri",
                "eqrr",
            )
    }

    private var instructionPointer = 0

    fun runProgram(operations: List<(Int, Int, Int, MutableList<Int>) -> Unit>): Int {
        while (instructionPointer in program.indices) {
            preOperation(instructionPointer, registers)
            with(program[instructionPointer]) {
                operations[opCode](a, b, c, registers)
            }
            instructionPointer = postOperation(instructionPointer, registers)
            instructionPointer++
        }
        return registers[0]
    }
}

fun List<String>.parseCPUSamples(): List<CPUSample> {
    return buildList {
        for (block in split()) {
            if (!block.first().startsWith("Before")) {
                break
            }
            val registersBefore = block[0].parseRegister("Before: ")
            val operation = block[1].split(" ").map(String::toInt)
            val registersAfter = block[2].parseRegister("After:  ")
            add(CPUSample(registersBefore, operation[0], operation[1], operation[2], operation[3], registersAfter))
        }
    }
}

private fun String.parseRegister(prefix: String) =
    substringAfter("$prefix[").substringBefore("]").split(", ").map(String::toInt)

data class CPUSample(
    val registersBefore: List<Int>,
    val opcode: Int,
    val a: Int,
    val b: Int,
    val c: Int,
    val registersAfter: List<Int>
)

data class ProgramStep(val opCode: Int, val a: Int, val b: Int, val c: Int)

fun List<String>.parseTestProgram(): List<ProgramStep> {
    var emptyLines = 0
    val iterator = iterator()
    while (emptyLines < 3) {
        val line = iterator.next()
        if (line.isEmpty()) {
            emptyLines++
        } else {
            emptyLines = 0
        }
    }
    return buildList {
        while (iterator.hasNext()) {
            val (opCode, a, b, c) = iterator.next().split(" ").map { it.toInt() }
            add(ProgramStep(opCode, a, b, c))
        }
    }
}
