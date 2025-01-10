package de.ronny_h.extensions

abstract class Grid<T>(input: List<String>) {
    val height = input.size
    val width = input[0].length

    abstract val nullElement: T
    abstract fun Char.toElementType(): T

    private val grid = MutableList(height) { MutableList(width) { nullElement } }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char.toElementType() }
        }
    }

    fun charAt(row: Int, col: Int): T {
        return grid.getOrNull(row)?.getOrNull(col) ?: nullElement
    }

    fun charAt(position: Coordinates) = charAt(position.row, position.col)

    fun <R> forEachIndex(action: (row: Int, col: Int) -> R): Sequence<R> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col))
            }
        }
    }

    fun <R> forEachElement(action: (row: Int, col: Int, element: T) -> R): Sequence<R> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(row, col, charAt(row, col)))
            }
        }
    }

    fun printGrid(overrides: Set<Coordinates> = setOf(), overrideChar: Char = '#') {
        forEachElement { row, col, char ->
            if (overrides.contains(Coordinates(row, col))) {
                print(overrideChar)
            } else {
                print(char)
            }
            if (col == width - 1) println()
        }.last()
    }
}
