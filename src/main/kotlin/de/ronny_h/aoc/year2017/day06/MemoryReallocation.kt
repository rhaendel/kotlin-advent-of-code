package de.ronny_h.aoc.year2017.day06

import de.ronny_h.aoc.AdventOfCode

fun main() = MemoryReallocation().run(12841, 8038)

class MemoryReallocation : AdventOfCode<Int>(2017, 6) {
    override fun part1(input: List<String>): Int = input.findLoop().end
    override fun part2(input: List<String>): Int = input.findLoop().size

    private data class Loop(val end: Int, val size: Int)

    private fun List<String>.findLoop(): Loop {
        val seenConfigurations = mutableMapOf<List<Int>, Int>()
        var memoryBanks = first().split(" ", "\t").map(String::toInt)

        var cycles = -1
        var loopStart: Int?
        do {
            cycles++
            loopStart = seenConfigurations.put(memoryBanks, cycles)
            memoryBanks = memoryBanks.redistribute()
        } while (loopStart == null)
        return Loop(cycles, cycles - loopStart)
    }
}

fun List<Int>.redistribute(): List<Int> {
    val (index, max) = withIndex().maxBy { it.value }
    val newDistribution = toMutableList()
    newDistribution[index] = 0
    val toAddToAll = max / size
    var remainder = max % size
    if (toAddToAll > 0) {
        newDistribution.indices.forEach {
            newDistribution[it] += toAddToAll
        }
    }
    var remainderIndex = (index + 1) % size
    while (remainder > 0) {
        newDistribution[remainderIndex]++
        remainderIndex = (remainderIndex + 1) % size
        remainder--
    }
    return newDistribution
}
