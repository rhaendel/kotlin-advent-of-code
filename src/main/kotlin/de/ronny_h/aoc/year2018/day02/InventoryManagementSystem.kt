package de.ronny_h.aoc.year2018.day02

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.combinations
import kotlin.math.min

fun main() = InventoryManagementSystem().run("5434", "agimdjvlhedpsyoqfzuknpjwt")

class InventoryManagementSystem : AdventOfCode<String>(2018, 2) {
    override fun part1(input: List<String>): String {
        var twoLetterCount = 0
        var threeLetterCount = 0
        input.forEach { id ->
            val groups = id.groupBy { it }
            twoLetterCount += if (groups.any { it.value.size == 2 }) 1 else 0
            threeLetterCount += if (groups.any { it.value.size == 3 }) 1 else 0
        }
        return "${twoLetterCount * threeLetterCount}"
    }

    override fun part2(input: List<String>): String {
        val minPair = input
            .combinations()
            .minBy { lev(it.first, it.second) }

        return minPair.first.filterIndexed { i, char ->
            minPair.second[i] == char
        }
    }
}

private val levCache = mutableMapOf<Pair<String, String>, Int>()

/**
 * The Levenshtein distance (an edit distance).
 */
fun lev(a: String, b: String): Int {
    levCache[a to b]?.let { return it }

    if (b.isEmpty()) return a.length
    if (a.isEmpty()) return b.length

    val aTail = a.substring(1)
    val bTail = b.substring(1)

    val tailResult = lev(aTail, bTail)
    levCache[aTail to bTail] = tailResult

    if (a.first() == b.first()) {
        return tailResult
    }

    val aTailResult = lev(aTail, b)
    val bTailResult = lev(a, bTail)
    levCache[aTail to b] = aTailResult
    levCache[a to bTail] = bTailResult

    return 1 + min(min(aTailResult, bTailResult), tailResult)
}
