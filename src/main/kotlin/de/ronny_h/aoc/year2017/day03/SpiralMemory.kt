package de.ronny_h.aoc.year2017.day03

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Coordinates.Companion.ZERO
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Direction.SOUTH
import kotlin.math.abs

fun main() = SpiralMemory().run(480, 349975)

class SpiralMemory : AdventOfCode<Int>(2017, 3) {
    override fun part1(input: List<String>): Int = coordinatesOf(input.first().toInt()).taxiDistanceTo(ZERO)

    override fun part2(input: List<String>): Int {
        val number = input.first().toInt()
        var furtherStep = 0
        return sequenceOfCoordinatesAndSums()
            .takeWhile {
                if (it.second > number) {
                    furtherStep++
                }
                furtherStep < 2
            }
            .last()
            .second
    }
}

fun coordinatesOf(number: Int): Coordinates {
    fun Coordinates.isInACornerNotInSouthEast(): Boolean = abs(col) == abs(row) && (col <= 0 || row <= 0)
    fun Coordinates.isOneStepEastOfSouthEastCorner(): Boolean = col == row + 1 && col > 0

    return generateSequence(ZERO to SOUTH) { (coordinates, direction) ->
        val newDirection =
            if (coordinates.isInACornerNotInSouthEast() || coordinates.isOneStepEastOfSouthEastCorner()) {
                direction.turnLeft()
            } else {
                direction
            }
        (coordinates + newDirection) to newDirection
    }
        .take(number)
        .last()
        .first
}

fun sequenceOfCoordinatesAndSums(): Sequence<Pair<Coordinates, Int>> {
    val squares = mutableMapOf<Coordinates, Pair<Int, Direction>>()
    squares[ZERO] = 1 to SOUTH
    val sequence = generateSequence(ZERO to 1) { (coordinates, _) ->
        val (_, direction) = squares.getValue(coordinates)
        val toTheLeft = coordinates + direction.turnLeft()
        val (newCoordinates, newDirection) = if (squares[toTheLeft] == null) {
            toTheLeft to direction.turnLeft()
        } else {
            (coordinates + direction) to direction
        }

        val sum = newCoordinates.neighboursIncludingDiagonals().sumOf { squares[it]?.first ?: 0 }
        squares[newCoordinates] = sum to newDirection

        newCoordinates to sum
    }
    return sequence
}
