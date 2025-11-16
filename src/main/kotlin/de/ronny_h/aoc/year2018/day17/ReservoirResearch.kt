package de.ronny_h.aoc.year2018.day17

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.math.max
import kotlin.math.min
import kotlin.text.Charsets.UTF_8

fun main() = ReservoirResearch().run(0, 0)

class ReservoirResearch : AdventOfCode<Int>(2018, 17) {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class VerticalSliceOfGround(input: List<String>) {
    private val clay = '#'
    private val sand = '.'
    private val waterSpring = '+'

    private val slice = MapBackedCharGrid(Coordinates(500, 0), sand)

    init {
        slice[Coordinates(500, 0)] = waterSpring
        input.forEach {
            val (single, range) = it.split(", ")
            if (single.startsWith('x')) {
                slice[single.intAfter("x="), range.intRangeAfter("y=")] = clay
            } else {
                slice[range.intRangeAfter("x="), single.intAfter("y=")] = clay
            }
        }
    }

    override fun toString(): String = slice.toString()

    private fun String.intAfter(delimiter: String): Int = substringAfter(delimiter).toInt()

    private fun String.intRangeAfter(delimiter: String): IntRange {
        val (from, to) = substringAfter(delimiter).split("..").map(String::toInt)
        return from..to
    }
}

class MapBackedCharGrid(center: Coordinates, default: Char) {
    private var minX = center.x
    private var maxX = center.x
    private var minY = center.y
    private var maxY = center.y

    private val slice = mutableMapOf<Coordinates, Char>().withDefault { default }

    operator fun set(position: Coordinates, value: Char) {
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
}
