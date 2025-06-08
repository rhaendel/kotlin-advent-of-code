package de.ronny_h.aoc.year24.day13

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.isIntegral

fun main() = ClawContraption().run(33427, 91649162972270)

class ClawContraption : AdventOfCode<Long>(2024, 13) {
    private val buttonA = """Button A: X\+(\d+), Y\+(\d+)""".toRegex()
    private val buttonB = """Button B: X\+(\d+), Y\+(\d+)""".toRegex()
    private val prize = """Prize: X=(\d+), Y=(\d+)""".toRegex()
    private val buttonATokens = 3
    private val buttonBTokens = 1

    private fun List<String>.parseClawMachines(): List<ClawMachine> {
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

    private fun solveEquations(clawMachine: ClawMachine, offset: Long = 0): Long {
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

    override fun part1(input: List<String>) = input
        .parseClawMachines()
        .map(::solveEquations)
        .sumOf { it }


    override fun part2(input: List<String>) = input
        .parseClawMachines()
        .map { solveEquations(it, 10000000000000) }
        .sumOf { it }
}

data class ClawMachine(val ax: Int, val ay: Int, val bx: Int, val by: Int, val x: Int, val y: Int)
