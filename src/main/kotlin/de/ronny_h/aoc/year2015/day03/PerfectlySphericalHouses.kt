package de.ronny_h.aoc.year2015.day03

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction

fun main() = PerfectlySphericalHouses().run(2592, 2360)

class PerfectlySphericalHouses : AdventOfCode<Int>(2015, 3) {
    override fun part1(input: List<String>): Int {
        require(input.size == 1)
        var position = Coordinates(0, 0)
        val visited = mutableSetOf<Coordinates>(position)
        input.parseDirections()
            .forEach {
                position += it
                visited.add(position)
            }
        return visited.size
    }

    override fun part2(input: List<String>): Int {
        require(input.size == 1)
        var santaPosition = Coordinates(0, 0)
        var robotSantaPosition = santaPosition
        val visited = mutableSetOf<Coordinates>(santaPosition)
        input.parseDirections()
            .forEachIndexed { i, direction ->
                if (i % 2 == 0) {
                    robotSantaPosition += direction
                    visited.add(robotSantaPosition)
                } else {
                    santaPosition += direction
                    visited.add(santaPosition)
                }
            }
        return visited.size
    }

    private fun List<String>.parseDirections() = first()
        .map {
            when (it) {
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                '>' -> Direction.EAST
                '<' -> Direction.WEST
                else -> error("invalid direction: $it")
            }
        }
}
