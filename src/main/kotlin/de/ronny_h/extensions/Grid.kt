package de.ronny_h.extensions

import kotlin.collections.MutableList

/**
 * @param nullElement The initial element within the grid
 * @param overrideElement Used for out of bound positions. Defaults to `nullElement`
 */
abstract class Grid<T>(
    val height: Int,
    val width: Int,
    protected val nullElement: T,
    private val overrideElement: T = nullElement,
) {

    private val grid: MutableList<MutableList<T>> = MutableList(height) { MutableList(width) { nullElement } }

    abstract fun Char.toElementType(): T

    constructor(height: Int, width: Int, nullElement: T, overrideElement: T = nullElement, overrides: List<Coordinates> = emptyList()) : this(height, width, nullElement, overrideElement) {
        overrides.forEach {
            grid[it.row][it.col] = overrideElement
        }
    }

    constructor(input: List<String>, nullElement: T, overrideElement: T = nullElement) : this(input.size, input[0].length, nullElement, overrideElement, emptyList()){
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char.toElementType() }
        }
    }

    operator fun get(row: Int, col: Int): T {
        return grid
            .getOrNull(row)
            ?.getOrNull(col)
            ?: overrideElement
    }

    fun getAt(position: Coordinates) = get(position.row, position.col)
    fun setAt(position: Coordinates, element: T) {
        grid[position.row][position.col] = element
    }

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
                yield(action(row, col, get(row, col)))
            }
        }
    }

    fun <R> forEachCoordinates(action: (position: Coordinates, element: T) -> R): Sequence<R> = sequence {
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                yield(action(Coordinates(row, col), get(row, col)))
            }
        }
    }

    fun printGrid(
        overrides: Set<Coordinates> = setOf(),
        overrideChar: Char = '#',
        highlightPosition: Coordinates? = null,
        highlightDirection: Direction? = null,
        path: Map<Coordinates, Char> = mapOf(),
    ) {
        forEachCoordinates { position, element ->
            if (highlightPosition == position && highlightDirection != null) {
                print(highlightDirection.asChar())
            } else if (path.contains(position)) {
                print(path[position])
            } else if (overrides.contains(position)) {
                print(overrideChar)
            } else {
                print(element)
            }
            if (position.col == width - 1) println()
        }.last()
    }
}
