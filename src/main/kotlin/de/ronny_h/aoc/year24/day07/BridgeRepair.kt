package de.ronny_h.aoc.year24.day07

import printAndCheck
import readInput
import kotlin.math.pow

fun main() {
    val day = "Day07"
    val input = readInput(day)
    val repair = BridgeRepair()

    println("$day part 1")
    printAndCheck(input, repair::part1, 1399219271639)

    println("$day part 2")
    printAndCheck(input, repair::part2, 275791737999003)
}

class BridgeRepair {
    private fun parseEquations(input: List<String>) = input.map { line ->
        val (result, numbers) = line.split(": ")
        Equation(result.toLong(), numbers.split(" ").map(String::toLong))
    }

    fun part1(input: List<String>) = parseEquations(input)
        .map { it.checkSolvability(listOf(Long::plus, Long::times)) }
        .filter { it.solvable }
        .sumOf { it.result }


    fun part2(input: List<String>) = parseEquations(input)
        .map { it.checkSolvability(listOf(Long::plus, Long::times, ::concat)) }
        .filter { it.solvable }
        .sumOf { it.result }
}

data class Equation(val result: Long, val numbers: List<Long>, var solvable: Boolean = false) {

    fun checkSolvability(operators: List<(Long, Long) -> Long>): Equation {
        val sequence = operatorIndexSequenceFor(numbers.size - 1, operators.size).iterator()
        while (sequence.hasNext()) {
            val result = numbers.drop(1).fold(numbers.first()) { acc, number ->
                operators[sequence.next()](acc, number)
            }
            if (result == this.result) {
                solvable = true
                return this
            }
        }
        return this
    }

    /**
     * With default parameters, returns a sequence of `0`s and `1`s corresponding to the 0-padded binary
     * representation of a counter value starting at 0.
     *
     * Examples:
     * - `operatorIndexSequenceFor(1)` yields `[0,1]`
     * - `operatorIndexSequenceFor(2)` yields `[0,0,0,1,1,0,1,1]` (counting from 0 `00` to 4 `11`)
     *
     * With different values of `operatorCount` the given value will be used as the radix for
     * a radix-based representation of the counter value.
     *
     * Example:
     * - `operatorIndexSequenceFor(1,3)` yields `[0,1,2]`
     * - `operatorIndexSequenceFor(2,3)` yields `[0,0,0,1,0,2,1,0,1,1,1,2,2,0,2,1,2,2]` (counting from 0 `00` to 9 `22`)
     */
    private fun operatorIndexSequenceFor(digits: Int, operatorCount: Int = 2) = sequence {
        for (count in 0..<(operatorCount.toDouble().pow(digits).toInt())) {
            val binary = count.toString(operatorCount).fillLeadingZeros(digits)
            for (i in 0..<digits) {
                yield(binary[i].digitToInt())
            }
        }
    }

    private fun String.fillLeadingZeros(digits: Int) = "0".repeat(digits - length) + this

}

private fun concat(first: Long, second: Long) = "$first$second".toLong()
