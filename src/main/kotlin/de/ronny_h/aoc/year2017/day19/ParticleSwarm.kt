package de.ronny_h.aoc.year2017.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Direction.*
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid

fun main() = ParticleSwarm().run("EOCZQMURF", "16312")

class ParticleSwarm : AdventOfCode<String>(2017, 19) {
    override fun part1(input: List<String>): String = RoutingDiagram(input).followThePath().letters

    override fun part2(input: List<String>): String = RoutingDiagram(input).followThePath().length.toString()
}

data class Path(val letters: String, val length: Int)

class RoutingDiagram(input: List<String>) : SimpleCharGrid(input, ' ') {
    fun findStartCoordinates(): Coordinates = find('|')

    fun followThePath(): Path {
        val letters = mutableListOf<Char>()
        var position = findStartCoordinates()
        var direction = SOUTH
        var steps = 0

        while (getAt(position) != nullElement) {
            if (getAt(position).isLetter()) {
                letters.add(getAt(position))
            }

            position += direction
            steps++

            if (getAt(position) == '+') {
                val turnRight = direction.turnRight()
                val charAtRight = getAt(position + turnRight)
                direction = if (charAtRight == turnRight.char() || charAtRight.isLetter()) {
                    turnRight
                } else {
                    direction.turnLeft()
                }
            }
        }
        return Path(letters.joinToString(""), steps)
    }

    private fun Direction.char() = when (this) {
        NORTH -> '|'
        EAST -> '-'
        SOUTH -> '|'
        WEST -> '-'
    }
}
