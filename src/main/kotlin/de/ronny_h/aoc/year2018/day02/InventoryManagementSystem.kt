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
            .minBy { lev(it.first, it.second, 1) }

        return minPair.first.filterIndexed { i, char ->
            minPair.second[i] == char
        }
    }
}

/**
 * @return The Levenshtein distance (an edit distance) between the Strings [a] and [b].
 * @param maxDistance Specifies an upper bound for the calculated distance. The function aborts if this value is exceeded
 * returning the distance calculated so far (which might be much higher than [maxDistance]).
 * @param distanceSoFar For recursive calls, only. The distance calculated to the current level.
 */
fun lev(a: String, b: String, maxDistance: Int = Int.MAX_VALUE, distanceSoFar: Int = 0): Int {
    if (b.isEmpty()) return a.length
    if (a.isEmpty()) return b.length
    if (distanceSoFar > maxDistance) return 0

    val aTail = a.substring(1)
    val bTail = b.substring(1)

    if (a.first() == b.first()) {
        return lev(aTail, bTail, maxDistance, distanceSoFar)
    }

    val aTailResult = lev(aTail, b, maxDistance, distanceSoFar + 1)
    val bTailResult = lev(a, bTail, maxDistance, distanceSoFar + 1)
    val tailResult = lev(aTail, bTail, maxDistance, distanceSoFar + 1)

    return 1 + min(min(aTailResult, bTailResult), tailResult)
}
