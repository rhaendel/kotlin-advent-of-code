package de.ronny_h.aoc.year2015.day12

import de.ronny_h.aoc.AdventOfCode

fun main() = JSAbacusFramework().run(111754, 65402)

class JSAbacusFramework : AdventOfCode<Int>(2015, 12) {

    private val numberRegex = "[-]{0,1}[0-9]+".toRegex()

    override fun part1(input: List<String>) = input.first().sumAllNumbers()

    override fun part2(input: List<String>) = input.first().filterOutRedObjects().first.sumAllNumbers()

    private fun String.sumAllNumbers(): Int = numberRegex.findAll(this).sumOf { match -> match.value.toInt() }
}

private const val redValue = ":\"red\""

/**
 * From a JSON String, filters out sub-objects that have a value equal to "red".
 *
 * @return A pair of the filtered JSON String and the length of the JSON object that was processed.
 */
fun String.filterOutRedObjects(): Pair<String, Int> {
    var isRed = false
    var inRedValue = 0
    var prefix = ""
    var i = 0
    var lastSubObjectAt = 0
    while (i < length) {
        when (this[i]) {
            '{' -> {
                val (infix, len) = substring(i + 1).filterOutRedObjects()
                prefix = prefix + substring(lastSubObjectAt..i) + infix
                i += len
                lastSubObjectAt = i
            }

            '}' -> if (isRed) {
                return "" to i + 1
            } else {
                return prefix + substring(lastSubObjectAt, i) to i + 1
            }

            redValue[inRedValue] -> inRedValue++

            else -> inRedValue = 0
        }
        if (inRedValue == redValue.length) {
            isRed = true
            inRedValue = 0
        }
        i++
    }
    return prefix + this.substring(lastSubObjectAt) to length
}
