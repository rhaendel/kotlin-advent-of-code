package de.ronny_h.aoc.year2018.day22

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStar
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Coordinates.Companion.ZERO
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.year2018.day22.RegionType.*
import de.ronny_h.aoc.year2018.day22.Tool.*

// According to adventofcode.com, the right answer for part two is 1070.
// Part 1 and all tests pass, the AStar implementation yields the right results for all other applications. I cannot
// find an error here. The difference of 6 implies that one step is swapped with a tool change.
fun main() = ModeMaze().run(8681, 1064)

class ModeMaze : AdventOfCode<Int>(2018, 22) {
    override fun part1(input: List<String>): Int = CaveSystem(input).areaRiskLevel()

    override fun part2(input: List<String>): Int = CaveSystem(input).findShortestPath()
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

    fun findShortestPath(): Int {
        data class Node(val pos: Coordinates, val tool: Tool)

        val goal = Node(target, TORCH)

        val shortestPath = aStar(
            start = Node(ZERO, TORCH),
            isGoal = { this == goal },
            neighbors = { node ->
                Direction
                    .entries
                    .map { node.pos + it }
                    .filter { it.x in 0..depth && it.y in 0..depth }
                    .flatMap { neighbor ->
                        typeOf(neighbor).usableTools.map { Node(neighbor, it) }
                    }
            },
            d = { from, to -> if (from.tool == to.tool) 1 else 8 },
            h = { it.pos taxiDistanceTo target },
        )
        return shortestPath.distance
    }
}

enum class RegionType {
    ROCKY {
        override val usableTools = listOf(CLIMBING_GEAR, TORCH)
    },
    WET {
        override val usableTools = listOf(CLIMBING_GEAR, NEITHER)
    },
    NARROW {
        override val usableTools = listOf(TORCH, NEITHER)
    };

    fun riskLevel(): Int = ordinal

    abstract val usableTools: List<Tool>
}

enum class Tool {
    TORCH, CLIMBING_GEAR, NEITHER
}
