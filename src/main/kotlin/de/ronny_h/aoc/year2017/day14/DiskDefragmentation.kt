package de.ronny_h.aoc.year2017.day14

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2017.day10.knotHash
import java.math.BigInteger

fun main() = DiskDefragmentation().run(8226, 1128)

class DiskDefragmentation : AdventOfCode<Int>(2017, 14) {
    override fun part1(input: List<String>): Int = input
        .single()
        .toBinaryKnotHashes()
        .sumOf { binary -> binary.count { it == '1' } }

    private fun String.toBinaryKnotHashes(): List<String> = (0..127)
        .map { "$this-$it" }
        .map { it.knotHash() }
        .map(::hexToBinary)

    override fun part2(input: List<String>): Int {
        val binaryKnotHashes = input
            .single()
            .toBinaryKnotHashes()

        return Disk(binaryKnotHashes)
            .clusterRegions()
            .size
    }
}

class Disk(input: List<String>) : SimpleCharGrid(input, '0') {

    fun clusterRegions(): List<List<Coordinates>> {
        val assigned = mutableSetOf<Coordinates>()
        return forEachCoordinates { position, element ->
            if (element == '1' && position !in assigned) {
                collectRegionAt(position, assigned)
            } else {
                null
            }
        }
            .filterNotNull()
            .toList()
    }

    private fun collectRegionAt(
        position: Coordinates,
        visited: MutableSet<Coordinates> = mutableSetOf(position),
        regionsCoordinates: MutableList<Coordinates> = mutableListOf(position),
    ): List<Coordinates> {
        position.neighbours().forEach { coordinates ->
            if (getAt(coordinates) == '1' && visited.add(coordinates)) {
                regionsCoordinates.add(coordinates)
                collectRegionAt(coordinates, visited, regionsCoordinates)
            }
        }
        return regionsCoordinates
    }
}

fun hexToBinary(hexString: String): String =
    hexString
        .map { ch -> BigInteger("$ch", 16).toString(2).padStart(4, '0') }
        .joinToString("")
