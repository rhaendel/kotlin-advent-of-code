package de.ronny_h.aoc.year2024.day12

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Direction.NORTH
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid

const val verbose = false

fun main() = GardenGroups().run(1546338, 978590)

class GardenGroups : AdventOfCode<Int>(2024, 12) {
    override fun part1(input: List<String>) = Farm(input)
        .separateRegions()
        .sumOf { it.area * it.perimeter }

    override fun part2(input: List<String>) = Farm(input)
        .separateRegions()
        .sumOf { it.area * it.numberOfSides }
}

private data class Region(val area: Int, val perimeter: Int, val numberOfSides: Int)

private class Farm(input: List<String>) : SimpleCharGrid(input, ' ') {

    fun separateRegions(): List<Region> = clusterRegions()
        .map { region ->
            val plant = getAt(region.first())
            val perimeter = perimeterOf(region, plant)
            val numberOfSides = numberOfSidesOf(region, plant)
            if (verbose) {
                val regionAsSet = region.toSet()
                check(region.size == regionAsSet.size)
                printGrid(regionAsSet)
                println("---------- area: ${region.size} perimeter: $perimeter")
            }
            Region(region.size, perimeter, numberOfSides)
        }

    private fun perimeterOf(region: List<Coordinates>, plant: Char) = region.sumOf {
        it.neighbours().filter { n -> getAt(n) != plant }.size
    }

    private fun numberOfSidesOf(region: List<Coordinates>, plant: Char): Int {
        val boundary = region.filter { it.neighbours().any { n -> getAt(n) != plant } }
        val visited = mutableSetOf<Pair<Coordinates, Direction>>()
        val sides = boundary.sumOf { start ->
            val walk = walkAroundTheBoundary(start, plant) { position, direction ->
                val goOn = visited.add(position to direction)
                if (verbose) {
                    println("-- $plant -------------------- continue: $goOn")
                    printGrid(
                        visited.map { it.first }.toSet(),
                        highlightPosition = position,
                        highlightDirection = direction
                    )
                }
                goOn
            }
            if (verbose) println(" walk around $plant: $walk")
            walk
        }
        if (verbose) println("sides of plant $plant: $sides")
        return sides
    }

    private fun walkAroundTheBoundary(
        start: Coordinates,
        plant: Char,
        visit: (Coordinates, Direction) -> Boolean,
    ): Int {
        var direction = findStartDirectionFor(start, plant)
        var position = start
        var sideCount = 0

        while (visit(position, direction)) {
            val stepAhead = position + direction
            // check if next tile is part of the same side
            if (getAt(stepAhead) == plant) {
                if (leftOf(stepAhead, direction) == plant) {
                    sideCount++
                    position = stepAhead
                    direction = direction.turnLeft()
                } // else the tile step ahead is part of the same side -> continue in that direction
                position += direction
            } else {
                sideCount++
                // here always leftOf(position, direction) != plant
                direction = direction.turnRight()
            }
        }
        return sideCount
    }

    private fun leftOf(position: Coordinates, direction: Direction) = getAt(position + direction.turnLeft())

    private fun findStartDirectionFor(start: Coordinates, plant: Char): Direction {
        val startDirection = NORTH
        var direction = startDirection
        var inLastDirection = getAt(start + direction)
        var inDirection = inLastDirection
        do {
            direction = direction.turnRight()
            inLastDirection = inDirection
            inDirection = getAt(start + direction)
        } while ((inLastDirection == plant || inDirection != plant) && direction != startDirection) // last condition prohibits endless loops on 1-tile gardens
        return direction
    }
}
