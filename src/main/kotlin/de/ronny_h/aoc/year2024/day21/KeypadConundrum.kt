package de.ronny_h.aoc.year2024.day21

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Direction.*
import de.ronny_h.aoc.extensions.SimpleCharGrid
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.memoize

fun main() = KeypadConundrum().run(94426, 118392478819140)

class KeypadConundrum : AdventOfCode<Long>(2024, 21) {
    fun input(code: String, keypad: Keypad, depth: Int): Long {
        data class Parameters(val code: String, val depth: Int)

        val keypads = List(depth) { Keypad(directionalKeypadLayout) } + keypad
        lateinit var inputRec: (Parameters) -> Long

        inputRec = { p: Parameters ->
            if (p.depth == 0) {
                p.code.length.toLong()
            } else {
                p.code.map { char ->
                    keypads[p.depth].moveTo(char).minOf {
                        inputRec(Parameters(it, p.depth - 1))
                    }
                }.sum()
            }
        }.memoize()

        return inputRec(Parameters(code, depth))
    }

    override fun part1(input: List<String>): Long = sumOfComplexities(input, 3)
    override fun part2(input: List<String>): Long = sumOfComplexities(input, 26)

    private fun sumOfComplexities(input: List<String>, depth: Int): Long = input.sumOf { code ->
        input(code, Keypad(numericKeypadLayout), depth) * code.dropLast(1).toLong()
    }
}

/*
+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+
*/
val numericKeypadLayout = """
    789
    456
    123
     0A
""".asList()

/*
    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
*/
val directionalKeypadLayout = """
     ^A
    <v>
""".asList()

class Keypad(layout: List<String>) : SimpleCharGrid(layout, ' ') {
    override fun toString(): String = toString(setOf(currentPosition), 'X')

    private var currentPosition = find('A')

    fun moveTo(target: Char): List<String> {
        val targetPosition = find(target)
        val paths = shortestPaths(currentPosition, targetPosition)
        currentPosition = targetPosition
        val directions = paths.map { shortestPath ->
            shortestPath.path
                .windowed(2)
                .map { (from, to) -> Direction.entries.first { from + it == to } }
                .joinToString("") {
                    when (it) {
                        NORTH -> "^"
                        EAST -> ">"
                        SOUTH -> "v"
                        WEST -> "<"
                    }
                }
        }
        return directions.map { "${it}A" }
    }
}
