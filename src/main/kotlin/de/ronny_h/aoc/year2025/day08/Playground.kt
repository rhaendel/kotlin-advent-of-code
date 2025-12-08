package de.ronny_h.aoc.year2025.day08

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.symmetricalCombinations
import de.ronny_h.aoc.extensions.threedim.Vector
import de.ronny_h.aoc.extensions.threedim.Vector.Companion.ZERO

fun main() = Playground().run(122430, 8135565324)

class Playground : AdventOfCode<Long>(2025, 8) {
    override fun part1(input: List<String>): Long = JunctionBoxConnector(input).connectShortest(1000)

    override fun part2(input: List<String>): Long = JunctionBoxConnector(input).connectShortest()
}

fun List<String>.parseJunctionBoxes() = map { it.split(",").map(String::toLong) }
    .map { (x, y, z) -> Vector(x, y, z) }

class JunctionBoxConnector(input: List<String>) {

    private val boxes = input.parseJunctionBoxes()

    fun connectShortest(n: Int? = null): Long {
        val distances = boxes.pairwiseDistances()
            .sortedBy { it.distance }
            .let { dist -> n?.let { dist.take(n) } ?: dist }
        val circuits = mutableListOf<MutableSet<Vector>>()

        var lastMergingPair: Pair<Vector, Vector> = ZERO to ZERO

        distances.forEach { (boxes, _) ->
            val containsFirst = circuits.find { it.contains(boxes.first) }
            val containsSecond = circuits.find { it.contains(boxes.second) }

            when {
                containsFirst == null && containsSecond == null -> {
                    circuits.add(mutableSetOf(boxes.first, boxes.second))
                }

                containsFirst == containsSecond -> {
                    // already in same circuit -> do nothing
                }

                containsFirst != null && containsSecond != null -> {
                    containsFirst.addAll(containsSecond)
                    circuits.remove(containsSecond)
                    lastMergingPair = boxes
                }

                else -> {
                    containsFirst?.add(boxes.second)
                    containsSecond?.add(boxes.first)
                    lastMergingPair = boxes
                }
            }
        }

        return n?.let {
            circuits
                .map(MutableSet<Vector>::size)
                .sorted()
                .takeLast(3)
                .reduce(Int::times)
                .toLong()
        }
            ?: (lastMergingPair.first.x * lastMergingPair.second.x)
    }
}

fun List<Vector>.pairwiseDistances(): List<BoxDistance> = symmetricalCombinations().map { boxes ->
    val (a, b) = boxes
    BoxDistance(boxes, a straightLineDistanceTo b)
}.toList()

data class BoxDistance(val boxes: Pair<Vector, Vector>, val distance: Double)
