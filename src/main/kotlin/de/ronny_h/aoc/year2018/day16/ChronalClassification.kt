package de.ronny_h.aoc.year2018.day16

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split

fun main() = ChronalClassification().run(531, 0)

class ChronalClassification : AdventOfCode<Int>(2018, 16) {
    override fun part1(input: List<String>): Int {
        val samples = input.parseCPUSamples()
        val device = WristDevice()
        return device.behaveLikeNumberOfOpcodes(samples).filter { it > 2 }.size
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class WristDevice {
    private val registers = mutableListOf(0, 0, 0, 0)

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

    private val operations =
        listOf(addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)

    fun behaveLikeNumberOfOpcodes(samples: List<CPUSample>) =
        samples.map { sample ->
            operations.count { op ->
                val registers = sample.registersBefore.toMutableList()
                op(sample.a, sample.b, sample.c, registers)
                registers == sample.registersAfter
            }
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
