package de.ronny_h.aoc.year2024.day25

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split
import de.ronny_h.aoc.extensions.combinationsOf

fun main() = CodeChronicle().run(2993, 0)

class CodeChronicle : AdventOfCode<Int>(2024, 25) {
    companion object {
        private val filled = '#'
        private val empty = '.'

        fun List<String>.convertToLocksAndKeys() = split()
            .map { block ->
                if (block.first().all { it == filled } && block.last().all { it == empty }) {
                    Lock(block.countHeights())
                } else if (block.first().all { it == empty } && block.last().all { it == filled }) {
                    Key(block.countHeights())
                } else {
                    error("The current block is neither a lock nor a key: '$block'")
                }
            }

        fun Key.fitsIntoLockWithoutOverlapping(lock: Lock) = (0..4).all { column ->
            this.heights[column] + lock.pins[column] <= 5
        }

        private fun List<String>.countHeights(): List<Int> = (0..4).map { column ->
            count { it[column] == filled } - 1
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun part1(input: List<String>): Int {
        val (keys, locks) = input
            .convertToLocksAndKeys()
            .partition { it is Key } as Pair<List<Key>, List<Lock>>
        return combinationsOf(keys, locks).count { (key, lock) ->
            key.fitsIntoLockWithoutOverlapping(lock)
        }
    }

    override fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
    }
}

sealed interface VirtualFivePinTumbler

data class Lock(val pins: List<Int>) : VirtualFivePinTumbler {
    init {
        require(pins.size == 5) { "Number of pins is not 5: $pins" }
    }
}

data class Key(val heights: List<Int>) : VirtualFivePinTumbler {
    init {
        require(heights.size == 5) { "Number of heights is not 5: $heights" }
    }
}
