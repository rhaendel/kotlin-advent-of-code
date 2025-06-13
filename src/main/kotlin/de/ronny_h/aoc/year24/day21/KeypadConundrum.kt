package de.ronny_h.aoc.year24.day21

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.ShortestPath
import de.ronny_h.aoc.extensions.aStar
import de.ronny_h.aoc.extensions.asList

fun main() = KeypadConundrum().run(0, 0)

class KeypadConundrum : AdventOfCode<Int>(2024, 21) {
    override fun part1(input: List<String>): Int {
        println("directionalKeypad: $directionalKeypadLayout")

        val numericKeypad = Keypad(numericKeypadLayout)
        val sequence = input.map { code ->
            val sequence1 = code.map { position ->
                numericKeypad.moveTo(position)
            }.joinToString("")
            println("robot1: $sequence1")

            val directionalKeypad1 = Keypad(directionalKeypadLayout)
            val sequence2 = sequence1.map { direction ->
                directionalKeypad1.moveTo(direction)
            }.joinToString("")
            println("robot2: $sequence2")

            val directionalKeypad2 = Keypad(directionalKeypadLayout)
            val sequence3 = sequence2.map { direction ->
                directionalKeypad2.moveTo(direction)
            }.joinToString("")
            println("$code: $sequence3")
        }

        return 0
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

class Keypad(layout: List<String>) : Grid<Char>(layout, ' ') {
    override fun Char.toElementType(): Char = this

    data class Node(val direction: Direction, val position: Coordinates) {
        override fun toString() = "$position$direction"
    }

    private var currentPosition = find('A')

    fun moveTo(target: Char): String {
        val targetPosition = find(target)
        val path = shortestPath(currentPosition, targetPosition)
        currentPosition = targetPosition
        val directions = path.path.drop(1).joinToString("") {
            when (it.direction) {
                Direction.NORTH -> "^"
                Direction.EAST -> ">"
                Direction.SOUTH -> "v"
                Direction.WEST -> "<"
            }
        }
        return "${directions}A"
    }

    fun shortestPath(start: Coordinates, goal: Coordinates): ShortestPath<Node> {

        val neighbours: (Node) -> List<Node> = { node ->
            Direction
                .entries
                .map { Node(it, node.position + it) }
                .filter { getAt(it.position) != nullElement }
        }

        val d: (Node, Node) -> Int = { a, b ->
            // pre-condition: a and b are neighbours
            1
        }

        val h: (Node) -> Int = { it.position taxiDistanceTo goal }

        return aStar(Node(Direction.EAST, start), { this.position == goal }, neighbours, d, h)
    }
}
