package de.ronny_h.aoc.year2018.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2018.day16.ProgramStep
import de.ronny_h.aoc.year2018.day16.WristDevice

fun main() = GoWithTheFlow().run(1694, 0)

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

    fun one(r5: Int): Int {
        var result = 0
        var r3 = 1
        do {
            var r1 = 1
            do {
                if (r3 * r1 == r5) {
                    result += r3
                }
                r1++
            } while (r1 <= r5)
            r3++
        } while (r3 <= r5)
        return result
    }

    fun partOne(isPartOne: Boolean = true): Int {
        var r5 = 2 * 2 * 19 * 11 + (5 * 22 + 18) // 964
        println("r5: $r5")

        if (isPartOne) {
            return one(r5)
        }

        val toAdd = (27 * 28 + 29) * 30 * 14 * 32 // 10550400
        println("toAdd: $toAdd")
        r5 += toAdd
        return one(r5)
    }

    fun partTwo(): Int {
        return partOne(false)
    }
}
