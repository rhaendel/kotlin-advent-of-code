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
        .fold(listOf<LongRange>()) { acc, toAdd ->
            val range = acc.lastOrNull() ?: return@fold listOf(toAdd)

            when {
                // toAdd is contained completely in the last range
                toAdd.last in range -> acc
                // disjunct -> just add it
                toAdd.first !in range -> acc + listOf(toAdd)
                // toAdd overlaps partly with the last range
                else -> acc.dropLast(1) + listOf(range.first..toAdd.last)
            }
        }

fun Long.isFresh(freshRanges: List<LongRange>): Boolean = freshRanges.any { this in it }

fun List<String>.parseIngredients(): Ingredients {
    val (fresh, available) = split()
    return Ingredients(
        fresh.map {
            val (from, to) = it.split("-").map(String::toLong)
            from..to
        },
        available.map(String::toLong)
    )
}
