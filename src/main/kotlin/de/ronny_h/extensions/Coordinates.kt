package de.ronny_h.extensions

data class Coordinates(val row: Int, val col: Int) {
    operator fun plus(other: Coordinates) = Coordinates(row + other.row, col + other.col)
    operator fun minus(other: Coordinates) = Coordinates(row - other.row, col - other.col)

    operator fun times(other: Int) = Coordinates(row * other, col * other)
}

operator fun Int.times(other: Coordinates) = Coordinates(this * other.row, this * other.col)
