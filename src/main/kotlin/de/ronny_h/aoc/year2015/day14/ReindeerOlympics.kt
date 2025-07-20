package de.ronny_h.aoc.year2015.day14

import de.ronny_h.aoc.AdventOfCode
import kotlin.math.min

fun main() = ReindeerOlympics().run(2660, 1256)

class ReindeerOlympics : AdventOfCode<Int>(2015, 14) {
    override fun part1(input: List<String>): Int = input.parse().maxReindeerDistanceIn(2503)
    override fun part2(input: List<String>): Int = input.parse().pointsOfWinnerIn(2503)
}

fun List<Reindeer>.maxReindeerDistanceIn(secondsTotal: Int) = map { it.reindeerDistanceIn(secondsTotal) }.max()

fun List<Reindeer>.pointsOfWinnerIn(secondsTotal: Int): Int {
    val reindeerPoints = MutableList(size) { 0 }
    for (seconds in 1..secondsTotal) {
        val distances = map { it.reindeerDistanceIn(seconds) }.withIndex()
        val leaderDistance = distances.maxOf { it.value }
        val leaders = distances.filter { it.value == leaderDistance }
        leaders.forEach {
            reindeerPoints[it.index]++
        }
    }
    return reindeerPoints.max()
}

private fun Reindeer.reindeerDistanceIn(secondsTotal: Int): Int {
    val cycleDuration = flyDuration + restDuration
    val cycles = secondsTotal / cycleDuration
    val remainingFlyDuration = min(secondsTotal - (cycleDuration * cycles), flyDuration)
    return speed * (flyDuration * cycles + remainingFlyDuration)
}

data class Reindeer(val speed: Int, val flyDuration: Int, val restDuration: Int)

fun List<String>.parse() = map {
    val (speed, flyDuration, restDuration) = it
        .substringAfter(" can fly ")
        .substringBeforeLast(" seconds.")
        .split(" km/s for ", " seconds, but then must rest for ")
    Reindeer(speed.toInt(), flyDuration.toInt(), restDuration.toInt())
}
