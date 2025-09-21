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
    Coordinates(row.toInt(), col.toInt())
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
        for (row in min.row..max.row) {
            for (col in min.col..max.col) {
                yield(Coordinates(row, col))
            }
        }
    }

    private fun collectFiniteAreas(): Set<Coordinates> {
        // coordinates on the border are part of infinite areas -> filter them out
        val borderAreas = mutableSetOf<Coordinates>()
        for (col in min.col..max.col) {
            grid[Coordinates(min.row, col)]?.let { borderAreas.add(it) }
            grid[Coordinates(max.row, col)]?.let { borderAreas.add(it) }
        }
        for (row in min.row..max.row) {
            grid[Coordinates(row, min.col)]?.let { borderAreas.add(it) }
            grid[Coordinates(row, max.col)]?.let { borderAreas.add(it) }
        }
        return coordinates.toSet() - borderAreas
    }

    private fun List<Coordinates>.findBoundaries(): Pair<Coordinates, Coordinates> {
        var minRow = first().row
        var minCol = first().col
        var maxRow = minRow
        var maxCol = minCol
        forEach {
            minRow = min(minRow, it.row)
            minCol = min(minCol, it.col)
            maxRow = max(maxRow, it.row)
            maxCol = max(maxCol, it.col)
        }
        return Coordinates(minRow, minCol) to Coordinates(maxRow, maxCol)
    }
}
