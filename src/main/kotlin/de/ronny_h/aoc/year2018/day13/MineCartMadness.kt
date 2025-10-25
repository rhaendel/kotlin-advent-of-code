package de.ronny_h.aoc.year2018.day13

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.firstDuplicate
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction
import de.ronny_h.aoc.extensions.grids.Direction.*
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.extensions.grids.Turn
import de.ronny_h.aoc.extensions.grids.Turn.*

fun main() = MineCartMadness().run("33,69", "")

class MineCartMadness : AdventOfCode<String>(2018, 13) {
    override fun part1(input: List<String>): String {
        val collision = Track(input).moveCartsUntilFirstCrash()
        return "${collision.col},${collision.row}"
    }

    override fun part2(input: List<String>): String {
        return ""
    }
}

class Track(input: List<String>) : SimpleCharGrid(input, ' ') {
    private val carts: List<Cart> = buildList {
        forEachCoordinates { position, element ->
            when (element) {
                '<' -> {
                    add(Cart(position, WEST))
                    setAt(position, '-')
                }

                '>' -> {
                    add(Cart(position, EAST))
                    setAt(position, '-')
                }

                '^' -> {
                    add(Cart(position, NORTH))
                    setAt(position, '|')
                }

                'v' -> {
                    add(Cart(position, SOUTH))
                    setAt(position, '|')
                }
            }
        }.last()
    }

    fun moveCartsUntilFirstCrash(): Coordinates {
        var collision: Coordinates? = null
        while (collision == null) {
            collision = tick()
        }
        return collision
    }

    private fun tick(): Coordinates? {
        for (cart in carts.sortedBy { it.position }) {
            cart.move()
            carts.firstDuplicate(Cart::position)?.also { return it }
        }
        return null
    }

    private fun Cart.move() {
        position += direction
        direction = when (getAt(position)) {
            '+' -> direction.turn(nextTurn())
            '/' -> if (direction == NORTH || direction == SOUTH) direction.turnRight() else direction.turnLeft()
            '\\' -> if (direction == NORTH || direction == SOUTH) direction.turnLeft() else direction.turnRight()
            else -> direction
        }
    }
}

data class Cart(var position: Coordinates, var direction: Direction, private var lastTurn: Turn = RIGHT) {
    fun nextTurn(): Turn {
        lastTurn = when (lastTurn) {
            LEFT -> STRAIGHT
            STRAIGHT -> RIGHT
            RIGHT -> LEFT
        }
        return lastTurn
    }
}
