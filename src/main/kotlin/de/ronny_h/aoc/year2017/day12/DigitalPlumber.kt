package de.ronny_h.aoc.year2017.day12

import de.ronny_h.aoc.AdventOfCode

fun main() = DigitalPlumber().run(134, 193)

class DigitalPlumber : AdventOfCode<Int>(2017, 12) {
    override fun part1(input: List<String>): Int = input.parseDirectConnections().groupOf(0).size

    override fun part2(input: List<String>): Int {
        val connections = input.parseDirectConnections()

        val groups = mutableListOf<List<Int>>()
        connections.keys.forEach { programId ->
            if (groups.none { it.contains(programId) }) {
                groups.add(connections.groupOf(programId))
            }
        }

        return groups.size
    }

    private fun List<String>.parseDirectConnections(): Map<Int, List<Int>> = associate {
        val (programId, connections) = it.split(" <-> ")
        programId.toInt() to connections.split(", ").map(String::toInt)
    }

    private fun Map<Int, List<Int>>.groupOf(start: Int): List<Int> {
        val group = mutableListOf(start)
        fun addTransitiveConnectionsOf(programId: Int) {
            this[programId]?.forEach {
                if (!group.contains(it)) {
                    group.add(it)
                    addTransitiveConnectionsOf(it)
                }
            }
        }
        addTransitiveConnectionsOf(start)
        return group
    }
}
