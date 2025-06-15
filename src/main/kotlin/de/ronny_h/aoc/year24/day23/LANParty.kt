package de.ronny_h.aoc.year24.day23

import de.ronny_h.aoc.AdventOfCode

fun main() = LANParty().run(1240, 0)

class LANParty : AdventOfCode<Int>(2024, 23) {

    override fun part1(input: List<String>): Int = Network(input)
        .searchForLANPartiesWithThreeComputers()
        .filter { set -> set.any { it.startsWith("t") } }.size

    override fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
    }
}

class Network(input: List<String>) {
    // from one computer to all others it is directly connected to
    private val connections: Map<String, Set<String>>

    init {
        val mutableConnections: MutableMap<String, MutableSet<String>> = mutableMapOf()
        input.map {
            val (a, b) = it.split("-")
            mutableConnections.getOrPut(a) { mutableSetOf() }.add(b)
            mutableConnections.getOrPut(b) { mutableSetOf() }.add(a)
        }
        connections = mutableConnections
    }

    fun searchForLANPartiesWithThreeComputers(): Set<Set<String>> {
        val triples = mutableSetOf<Set<String>>()
        connections.forEach { (computer, neighbors) ->
            neighbors.forEach { neighbor ->
                val otherNeighborsNeighbors = connections.getValue(neighbor).filterNot { it == computer }
                val neighborsWithBacklinkToComputer =
                    otherNeighborsNeighbors.filter { connections.getValue(it).contains(computer) }
                neighborsWithBacklinkToComputer.forEach {
                    triples.add(setOf(computer, neighbor, it))
                }
            }
        }
        return triples
    }
}
