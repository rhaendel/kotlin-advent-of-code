package de.ronny_h.aoc.year2017.day17

import de.ronny_h.aoc.AdventOfCode

fun main() = Spinlock().run(2000, 10242889)

class Spinlock : AdventOfCode<Int>(2017, 17) {
    override fun part1(input: List<String>): Int = insertIntoCircularBuffer(input.single().toInt())
    override fun part2(input: List<String>): Int = simulateInsertIntoCircularBuffer(input.single().toInt())

    private fun insertIntoCircularBuffer(steps: Int): Int {
        val circularBuffer = mutableListOf<Int>()
        var index = 0
        repeat(2017) { i ->
            circularBuffer.add(index, i)
            index = (index + steps) % circularBuffer.size + 1
        }
        return circularBuffer[index]
    }

    private fun simulateInsertIntoCircularBuffer(steps: Int): Int {
        var bufferSize = 0
        var index = 0
        var atIndex1 = -1
        repeat(50_000_000) { i ->
            if (index == 1) {
                atIndex1 = i
            }
            bufferSize++
            index = (index + steps) % bufferSize + 1
        }
        return atIndex1
    }
}
