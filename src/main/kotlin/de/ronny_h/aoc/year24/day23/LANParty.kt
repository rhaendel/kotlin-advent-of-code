package de.ronny_h.aoc.year24.day23

import de.ronny_h.aoc.AdventOfCode

fun main() = LANParty().run("1240", "am,aq,by,ge,gf,ie,mr,mt,rw,sn,te,yi,zb")

class LANParty : AdventOfCode<String>(2024, 23) {

    override fun part1(input: List<String>): String = Network(input)
        .searchForLANPartiesWithThreeComputers()
        .filter { set -> set.any { it.startsWith("t") } }
        .size
        .toString()

    override fun part2(input: List<String>): String = Network(input)
        .sortedConnectionSets()
        .findLargestIntersection()
        .sorted()
        .joinToString(",")
}

class Network(input: List<String>) {
    // a map entry for computer `c` contains all other computers that `c` is directly connected to
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
                val neighborsOtherNeighbors = connections.getValue(neighbor).filterNot { it == computer }
                val neighborsWithBacklinkToComputer =
                    neighborsOtherNeighbors.filter { connections.getValue(it).contains(computer) }
                neighborsWithBacklinkToComputer.forEach {
                    triples.add(setOf(computer, neighbor, it))
                }
            }
        }
        return triples
    }

    fun sortedConnectionSets() = connections
        .map { (computer, neighbors) -> neighbors + computer }
        .sortedBy { -it.size }
}

fun List<Set<String>>.findLargestIntersection(): Set<String> {
    // precondition: `this` is sorted in decreasing order by size
    var largestIntersection = emptySet<String>()
    for ((i, s1) in this.withIndex()) {
        for ((j, s2) in this.withIndex()) {
            if (i == j) continue
            if (s2.size < largestIntersection.size) {
                return largestIntersection
            }
            val intersection = s1 intersect s2
            if (intersection.size > largestIntersection.size) {
                if (this.count { it.containsAll(intersection) } == intersection.size) {
                    largestIntersection = intersection
                }
            }
        }
    }
    return largestIntersection
}
