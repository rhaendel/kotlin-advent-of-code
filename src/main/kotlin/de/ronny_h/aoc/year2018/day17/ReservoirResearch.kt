package de.ronny_h.aoc.year2018.day17

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.year2018.day17.Direction.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.collections.MutableMap.MutableEntry
import kotlin.math.max
import kotlin.math.min
import kotlin.text.Charsets.UTF_8

fun main() = ReservoirResearch().run(31934, 0)

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

    private val slice = MapBackedCharGrid(springCoordinates, sand)

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

    override fun toString(): String = slice.toString()

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

class MapBackedCharGrid(center: Coordinates, default: Char) {
    private var minClayY = Int.MAX_VALUE
    private var minX = center.x
    private var maxX = center.x
    private var minY = center.y
    var maxY = center.y
        private set

    private val slice = mutableMapOf<Coordinates, Char>().withDefault { default }

    operator fun set(position: Coordinates, value: Char) {
        check(value == clay || slice[position] != clay) { "clay must not be overwritten" }
        if (value == clay) {
            minClayY = min(minClayY, position.y)
        }
        minX = min(minX, position.x)
        minY = min(minY, position.y)
        maxX = max(maxX, position.x)
        maxY = max(maxY, position.y)
        slice[position] = value
    }

    operator fun set(x: Int, y: Int, value: Char) = set(Coordinates(x, y), value)

    operator fun get(position: Coordinates) = slice.getValue(position)

    operator fun set(x: Int, yRange: IntRange, value: Char) {
        for (y in yRange) {
            this[Coordinates(x, y)] = value
        }
    }

    operator fun set(xRange: IntRange, y: Int, value: Char) {
        for (x in xRange) {
            this[Coordinates(x, y)] = value
        }
    }

    override fun toString(): String {
        val out = ByteArrayOutputStream()
        PrintStream(out, true, UTF_8).use {
            for (y in minY..maxY) {
                for (x in minX - 1..maxX + 1) {
                    it.print(this[Coordinates(x, y)])
                }
                it.print('\n')
            }
        }
        return out.toString(UTF_8).trim()
    }

    fun count(predicate: (Char) -> Boolean): Int =
        slice.entries.filter { it.key.y >= minClayY }.map(MutableEntry<Coordinates, Char>::value)
            .count(predicate)
}
