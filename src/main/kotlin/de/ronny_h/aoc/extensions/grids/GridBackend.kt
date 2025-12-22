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

    val width: Int
    val height: Int
    val minX: Int
    val minY: Int
    val maxX: Int
    val maxY: Int
    val entries: Set<Pair<Coordinates, T>>
}

class ListGridBackend<T>(override val width: Int, override val height: Int, nullElement: T) : GridBackend<T> {
    override val minX: Int get() = 0
    override val minY: Int get() = 0
    override val maxX: Int get() = width - 1
    override val maxY: Int get() = height - 1

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ListGridBackend<*>) return false

        if (width != other.width) return false
        if (height != other.height) return false
        if (grid != other.grid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + grid.hashCode()
        return result
    }

    override val entries: Set<Pair<Coordinates, T>>
        get() = mapToSequence { x, y ->
            Coordinates(x, y) to get(x, y)
        }.toSet()
}

class MapGridBackend<T>(defaultValue: T) : GridBackend<T> {
    override val height: Int get() = maxY + 1 - minY
    override val width: Int get() = maxX + 1 - minX
    override var minX = Int.MAX_VALUE
        private set
    override var maxX = Int.MIN_VALUE
        private set
    override var minY = Int.MAX_VALUE
        private set
    override var maxY = Int.MIN_VALUE
        private set

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MapGridBackend<*>) return false

        if (minX != other.minX) return false
        if (maxX != other.maxX) return false
        if (minY != other.minY) return false
        if (maxY != other.maxY) return false
        if (grid != other.grid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minX
        result = 31 * result + maxX
        result = 31 * result + minY
        result = 31 * result + maxY
        result = 31 * result + grid.hashCode()
        return result
    }

    override val entries: Set<Pair<Coordinates, T>> get() = grid.entries.map { it.key to it.value }.toSet()
}
