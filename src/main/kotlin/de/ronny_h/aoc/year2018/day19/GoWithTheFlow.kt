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

    //    private var ip = 0 // #ip 4
    private var r5 = 0

    fun one(): Boolean {
        // 1 seti 1 2 3
        r3 = 1
        do {
            // 2 seti 1 6 1
            r1 = 1
            do {
                // 3 mulr 3 1 2
                r2 = r3 * r1
                // 4 eqrr 2 5 2
//                r2 = if (r2 == r5) 1 else 0
                // 5 addr 2 4 4
//        ip += r2 // if (r2 == r5) goto 7
                // 6 addi 4 1 4
//        ip += 1 // goto 8
                if (r2 == r5) {
                    r2 = 1
                    // 7 addr 3 0 0
                    r0 += r3
                } else {
                    r2 = 0
                }
                // 8 addi 1 1 1
                r1 += 1
                // 9 gtrr 1 5 2
                r2 = if (r1 > r5) 1 else 0
                // 10 addr 4 2 4
//            ip += r2 // if (r1 > r5) goto 12
                // 11 seti 2 8 4
//            ip = 2 // goto 3
            } while (r1 <= r5)
            // 12 addi 3 1 3
            r3 += 1
            // 13 gtrr 3 5 2
            r2 = if (r3 > r5) 1 else 0
            // 14 addr 2 4 4
//            ip += r2 // if (r3 > r5) goto 16
            // 15 seti 1 4 4
//            ip = 1 // goto 2
        } while (r3 <= r5)
        // 16 mulr 4 4 4
//        ip *= ip // goto 16 * 16 -> HALT
        return true
    }

    fun partOne(): Int {
        // 0 addi 4 16 4
//        ip += 16 // goto 17
        // 17 addi 5 2 5
        r5 += 2
        // 18 mulr 5 5 5
        r5 *= r5
        // 19 mulr 4 5 5
        r5 *= 19 // ip
        // 20 muli 5 11 5
        r5 *= 11
        // 21 addi 2 5 2
        r2 += 5
        // 22 mulr 2 4 2
        r2 *= 22 // ip
        // 23 addi 2 18 2
        r2 += 18
        // 24 addr 5 2 5
        r5 += r2
        // 25 addr 4 0 4
//        ip += r0 // goto 26
        // 26 seti 0 6 4
//        ip = 0 // goto 1

        if (r0 == 0) {
            one()
            return r0
        }

        // 27 setr 4 8 2
        r2 = 27 // ip
        // 28 mulr 2 4 2
        r2 *= 28 // ip
        // 29 addr 4 2 2
        r2 += 29 // ip
        // 30 mulr 4 2 2
        r2 *= 30 // ip
        // 31 muli 2 14 2
        r2 *= 14
        // 32 mulr 2 4 2
        r2 *= 32 // ip
        // 33 addr 5 2 5
        r5 += r2
        // 34 seti 0 1 0
        r0 = 0
        // 35 seti 0 5 4
//        ip = 0 // goto 1
        one()
        return r0
    }

    fun partTwo(): Int {
        r0 = 1
        return partOne()
    }
}
