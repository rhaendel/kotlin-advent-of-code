package de.ronny_h.aoc.year2024.day08

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.combinations
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = ResonantCollinearity().run(214, 809)

class ResonantCollinearity : AdventOfCode<Int>(2024, 8) {
    override fun part1(input: List<String>) = CityMap(input).collectAntinodes().size

    override fun part2(input: List<String>): Int {
        val map = CityMap(input)
        // map.printGrid()
        val antinodes = map.collectAntinodes(withResonantHarmonics = true)
        // map.printGrid(antinodes)
        return antinodes.size
    }
}

private class CityMap(input: List<String>) : Grid<Char>(input, '.') {

    override fun Char.toElementType() = this

    private val colIndices = input[0].indices
    private val rowIndices = input.indices

    private val antennas: Map<Char, List<Coordinates>> = buildMap<Char, MutableList<Coordinates>> {
        forEachElement { row, col, char ->
            if (char != nullElement) {
                getOrPut(char, ::mutableListOf).add(Coordinates(row, col))
            }
        }.last()
    }

    fun collectAntinodes(withResonantHarmonics: Boolean = false): HashSet<Coordinates> {
        val antinodes = HashSet<Coordinates>()

        antennas.entries.forEach { entry ->
            val antennasOfSameFrequency = entry.value
            if (withResonantHarmonics) {
                antinodes.addAll(antennasOfSameFrequency)
            }
            antennasOfSameFrequency.combinations().forEach {
                val diff = it.second - it.first
                var antinode = it.second + diff
                if (withResonantHarmonics) {
                    while (antinode.isOnTheMap()) {
                        antinodes.add(antinode)
                        antinode += diff
                    }
                } else {
                    if (antinode.isOnTheMap()) {
                        antinodes.add(antinode)
                    }
                }
            }
        }

        return antinodes
    }

    private fun Coordinates.isOnTheMap() = x in colIndices && y in rowIndices
}
