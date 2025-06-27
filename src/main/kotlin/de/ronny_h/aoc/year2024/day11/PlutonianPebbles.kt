package de.ronny_h.aoc.year2024.day11

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.digitCount
import de.ronny_h.aoc.extensions.memoize

fun main() = PlutonianPebbles().run(193899, 229682160383225)

class PlutonianPebbles : AdventOfCode<Long>(2024, 11) {
    private fun parseStones(input: List<String>) = input
        .first()
        .split(" ")
        .map(String::toLong)
        .toMutableList()

    private fun tenToPowerOf(digitCount: Int): Int {
        var tens = 10
        repeat((digitCount / 2) - 1) {
            tens *= 10
        }
        return tens
    }

    private val cacheDigitCounts = mutableMapOf<Long, Int>()
    private val cachePowers = IntArray(20) { tenToPowerOf(it) }

    private fun blink(stone: Long): List<Long> {
        if (stone == 0L) {
            return listOf(1)
        }
        val digitCount = cacheDigitCounts.getOrPut(stone) { stone.digitCount() }
        if (digitCount % 2 == 0) {
            val tens = cachePowers[digitCount]
            return listOf(stone.div(tens), stone % tens)
        }
        return listOf(stone * 2024)
    }

    private data class State(val height: Int, val stones: List<Long>)

    private fun List<Long>.blink(times: Int): Long {
        lateinit var blinkRec: (State) -> Long

        blinkRec = { state: State ->
            if (state.height == 0) {
                state.stones.size.toLong()
            } else if (state.stones.size > 1) {
                blinkRec(State(state.height, state.stones.subList(0, state.stones.size/2))) + blinkRec(State(state.height, state.stones.subList(state.stones.size/2, state.stones.size)))
            } else {
                blinkRec(State(state.height - 1, blink(state.stones.single())))
            }
        }.memoize()

        return blinkRec(State(times, this))
    }

    override fun part1(input: List<String>) = parseStones(input)
        .blink(25)

    override fun part2(input: List<String>) = parseStones(input)
        .blink(75)
}
