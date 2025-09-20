package de.ronny_h.aoc.year2018.day05

import de.ronny_h.aoc.AdventOfCode

fun main() = AlchemicalReduction().run(9900, 4992)

class AlchemicalReduction : AdventOfCode<Int>(2018, 5) {
    override fun part1(input: List<String>): Int = input.first().react().length

    override fun part2(input: List<String>): Int {
        val polymer = input.first()
        return "abcdefghijklmnopqrstuvwxyz".minOf {
            polymer.react(it).length
        }
    }
}

fun String.react(toRemove: Char = ' '): String {
    val toRemoveUpperCase = toRemove.uppercaseChar()
    val toSkip = List(length) { i ->
        get(i) == toRemove || get(i) == toRemoveUpperCase
    }.toMutableList()
    var somethingNewIsSkipped: Boolean

    do {
        somethingNewIsSkipped = false
        var index = 0
        while (index < lastIndex) {
            if (toSkip[index]) {
                index++
                continue
            }
            val nextIndex = nextNonSkippedIndex(index, toSkip)
            if (nextIndex > lastIndex) {
                break
            }
            if (get(index) == get(nextIndex).oppositeCase()) {
                toSkip[index] = true
                toSkip[nextIndex] = true
                index = nextIndex + 1
                somethingNewIsSkipped = true
            } else {
                index++
            }
        }
    } while (somethingNewIsSkipped)
    return filterIndexed { i, _ -> !toSkip[i] }
}

private fun String.nextNonSkippedIndex(index: Int, toSkip: List<Boolean>): Int {
    var nextIndex = index + 1
    while (nextIndex < length && toSkip[nextIndex]) {
        nextIndex++
    }
    return nextIndex
}

private fun Char.oppositeCase() = if (isLowerCase()) uppercaseChar() else lowercaseChar()
