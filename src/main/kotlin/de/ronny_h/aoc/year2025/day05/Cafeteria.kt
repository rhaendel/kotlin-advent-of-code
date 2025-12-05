package de.ronny_h.aoc.year2025.day05

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split

fun main() = Cafeteria().run(885, 348115621205535)

class Cafeteria : AdventOfCode<Long>(2025, 5) {
    override fun part1(input: List<String>): Long {
        with(input.parseIngredients()) {
            return available.count { it.isFresh(freshRanges) }.toLong()
        }
    }

    override fun part2(input: List<String>): Long =
        input.parseIngredients()
            .freshRanges
            .compact()
            .sumOf(LongRange::size)
}

data class Ingredients(val freshRanges: List<LongRange>, val available: List<Long>)

val LongRange.size: Long
    get() = last - first + 1

fun List<LongRange>.compact() =
    sortedBy { it.first }
        .fold(listOf<LongRange>()) { compactRanges, range ->
            compactRanges.mergeRange(range)
        }

// invariant: ranges in this List are non-overlapping and sorted
// pre-condition: add operations only occur with ranges sorted by their first value
fun List<LongRange>.mergeRange(toAdd: LongRange): List<LongRange> {
    if (none { toAdd.first in it || toAdd.last in it }) {
        // disjunct -> just add it
        return this + listOf(toAdd)
    }
    for ((i, range) in withIndex()) {
        if (toAdd.first in range && toAdd.last in range) {
            // toAdd is completely contained in this range
            return this
        }
        if (toAdd.first !in range && toAdd.last !in range) {
            continue
        }

        val lastOverlappingRangeIndex = indexOfLast { toAdd.last in it }
        if (toAdd.first in range && lastOverlappingRangeIndex == -1) {
            // the beginning overlaps with this range -> merge
            return take(i) + listOf(range.first..toAdd.last) + drop(i + 1)
        }

        // overlaps with several ranges -> squash them
        val lastOverlappingRange = this[lastOverlappingRangeIndex]
        return take(i) + listOf(range.first..lastOverlappingRange.last) + drop(lastOverlappingRangeIndex + 1)
    }
    error("If this line is reached, a case is missing")
}

fun Long.isFresh(freshRanges: List<LongRange>): Boolean = freshRanges.any { this in it }

fun List<String>.parseIngredients(): Ingredients {
    val (fresh, available) = split()
    return Ingredients(
        fresh.map {
            val (from, to) = it.split("-").map(String::toLong)
            from..to
        },
        available.map { it.toLong() }
    )
}
