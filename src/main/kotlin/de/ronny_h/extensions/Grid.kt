package de.ronny_h.extensions

abstract class Grid(input: List<String>) {
    private val grid = Array(input.size) { CharArray(input[0].length) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char }
        }
    }

    abstract val nullElement: Char

    fun charAt(row: Int, col: Int): Char {
        return grid.getOrNull(row)?.getOrNull(col) ?: nullElement
    }

    fun <T> forEach(block: (Int, Int) -> T): Sequence<T> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(block(row, col))
            }
        }
    }
}
