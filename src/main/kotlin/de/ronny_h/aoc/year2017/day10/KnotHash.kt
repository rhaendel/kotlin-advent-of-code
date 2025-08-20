package de.ronny_h.aoc.year2017.day10

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.MutableRingList

fun main() = KnotHash().run("3770", "a9d0e68649d0174c8756a59ba21d4dc6")

class KnotHash : AdventOfCode<String>(2017, 10) {
    override fun part1(input: List<String>): String {
        val lengths = input.single().split(",").map(String::toInt)
        val size = if (lengths.size < 5) 5 else 256  // just for testing
        return sparseHashProduct(size, lengths).toString()
    }

    override fun part2(input: List<String>): String = input.single().knotHash()
}

fun String.knotHash(): String {
    val lengths = toASCII() + listOf(17, 31, 73, 47, 23)
    val denseHash = sparseHash(256, lengths, 64).reduceToDenseHash()
    check(denseHash.size == 16)
    return denseHash.joinToString("") { it.toString(16).padStart(2, '0') }
}

fun sparseHashProduct(size: Int, lengths: List<Int>): Int {
    val circularList = sparseHash(size, lengths)
    return circularList[0] * circularList[1]
}

private fun sparseHash(
    size: Int,
    lengths: List<Int>,
    rounds: Int = 1,
): List<Int> {
    val circularList = MutableRingList(size) { it }
    var position = 0
    var skipSize = 0

    repeat(rounds) {
        lengths.forEach { length ->
            circularList.reverseSubList(position, length)
            position = (position + length + skipSize) % size
            skipSize++
        }
    }
    return circularList.toList()
}

fun List<Int>.reduceToDenseHash() = chunked(16) {
    it.reduce { a, b -> a xor b }
}

fun String.toASCII() = map(Char::code)
