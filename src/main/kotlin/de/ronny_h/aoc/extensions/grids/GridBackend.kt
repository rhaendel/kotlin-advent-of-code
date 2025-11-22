package de.ronny_h.aoc.extensions.grids

import kotlin.math.max
import kotlin.math.min

interface GridBackend<T> {
    operator fun get(x: Int, y: Int): T
    operator fun get(at: Coordinates): T

    operator fun set(x: Int, y: Int, value: T)
    operator fun set(at: Coordinates, value: T)

    /**
     * @return an element at the given [x] and [y] coordinates or null if the index is out of bounds of this GridBackend.
     */
    fun getOrNull(x: Int, y: Int): T?
    fun getOrNull(at: Coordinates): T?

    /**
     * @return a view of the portion of this Grid between the specified [x], [y] (inclusive) and [x] + [width], [y] + [height] (exclusive).
     * The returned list of lists is backed by this Grid, so non-structural changes in the returned list are reflected
     * in this Grid, and vice-versa.
     * Structural changes in the base Grid make the behavior of the view undefined.
     */
    fun subGridAt(x: Int, y: Int, width: Int, height: Int = width): List<List<T>>

    fun <R> mapToSequence(transform: (x: Int, y: Int) -> R): Sequence<R>
}

class ListGridBackend<T>(width: Int, height: Int, nullElement: T) : GridBackend<T> {
    private val grid: MutableList<MutableList<T>> = MutableList(height) { MutableList(width) { nullElement } }

    override fun get(x: Int, y: Int) = grid[y][x]
    override fun get(at: Coordinates): T = grid[at.y][at.x]

    override fun set(x: Int, y: Int, value: T) {
        grid[y][x] = value
    }

    override fun set(at: Coordinates, value: T) {
        grid[at.y][at.x] = value
    }

    override fun getOrNull(x: Int, y: Int): T? = grid
        .getOrNull(y)
        ?.getOrNull(x)

    override fun getOrNull(at: Coordinates): T? = getOrNull(at.x, at.y)

    override fun subGridAt(x: Int, y: Int, width: Int, height: Int): List<List<T>> = buildList {
        for (row in y..<y + height) {
            add(grid[row].subList(x, x + width))
        }
    }

    override fun <R> mapToSequence(transform: (x: Int, y: Int) -> R): Sequence<R> = sequence {
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                yield(transform(x, y))
            }
        }
    }
}

class MapGridBackend<T>(defaultValue: T) : GridBackend<T> {
    private var minX = Int.MAX_VALUE
    private var maxX = Int.MIN_VALUE
    private var minY = Int.MAX_VALUE
    private var maxY = Int.MIN_VALUE

    private val grid = mutableMapOf<Coordinates, T>().withDefault { defaultValue }

    override fun get(x: Int, y: Int): T = get(Coordinates(x, y))

    override fun get(at: Coordinates): T {
        if (at.x !in minX..maxX || at.y !in minY..maxY) {
            throw IndexOutOfBoundsException("Coordinates $at are not inside the bound of this Grid (min and max coordinates for which values were set)")
        }
        return grid.getValue(at)
    }

    override fun set(x: Int, y: Int, value: T) = set(Coordinates(x, y), value)

    override fun set(at: Coordinates, value: T) {
        minX = min(minX, at.x)
        minY = min(minY, at.y)
        maxX = max(maxX, at.x)
        maxY = max(maxY, at.y)
        grid[at] = value
    }

    override fun getOrNull(x: Int, y: Int): T? = grid[Coordinates(x, y)]

    override fun getOrNull(at: Coordinates): T? = grid[at]

    override fun subGridAt(x: Int, y: Int, width: Int, height: Int): List<List<T>> = buildList {
        for (row in y..<y + height) {
            add(buildList {
                for (col in x..<x + width) {
                    add(grid.getValue(Coordinates(col, row)))
                }
            }
            )
        }
    }

    override fun <R> mapToSequence(transform: (x: Int, y: Int) -> R): Sequence<R> = sequence {
        for (row in minY..maxY) {
            for (col in minX..maxX) {
                yield(transform(col, row))
            }
        }
    }
}
