package de.ronny_h.aoc.year2018.day17

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid
import de.ronny_h.aoc.extensions.grids.MapGridBackend
import de.ronny_h.aoc.year2018.day17.Direction.*
import kotlin.math.min

fun main() = ReservoirResearch().run(31934, 24790)

class ReservoirResearch : AdventOfCode<Int>(2018, 17) {
    override fun part1(input: List<String>): Int {
        val slice = VerticalSliceOfGround(input)
        slice.waterFlow(slice.springCoordinates)
        return slice.countTilesOfWater()
    }

    override fun part2(input: List<String>): Int {
        val slice = VerticalSliceOfGround(input)
        slice.waterFlow(slice.springCoordinates)
        return slice.countTilesOfRestingWater()
    }
}

private const val clay = '#'

class VerticalSliceOfGround(input: List<String>) {
    private val sand = '.'
    private val waterSpring = '+'
    private val waterFalling = '|'
    private val waterResting = '~'

    val springCoordinates = Coordinates(500, 0)

    private val slice = MapBackedCharGrid(sand)

    init {
        slice[springCoordinates] = waterSpring
        input.forEach {
            val (single, range) = it.split(", ")
            if (single.startsWith('x')) {
                slice[single.intAfter("x="), range.intRangeAfter("y=")] = clay
            } else {
                slice[range.intRangeAfter("x="), single.intAfter("y=")] = clay
            }
        }
    }

    fun waterFlow(spring: Coordinates) {
        // downwards
        var current = spring + DOWN
        while (slice[current] == sand) {
            if (current.y > slice.maxY) {
                return
            }
            slice[current] = waterFalling
            current += DOWN
        }
        if (slice[current] == waterFalling) {
            return
        }

        // sidewards & upwards
        var isOverflowing = false
        while (!isOverflowing) {
            current += UP
            val (leftmost, isOverflowingLeft) = current.fillToThe(LEFT)
            if (slice[current + RIGHT] == waterResting) {
                // a different recursive call has already filled up
                return
            }
            val (rightmost, isOverflowingRight) = current.fillToThe(RIGHT)
            isOverflowing = isOverflowingLeft || isOverflowingRight
            if (slice[current] != waterResting) {
                slice[leftmost.x..rightmost.x, current.y] = if (isOverflowing) waterFalling else waterResting
            }
        }
    }

    /**
     * @return A pair of `the farest Coordinates in [direction] belonging to the current body of water` and
     *   a Boolean telling if the water is overflowing.
     */
    private fun Coordinates.fillToThe(direction: Direction): Pair<Coordinates, Boolean> {
        var farestInDirection = this + direction
        while (true) {
            if (slice[farestInDirection] in listOf(sand, waterFalling)) {
                when (slice[farestInDirection + DOWN]) {
                    sand -> {
                        waterFlow(farestInDirection)
                        return farestInDirection to true
                    }

                    waterFalling -> {
                        // already been there
                        return farestInDirection to true
                    }

                    clay, waterResting -> {
                        farestInDirection += direction
                    }
                }
            } else if (slice[farestInDirection] == clay) {
                farestInDirection += direction.opposite()
                return farestInDirection to false
            }
        }
    }

    fun countTilesOfWater(): Int = slice.count { it in listOf(waterFalling, waterResting) }
    fun countTilesOfRestingWater(): Int = slice.count { it == waterResting }

    override fun toString(): String = toString(0)
    fun toString(padding: Int): String = slice.toString(padding = padding)

    private fun String.intAfter(delimiter: String): Int = substringAfter(delimiter).toInt()

    private fun String.intRangeAfter(delimiter: String): IntRange {
        val (from, to) = substringAfter(delimiter).split("..").map(String::toInt)
        return from..to
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun opposite() = when (this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }
}

operator fun Coordinates.plus(direction: Direction) = when (direction) {
    UP -> Coordinates(x, y - 1)
    DOWN -> Coordinates(x, y + 1)
    LEFT -> Coordinates(x - 1, y)
    RIGHT -> Coordinates(x + 1, y)
}

class MapBackedCharGrid(default: Char) :
    Grid<Char>(0, 0, default, grid = MapGridBackend(default)) {
    private var minClayY = Int.MAX_VALUE

    override fun Char.toElementType() = this

    override fun preSet(position: Coordinates, value: Char) {
        check(value == clay || get(position) != clay) { "clay must not be overwritten" }
        if (value == clay) {
            minClayY = min(minClayY, position.y)
        }
    }

    fun count(predicate: (Char) -> Boolean): Int =
        grid.entries.filter { it.first.y >= minClayY }.map(Pair<Coordinates, Char>::second)
            .count(predicate)

    val maxY: Int get() = grid.maxY
}
