package de.ronny_h.aoc.year2025.day02

import de.ronny_h.aoc.AdventOfCode

fun main() = GiftShop().run(32976912643, 54446379122)

class GiftShop : AdventOfCode<Long>(2025, 2) {
    override fun part1(input: List<String>): Long =
        input.parseIdRanges()
            .filterInvalidIds(Long::isSequenceRepeatedTwice)
            .sum()

    override fun part2(input: List<String>): Long =
        input.parseIdRanges()
            .filterInvalidIds(Long::isSequenceRepeatedAtLeastTwice)
            .sum()
}

private fun List<LongRange>.filterInvalidIds(isInvalid: (Long) -> Boolean): List<Long> =
    flatMap { range ->
        range.filter { isInvalid(it) }
    }

fun Long.isSequenceRepeatedTwice(): Boolean {
    val id = "$this"
    if (id.length % 2 != 0) {
        return false
    }
    val first = id.take(id.length / 2)
    val second = id.takeLast(id.length / 2)
    return first == second
}

fun Long.isSequenceRepeatedAtLeastTwice(): Boolean {
    val id = "$this"
    for (size in 1..id.length / 2) {
        if (id.length % size != 0) {
            continue
        }
        val chunks = id.chunked(size)
        if (chunks.all { it == chunks.first() }) {
            return true
        }
    }
    return false
}

fun List<String>.parseIdRanges() = first()
    .split(",")
    .map { range ->
        val (from, to) = range
            .split("-")
            .map(String::toLong)
        from..to
    }
