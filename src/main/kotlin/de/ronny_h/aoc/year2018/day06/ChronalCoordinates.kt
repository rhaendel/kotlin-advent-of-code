package de.ronny_h.aoc.year2018.day06

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.minByUniqueOrNull
import de.ronny_h.aoc.extensions.grids.Coordinates
import kotlin.math.max
import kotlin.math.min

fun main() = ChronalCoordinates().run(5187, 34829)

class ChronalCoordinates : AdventOfCode<Int>(2018, 6) {
    override fun part1(input: List<String>): Int =
        AreaGrid(input.parseCoordinates()).findMaxFiniteArea()

    override fun part2(input: List<String>): Int =
        AreaGrid(input.parseCoordinates()).sizeOfRegionWithDistanceLessThan(10000)
}

fun List<String>.parseCoordinates() = map {
    val (col, row) = it.split(", ")
    Coordinates(col.toInt(), row.toInt())
}

class AreaGrid(private val coordinates: List<Coordinates>) {
    // map grid coordinates to the nearest coordinates of the list above
    private val grid: Map<Coordinates, Coordinates>
    private val min: Coordinates
    private val max: Coordinates

    init {
        val boundaries = coordinates.findBoundaries()
        this.min = boundaries.first
        this.max = boundaries.second
        val mutableGrid = mutableMapOf<Coordinates, Coordinates>()
        allGridCoordinates()
            .map { current ->
                coordinates
                    .minByUniqueOrNull { it.taxiDistanceTo(current) }
                    ?.let { mutableGrid[current] = it }
            }
            .last()
        grid = mutableGrid.toMap()
    }

    fun findMaxFiniteArea() = collectFiniteAreas().maxOf { area -> grid.count { it.value == area } }

    fun sizeOfRegionWithDistanceLessThan(distance: Int): Int = allGridCoordinates()
        .map { coordinates.sumOf { c -> it.taxiDistanceTo(c) } }
        .filter { it < distance }
        .count()

    private fun allGridCoordinates() = sequence {
        for (row in min.y..max.y) {
            for (col in min.x..max.x) {
                yield(Coordinates(col, row))
            }
        }
    }

    private fun collectFiniteAreas(): Set<Coordinates> {
        // coordinates on the border are part of infinite areas -> filter them out
        val borderAreas = mutableSetOf<Coordinates>()
        for (col in min.x..max.x) {
            grid[Coordinates(col, min.y)]?.let { borderAreas.add(it) }
            grid[Coordinates(col, max.y)]?.let { borderAreas.add(it) }
        }
        for (row in min.y..max.y) {
            grid[Coordinates(min.x, row)]?.let { borderAreas.add(it) }
            grid[Coordinates(max.x, row)]?.let { borderAreas.add(it) }
        }
        return coordinates.toSet() - borderAreas
    }

    private fun List<Coordinates>.findBoundaries(): Pair<Coordinates, Coordinates> {
        var minRow = first().y
        var minCol = first().x
        var maxRow = minRow
        var maxCol = minCol
        forEach {
            minRow = min(minRow, it.y)
            minCol = min(minCol, it.x)
            maxRow = max(maxRow, it.y)
            maxCol = max(maxCol, it.x)
        }
        return Coordinates(minCol, minRow) to Coordinates(maxCol, maxRow)
    }
}
