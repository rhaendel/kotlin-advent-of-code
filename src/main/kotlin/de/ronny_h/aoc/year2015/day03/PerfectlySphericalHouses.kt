package de.ronny_h.aoc.year2015.day03

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction

fun main() = PerfectlySphericalHouses().run(2592, 2360)

class PerfectlySphericalHouses : AdventOfCode<Int>(2015, 3) {
    override fun part1(input: List<String>): Int = input
        .parseDirections()
        .visitCoordinates()
        .size

    override fun part2(input: List<String>): Int {
        val (santa, roboSanta) = input
            .parseDirections()
            .withIndex()
            .partition { it.index % 2 == 0 }

        return santa.map { it.value }.visitCoordinates()
            .union(
                roboSanta.map { it.value }.visitCoordinates()
            )
            .size
    }

    private fun List<String>.parseDirections(): List<Direction> {
        require(size == 1)
        return first()
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

    private fun List<Direction>.visitCoordinates(): Set<Coordinates> {
        var position = Coordinates(0, 0)
        val visited = mutableSetOf<Coordinates>(position)
        forEach {
            position += it
            visited.add(position)
        }
        return visited
    }
}
