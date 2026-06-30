package de.ronny_h.aoc.year2018.day23

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.threedim.Vector
import de.ronny_h.aoc.extensions.threedim.Vector.Companion.ZERO

fun main() = ExperimentalEmergencyTeleportation().run(396, 119406340)

class ExperimentalEmergencyTeleportation : AdventOfCode<Long>(2018, 23) {
    override fun part1(input: List<String>): Long {
        val bots = input.parseBots()
        val strongestBot = bots.maxBy { it.radius }
        val inRange = bots.filter { (strongestBot.position taxiDistanceTo it.position) <= strongestBot.radius }
        return inRange.count().toLong()
    }

    override fun part2(input: List<String>): Long = input
        .parseBots()
        .clusterBotsWithIntersectingRanges()
        .maxBy(Set<Nanobot>::size)
        // the bot farthest away with smallest range defines the nearest border of the intersected ranges
        .maxOf { (it.position taxiDistanceTo ZERO) - it.radius }

    private fun List<Nanobot>.clusterBotsWithIntersectingRanges(): List<MutableSet<Nanobot>> = mapIndexed { i, bot ->
        val intersecting = mutableSetOf(bot)
        for (j in i + 1..lastIndex) {
            val b = this[j]
            if (intersecting.all { it.position taxiDistanceTo b.position <= it.radius + b.radius }) {
                intersecting.add(b)
            }
        }
        intersecting
    }
}

data class Nanobot(val position: Vector, val radius: Long)

private val nanobotPattern = """pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""".toPattern()

fun List<String>.parseBots(): List<Nanobot> = map {
    val matcher = nanobotPattern.matcher(it)
    matcher.find()
    check(matcher.groupCount() == 4)
    Nanobot(
        position = Vector(
            matcher.group(1).toLong(),
            matcher.group(2).toLong(),
            matcher.group(3).toLong(),
        ),
        radius = matcher.group(4).toLong(),
    )
}
