package de.ronny_h.aoc.year2017.day05

import de.ronny_h.aoc.AdventOfCode

fun main() = AMazeOfTwistyTrampolines().run(375042, 28707598)

class AMazeOfTwistyTrampolines : AdventOfCode<Int>(2017, 5) {
    override fun part1(input: List<String>): Int = countJumpsUntilOutOfBounds(input)
    override fun part2(input: List<String>): Int = countJumpsUntilOutOfBounds(input, true)

    private fun countJumpsUntilOutOfBounds(input: List<String>, decreaseIfJumpGreaterThanTwo: Boolean = false): Int {
        val jumpOffsets = input.map { it.toInt() }.toMutableList()

        var stepCount = 0
        var instructionPointer = 0
        while (instructionPointer in 0..jumpOffsets.lastIndex) {
            val jumpOffset = jumpOffsets[instructionPointer]
            jumpOffsets[instructionPointer] += if (decreaseIfJumpGreaterThanTwo && jumpOffset > 2) {
                -1
            } else {
                1
            }
            instructionPointer += jumpOffset
            stepCount++
        }
        return stepCount
    }
}
