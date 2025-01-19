package de.ronny_h.extensions

import kotlin.math.abs

data class Coordinates(val row: Int, val col: Int) {
    operator fun plus(other: Coordinates) = Coordinates(row + other.row, col + other.col)
    operator fun minus(other: Coordinates) = Coordinates(row - other.row, col - other.col)

    operator fun times(other: Int) = Coordinates(row * other, col * other)

    operator fun plus(direction: Direction) = Coordinates(row + direction.row, col + direction.col)

    /**
     * Calculates the taxi distance, a.k.a. Manhattan distance, between this and the other Coordinates instance.
     */
    infix fun taxiDistanceTo(other: Coordinates): Int = abs(other.col - col) + abs(other.row - row)
}

operator fun Int.times(other: Coordinates) = Coordinates(this * other.row, this * other.col)

enum class Direction(val row: Int, val col: Int) {
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

    fun asChar() = when (this) {
        NORTH -> '↑'
        EAST -> '→'
        SOUTH -> '↓'
        WEST -> '←'
    }

    fun isHorizontal(): Boolean = this == EAST || this == WEST
    fun isVertical(): Boolean = this == NORTH || this == SOUTH
}
