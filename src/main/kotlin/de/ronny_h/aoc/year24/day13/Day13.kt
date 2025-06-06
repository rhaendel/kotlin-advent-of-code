package de.ronny_h.aoc.year24.day13

import printAndCheck
import readInput
import kotlin.math.floor

fun main() {
    val day = "Day13"

    val buttonA = """Button A: X\+(\d+), Y\+(\d+)""".toRegex()
    val buttonB = """Button B: X\+(\d+), Y\+(\d+)""".toRegex()
    val prize = """Prize: X=(\d+), Y=(\d+)""".toRegex()
    val buttonATokens = 3
    val buttonBTokens = 1

    println("$day part 1")

    fun List<String>.parseClawMachines(): List<ClawMachine> {
        var ax = 0
        var ay = 0
        var bx = 0
        var by = 0
        var x = 0
        var y = 0

        return buildList {
            this@parseClawMachines.forEach { line ->
                if (line.matches(buttonA)) {
                    val (linesAx, linesAy) = buttonA.find(line)!!.destructured
                    ax = linesAx.toInt()
                    ay = linesAy.toInt()
                } else if (line.matches(buttonB)) {
                    val (linesBx, linesBy) = buttonB.find(line)!!.destructured
                    bx = linesBx.toInt()
                    by = linesBy.toInt()
                } else if (line.matches(prize)) {
                    val (linesX, linesY) = prize.find(line)!!.destructured
                    x = linesX.toInt()
                    y = linesY.toInt()
                } else if (line.isBlank()) {
                    this.add(ClawMachine(ax, ay, bx, by, x, y))
                }
            }
            this.add(ClawMachine(ax, ay, bx, by, x, y))
        }
    }

    fun solveEquations(clawMachine: ClawMachine, offset: Long = 0): Long {
        with(clawMachine) {
            // formulas for a and b are deduced from the linear equations that
            // result directly from the problem statement for each slot machine
            val a = (bx * (y + offset) - by * (x + offset)).toDouble() / (bx * ay - by * ax)
            val b = (x + offset - ax * a) / bx
            if (a.isIntegral() && b.isIntegral()) {
                if (offset == 0L) {
                    check(a <= 100 && b <= 100) {
                        "More than 100 button presses for $clawMachine: A: $a, B: $b"
                    }
                }
                check(a >= 0 && b >= 0) {
                    "Negative button presses for $clawMachine: A: $a, B: $b"
                }
                return (a * buttonATokens + b * buttonBTokens).toLong()
            }
            return 0
        }
    }

    fun part1(input: List<String>) = input
        .parseClawMachines()
        .map(::solveEquations)
        .sumOf { it }

    printAndCheck(
        """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
            
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
            
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
            
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines(),
        ::part1, 480
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 33427)


    println("$day part 2")

    fun part2(input: List<String>) = input
        .parseClawMachines()
        .map { solveEquations(it, 10000000000000) }
        .sumOf { it }

    printAndCheck(
        """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
            
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
            
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
            
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines(),
        ::part2, 875318608908
    )

    printAndCheck(input, ::part2, 91649162972270)
}

data class ClawMachine(val ax: Int, val ay: Int, val bx: Int, val by: Int, val x: Int, val y: Int)

private fun Double.isIntegral() = floor(this) == this
