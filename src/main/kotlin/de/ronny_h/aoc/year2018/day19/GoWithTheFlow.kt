package de.ronny_h.aoc.year2018.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2018.day16.ProgramStep
import de.ronny_h.aoc.year2018.day16.WristDevice

// part 2: wrong: 0
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
    private var r0 = 0
    private var r1 = 0
    private var r2 = 0
    private var r3 = 0
    private var r5 = 0

    fun one() {
        r3 = 1
        do {
            r1 = 1
            do {
                r2 = r3 * r1
                if (r2 == r5) {
                    r2 = 1
                    r0 += r3
                } else {
                    r2 = 0
                }
                r1 += 1
                r2 = if (r1 > r5) 1 else 0
            } while (r1 <= r5)
            r3 += 1
            r2 = if (r3 > r5) 1 else 0
        } while (r3 <= r5)
    }

    fun partOne(): Int {
        r5 += 2
        r5 *= r5
        r5 *= 19
        r5 *= 11
        r2 += 5
        r2 *= 22
        r2 += 18
        r5 += r2

        if (r0 == 0) {
            one()
            return r0
        }

        r2 = 27
        r2 *= 28
        r2 += 29
        r2 *= 30
        r2 *= 14
        r2 *= 32
        r5 += r2
        r0 = 0
        one()
        return r0
    }

    fun partTwo(): Int {
        r0 = 1
        return partOne()
    }
}
