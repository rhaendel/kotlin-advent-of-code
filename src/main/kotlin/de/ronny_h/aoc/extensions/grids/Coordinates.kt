package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.grids.Direction.*
import kotlin.math.abs

data class Coordinates(val y: Int, val x: Int) : Comparable<Coordinates> {

    companion object {
        val ZERO = Coordinates(0, 0)
    }

    operator fun plus(other: Coordinates) = Coordinates(y + other.y, x + other.x)
    operator fun minus(other: Coordinates) = Coordinates(y - other.y, x - other.x)

    operator fun times(other: Int) = Coordinates(y * other, x * other)

    operator fun plus(direction: Direction) = Coordinates(y + direction.y, x + direction.x)

    fun neighbours() = listOf(this + EAST, this + SOUTH, this + WEST, this + NORTH)

    fun neighboursIncludingDiagonals() = neighbours() +
            listOf(this + EAST + NORTH, this + SOUTH + EAST, this + WEST + SOUTH, this + NORTH + WEST)

    fun directedNeighbours() = listOf(
        EAST to this + EAST,
        SOUTH to this + SOUTH,
        WEST to this + WEST,
        NORTH to this + NORTH,
    )

    /**
     * Calculates the taxi distance, a.k.a. Manhattan distance, between this and the other Coordinates instance.
     */
    infix fun taxiDistanceTo(other: Coordinates): Int = abs(other.x - x) + abs(other.y - y)

    override fun toString() = "($y,$x)"

    /**
     * Orders [Coordinates] in reading order: top-to-bottom, then left-to-right.
     */
    override fun compareTo(other: Coordinates): Int {
        if (this.y == other.y) {
            return this.x - other.x
        }
        return this.y - other.y
    }
}

operator fun Int.times(other: Coordinates) = Coordinates(this * other.y, this * other.x)

enum class Direction(val y: Int, val x: Int) {
    NORTH(-1, 0),
    EAST(0, +1),
    SOUTH(+1, 0),
    WEST(0, -1),
    ;

    fun turnRight() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun turnLeft() = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun reverse() = when (this) {
        NORTH -> SOUTH
        EAST -> WEST
        SOUTH -> NORTH
        WEST -> EAST
    }

    fun asChar() = when (this) {
        NORTH -> '↑'
        EAST -> '→'
        SOUTH -> '↓'
        WEST -> '←'
    }

    fun isHorizontal(): Boolean = this == EAST || this == WEST
    fun isVertical(): Boolean = this == NORTH || this == SOUTH

    fun isOpposite(other: Direction) = when (this) {
        NORTH -> other == SOUTH
        EAST -> other == WEST
        SOUTH -> other == NORTH
        WEST -> other == EAST
    }

    /**
     * Returns the minimal number of 90° rotations necessary to rotate this
     * to other.
     */
    operator fun minus(other: Direction): Int {
        return when {
            this == other -> 0
            this.isOpposite(other) -> 2
            else -> 1
        }
    }

    override fun toString() = when (this) {
        NORTH -> "N"
        EAST -> "E"
        SOUTH -> "S"
        WEST -> "W"
    }

    fun turn(turn: Turn): Direction = when (turn) {
        Turn.LEFT -> turnLeft()
        Turn.RIGHT -> turnRight()
        Turn.STRAIGHT -> this
    }
}

enum class Turn {
    LEFT, RIGHT, STRAIGHT
}
