package de.ronny_h.aoc.year24.day11

import de.ronny_h.aoc.extensions.memoize
import printAndCheck
import readInput
import kotlin.text.toLong

fun main() {
    val day = "Day11"
    val input = readInput(day)
    val plutonianPebbles = PlutonianPebbles()

    println("$day part 1")
    printAndCheck(input, plutonianPebbles::part1, 193899)

    println("$day part 2")
    printAndCheck(input, plutonianPebbles::part2, 229682160383225)
}

class PlutonianPebbles {
    private fun parseStones(input: List<String>) = input
        .first()
        .split(" ")
        .map(String::toLong)
        .toMutableList()

    private fun Long.digitCount(): Int {
        if (this == 0L) return 1

        var count = 0
        var currentNumber = this
        while (currentNumber > 0) {
            currentNumber /= 10
            count++
        }
        return count
    }

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

    fun part1(input: List<String>) = parseStones(input)
        .blink(25)

    fun part2(input: List<String>) = parseStones(input)
        .blink(75)
}
