package de.ronny_h.aoc.year24.day08

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.combinations
import printAndCheck
import readInput

fun main() {
    val day = "Day08"
    val input = readInput(day)
    val collinearity = ResonantCollinearity()

    println("$day part 1")
    printAndCheck(input, collinearity::part1, 214)

    println("$day part 2")
    printAndCheck(input, collinearity::part2, 809)
}

class ResonantCollinearity {
    fun part1(input: List<String>) = CityMap(input).collectAntinodes().size

    fun part2(input: List<String>): Int {
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

    private fun Coordinates.isOnTheMap() = col in colIndices && row in rowIndices
}
