package de.ronny_h.aoc.year2018.day23

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.threedim.Vector

fun main() = ExperimentalEmergencyTeleportation().run(396, 0)

class ExperimentalEmergencyTeleportation : AdventOfCode<Int>(2018, 23) {
    override fun part1(input: List<String>): Int {
        val bots = input.parseBots()
        val strongestBot = bots.maxBy { it.radius }
        val inRange = bots.filter { (strongestBot.position taxiDistanceTo it.position) <= strongestBot.radius }
        return inRange.count()
    }

    override fun part2(input: List<String>): Int {
        return 0
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
