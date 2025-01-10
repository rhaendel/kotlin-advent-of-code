package de.ronny_h.extensions

abstract class Grid(input: List<String>) {
    val height = input.size
    val width = input[0].length
    private val grid = Array(height) { CharArray(width) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char }
        }
    }

    abstract val nullElement: Char

    fun charAt(row: Int, col: Int): Char {
        return grid.getOrNull(row)?.getOrNull(col) ?: nullElement
    }

    fun <T> forEachIndex(action: (Int, Int) -> T): Sequence<T> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col))
            }
        }
    }

    fun <T> forEachElement(action: (Int, Int, Char) -> T): Sequence<T> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col, charAt(row, col)))
            }
        }
    }
}
