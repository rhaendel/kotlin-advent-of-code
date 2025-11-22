package de.ronny_h.aoc.extensions.grids

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
