package de.ronny_h.aoc.year2017.day22

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction.NORTH

fun main() = SporificaVirus().run(5538, 2511090)

class SporificaVirus : AdventOfCode<Int>(2017, 22) {
    override fun part1(input: List<String>): Int {
        val grid = SeeminglyInfiniteGrid(input)
        grid.burst1(10000)
        return grid.causedInfection
    }

    override fun part2(input: List<String>): Int {
        val grid = SeeminglyInfiniteGrid(input)
        grid.burst2(10000000)
        return grid.causedInfection
    }
}

class SeeminglyInfiniteGrid(input: List<String>) {
    private val clean = '.'
    private val infected = '#'
    private val weakened = 'W'
    private val flagged = 'F'

    private val grid = mutableMapOf<Coordinates, Char>().withDefault { clean }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, node ->
                grid[Coordinates(row, col)] = node
            }
        }
    }

    private var direction = NORTH
    private var position = Coordinates(input.size / 2, input[0].length / 2)
    var causedInfection = 0
        private set

    fun burst1(times: Int) = repeat(times) {
        if (grid[position] == infected) {
            direction = direction.turnRight()
            grid[position] = clean
        } else {
            direction = direction.turnLeft()
            grid[position] = infected
            causedInfection++
        }
        position += direction
    }

    fun burst2(times: Int) = repeat(times) {
        when (grid.getValue(position)) {
            clean -> {
                direction = direction.turnLeft()
                grid[position] = weakened
            }

            weakened -> {
                // no turn
                grid[position] = infected
                causedInfection++
            }

            infected -> {
                direction = direction.turnRight()
                grid[position] = flagged
            }

            flagged -> {
                direction = direction.reverse()
                grid[position] = clean
            }
        }
        position += direction
    }
}
