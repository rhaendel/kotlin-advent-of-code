package de.ronny_h.aoc.year2018.day22

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Coordinates.Companion.ZERO
import de.ronny_h.aoc.year2018.day22.RegionType.*

fun main() = ModeMaze().run(8681, 0)

class ModeMaze : AdventOfCode<Int>(2018, 22) {
    override fun part1(input: List<String>): Int = CaveSystem(input).areaRiskLevel()

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class CaveSystem(input: List<String>) {
    val depth: Int = input[0].substringAfter("depth: ").toInt()
    val target: Coordinates = input[1].substringAfter("target: ").split(',').map(String::toInt).let {
        Coordinates(it[0], it[1])
    }

    private val geologicalIndexCache = HashMap<Coordinates, Int>()

    fun geologicIndex(region: Coordinates): Int {
        geologicalIndexCache[region]?.let {
            return it
        }

        val result = when {
            region == ZERO -> 0
            region == target -> 0
            region.y == 0 -> region.x * 16807
            region.x == 0 -> region.y * 48271
            else -> erosionLevel(region.copy(x = region.x - 1)) * erosionLevel(region.copy(y = region.y - 1))
        }
        geologicalIndexCache[region] = result
        return result
    }

    fun erosionLevel(region: Coordinates) = (geologicIndex(region) + depth) % 20183

    fun typeOf(region: Coordinates) = when (erosionLevel(region) % 3) {
        0 -> ROCKY
        1 -> WET
        2 -> NARROW
        else -> error("invalid erosion level at $region")
    }

    fun areaRiskLevel() = (0..target.x).sumOf { x ->
        (0..target.y).sumOf { y ->
            typeOf(Coordinates(x, y)).riskLevel()
        }
    }
}

enum class RegionType {
    ROCKY, WET, NARROW;

    fun riskLevel(): Int = ordinal
}
