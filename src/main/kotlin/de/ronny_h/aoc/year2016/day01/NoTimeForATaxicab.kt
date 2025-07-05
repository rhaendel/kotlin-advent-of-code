package de.ronny_h.aoc.year2016.day01

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Coordinates.Companion.ZERO
import de.ronny_h.aoc.extensions.Direction.NORTH

fun main() = NoTimeForATaxicab().run(239, 141)

class NoTimeForATaxicab : AdventOfCode<Int>(2016, 1) {
    override fun part1(input: List<String>): Int = input.parseInstructions().followInstructions() taxiDistanceTo ZERO

    private fun List<String>.parseInstructions(): List<String> = first().split(", ")

    private fun List<String>.followInstructions(stopAtFirstLocationVisitedTwice: Boolean = false): Coordinates {
        var position = ZERO
        var direction = NORTH
        val visited = mutableSetOf(position)

        forEach {
            val turn = it.first()
            val steps = it.substring(1).toInt()

            direction = when (turn) {
                'L' -> direction.turnLeft()
                'R' -> direction.turnRight()
                else -> error("Invalid turn: $turn")
            }

            repeat(steps) {
                position += direction
                if (stopAtFirstLocationVisitedTwice) {
                    if (!visited.add(position)) {
                        return position
                    }
                }
            }
        }
        return position
    }

    override fun part2(input: List<String>): Int =
        input.parseInstructions().followInstructions(true) taxiDistanceTo ZERO
}
