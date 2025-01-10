package de.ronny_h.extensions

abstract class Grid(input: List<String>) {
    val height = input.size
    val width = input[0].length

    abstract val nullElement: Char

    private val grid = Array(height) { CharArray(width) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char }
        }
    }

    fun charAt(row: Int, col: Int): Char {
        return grid.getOrNull(row)?.getOrNull(col) ?: nullElement
    }

    fun charAt(position: Coordinates) = charAt(position.row, position.col)

    fun <T> forEachIndex(action: (row: Int, col: Int) -> T): Sequence<T> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col))
            }
        }
    }

    fun <T> forEachElement(action: (row: Int, col: Int, element: Char) -> T): Sequence<T> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col, charAt(row, col)))
            }
        }
    }
}
