package de.ronny_h.aoc.year2025.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.memoize

fun main() = Reactor().run(643, 417190406827152)

class Reactor : AdventOfCode<Long>(2025, 11) {
    override fun part1(input: List<String>): Long = ServerRack(input).numberOfPathsToOut()

    override fun part2(input: List<String>): Long = ServerRack(input).numberOfPathsToOutVisitingDacAndFft()
}

data class Device(val id: String, val outputs: List<String>)

fun List<String>.parseDevices() = map {
    val (id, outputs) = it.split(": ")
    Device(id, outputs.split(" "))
}

class ServerRack(input: List<String>) {
    private val startId = "you"
    private val mainOutId = "out"
    private val serverId = "svr"
    private val dacId = "dac"
    private val fftId = "fft"

    private val devicesById = input.parseDevices().associateBy { it.id }

    fun numberOfPathsToOut(): Long = numberOfPathsBetween(startId to mainOutId)

    fun numberOfPathsToOutVisitingDacAndFft(): Long {
        val fromDacToFft = numberOfPathsBetween(dacId to fftId)

        return if (fromDacToFft != 0L) {
            numberOfPathsBetween(serverId to dacId) * fromDacToFft * numberOfPathsBetween(fftId to mainOutId)
        } else {
            numberOfPathsBetween(serverId to fftId) * numberOfPathsBetween(fftId to dacId) * numberOfPathsBetween(dacId to mainOutId)
        }
    }

    private fun numberOfPathsBetween(devices: Pair<String, String>): Long {
        println("searching from '${devices.first}' to '${devices.second}'")

        lateinit var numberOfPathsBetweenRec: (Pair<String, String>) -> Long
        numberOfPathsBetweenRec = { p: Pair<String, String> ->
            when (p.first) {
                p.second -> 1
                mainOutId -> 0
                else -> devicesById.getValue(p.first).outputs.sumOf { numberOfPathsBetweenRec(it to p.second) }
            }
        }.memoize()

        return numberOfPathsBetweenRec(devices).also { println("-> $it") }
    }
}
