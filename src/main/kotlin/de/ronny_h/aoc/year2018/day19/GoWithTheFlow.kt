package de.ronny_h.aoc.year2018.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2018.day16.ProgramStep
import de.ronny_h.aoc.year2018.day16.WristDevice

fun main() = GoWithTheFlow().run(1694, 18964204)

class GoWithTheFlow : AdventOfCode<Int>(2018, 19) {

    override fun part1(input: List<String>): Int {
        println("part one hard-coded: " + HardCodedWristDevice().partOne())
        return WristDeviceWithFlowControl(input).runProgram()
    }

    override fun part2(input: List<String>): Int {
        // does not terminate in reasonable time:
        // WristDeviceWithFlowControl(input, 1).runProgram()
        return HardCodedWristDevice().partTwo()
    }

}

data class Program(val instructionPointerRegister: Int, val program: List<ProgramStep>)

fun List<String>.parseProgram() = Program(
    instructionPointerRegister = first().substringAfter("#ip ").toInt(),
    program = drop(1).map {
        val (op, a, b, c) = it.split(" ")
        ProgramStep(WristDevice.operationNames.indexOf(op), a.toInt(), b.toInt(), c.toInt())
    }
)

class WristDeviceWithFlowControl(input: List<String>, registerZeroInitValue: Int = 0) {

    private val program = input.parseProgram()
    private val device = WristDevice(
        program = program.program,
        registers = mutableListOf(registerZeroInitValue, 0, 0, 0, 0, 0),
        preOperation = { instructionPointer, registers ->
            registers[program.instructionPointerRegister] = instructionPointer
        },
        postOperation = { _, registers -> registers[program.instructionPointerRegister] }
    )

    fun runProgram(): Int = device.runProgram(WristDevice.operations)
}

class HardCodedWristDevice {
    private fun sumOfDividers(input: Int): Int =
        (1..input).fold(0) { acc, i ->
            if (input % i == 0) acc + i else acc
        }

    fun partOne(): Int = sumOfDividers(964)

    fun partTwo(): Int = sumOfDividers(964 + 10550400)
}
