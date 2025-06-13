package de.ronny_h.aoc.year24.day21

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Direction.*
import de.ronny_h.aoc.extensions.SimpleCharGrid
import de.ronny_h.aoc.extensions.asList

fun main() = KeypadConundrum().run(94426, 0)

class KeypadConundrum : AdventOfCode<Int>(2024, 21) {

    data class Node(val code: String, val children: List<Node>) {
        fun collectSequences(level: Int = 0): String {
            if (children.isEmpty()) {
                check(level % 2 == 0)
                return code
            }
            return if (level % 2 == 0) {
                // direct children = sequence
                children.joinToString("") { it.collectSequences(level + 1) }
            } else {
                // direct children = different options (branches in the tree)
                children
                    .map { it.collectSequences(level + 1) }
                    .minBy { it.length }
            }
        }
    }

    fun input(code: String, keypad: Keypad, depth: Int): Node {
        if (depth == 0) {
            return Node(code, emptyList())
        }
        return Node(code, code.map { char ->
            Node("$char", keypad.moveTo(char).map {
                input(it, Keypad(directionalKeypadLayout), depth - 1)
            })
        })
    }

    override fun part1(input: List<String>): Int = input.sumOf {
        input(it, Keypad(numericKeypadLayout), 3).collectSequences().length * it.dropLast(1).toInt()
    }

    override fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
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
