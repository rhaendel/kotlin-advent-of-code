package de.ronny_h.aoc.year2015.day25

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.numbers.sumOfFirstNaturalNumbers

fun main() = LetItSnow().run(9132360, 0)

class LetItSnow : AdventOfCode<Int>(2015, 25) {
    override fun part1(input: List<String>): Int = generateSequence(20151125, ::nextCodeFor)
        .take(numberOfSequenceAt(input.parseCoordinates()))
        .last()

    override fun part2(input: List<String>) = 0
}

fun List<String>.parseCoordinates(): Coordinates {
    val (row, col) = first()
        .substringAfter("Enter the code at row ")
        .split(", column ")
    return Coordinates(row.toInt(), col.substring(0, col.lastIndex).toInt())
}

fun nextCodeFor(code: Int): Int = ((code * 252533L) % 33554393).toInt()

fun numberOfSequenceAt(coordinates: Coordinates): Int {
    return (sumOfFirstNaturalNumbers(coordinates.col)
            + sumOfFirstNaturalNumbers(coordinates.col + coordinates.row - 2)
            - sumOfFirstNaturalNumbers(coordinates.col - 1)
            ).toInt()
}
