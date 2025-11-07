package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.graphs.shortestpath.Graph
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStarAllPaths
import de.ronny_h.aoc.extensions.graphs.shortestpath.dijkstra
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

/**
 * A 2-dimensional, rectangular Grid where each cell is identified by its [Coordinates] and represented by a value of type [T].
 *
 * @param height The number of rows in the Grid.
 * @param width The number of columns in the Grid.
 * @param nullElement The initial element within the grid
 * @param fallbackElement Used for out of bound positions. Defaults to [nullElement].
 */
abstract class Grid<T>(
    val height: Int,
    val width: Int,
    val nullElement: T,
    private val fallbackElement: T = nullElement,
) {

    private val grid: MutableList<MutableList<T>> = MutableList(height) { MutableList(width) { nullElement } }

    /**
     * A function that maps each `Char` that may occur in the `input: List<String>` to a value of type [T].
     */
    abstract fun Char.toElementType(): T

    /**
     * Constructs a Grid with the specified dimensions filled with the [nullElement] as default value and all
     * coordinates in [overrides] set to [overrideElement].
     */
    constructor(
        height: Int,
        width: Int,
        nullElement: T,
        overrideElement: T = nullElement,
        overrides: List<Coordinates> = emptyList()
    ) : this(height, width, nullElement, overrideElement) {
        overrides.forEach {
            grid[it.row][it.col] = overrideElement
        }
    }

    /**
     * Constructs a Grid from the [input] list. Uses function [toElementType] for converting the [String]s' [Char]s
     * to values of type [T].
     *
     * @param input A list of `String`s of the same length that determines the Grid's width. Its size determines the Grid's height.
     */
    constructor(input: List<String>, nullElement: T, overrideElement: T = nullElement) : this(
        input.size,
        input.maxOf { it.length },
        nullElement,
        overrideElement,
        emptyList()
    ) {
        initGrid(input)
    }

    protected fun initGrid(input: List<String>) = input.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            grid[row][col] = char.toElementType()
        }
    }

    /**
     * @return The value at the specified cell.
     */
    operator fun get(row: Int, col: Int): T {
        return grid
            .getOrNull(row)
            ?.getOrNull(col)
            ?: fallbackElement
    }

    operator fun set(row: Int, col: Int, value: T) {
        grid[row][col] = value
    }

    fun getAt(position: Coordinates) = get(position.row, position.col)
    fun setAt(position: Coordinates, element: T) {
        this[position.row, position.col] = element
    }

    /**
     * @return a view of the portion of this Grid between the specified [row], [col] (inclusive) and [row] + [height], [col] + [width] (exclusive).
     * The returned list of lists is backed by this Grid, so non-structural changes in the returned list are reflected
     * in this Grid, and vice-versa.
     * Structural changes in the base Grid make the behavior of the view undefined.
     */
    fun subGridAt(row: Int, col: Int, height: Int, width: Int = height): List<List<T>> {
        return buildList {
            for (r in row..<row + height) {
                add(grid[r].subList(col, col + width))
            }
        }
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

    /**
     * Returns the first element matching the given [value].
     *
     * @throws NoSuchElementException If no matching element can be found.
     */
    fun find(value: T): Coordinates = forEachElement { row, col, element ->
        if (element == value) Coordinates(row, col) else null
    }.filterNotNull().first()

    override fun toString(): String = toString(setOf())

    /**
     * @return A String representation of this [Grid] with all coordinates in [overrides] overridden
     * by the Char specified as [overrideChar].
     */
    fun toString(
        overrides: Set<Coordinates> = setOf(),
        overrideChar: Char = '#',
    ): String {
        val out = ByteArrayOutputStream()
        PrintStream(out, true, StandardCharsets.UTF_8).use {
            printGrid(it, overrides = overrides, overrideChar = overrideChar)
        }
        return out.toString().trim()
    }

    /**
     * Prints this [Grid] to [System.out] with (the first matching rule applies):
     * - if not `null`, the [highlightPosition] overridden by the `Char` representation of [highlightDirection]
     * - each coordinate in [path] overridden by the `Char` that is mapped to it
     * - all coordinates in [overrides] overridden by the `Char` specified as [overrideChar]
     */
    fun printGrid(
        overrides: Set<Coordinates> = setOf(),
        overrideChar: Char = '#',
        highlightPosition: Coordinates? = null,
        highlightDirection: Direction? = null,
        path: Map<Coordinates, Char> = mapOf(),
    ) {
        printGrid(System.out, overrides, overrideChar, highlightPosition, highlightDirection, path)
    }

    private fun printGrid(
        writer: PrintStream,
        overrides: Set<Coordinates> = setOf(),
        overrideChar: Char = '#',
        highlightPosition: Coordinates? = null,
        highlightDirection: Direction? = null,
        path: Map<Coordinates, Char> = mapOf(),
    ) {
        forEachCoordinates { position, element ->
            if (highlightPosition == position && highlightDirection != null) {
                writer.print(highlightDirection.asChar())
            } else if (path.contains(position)) {
                writer.print(path[position])
            } else if (overrides.contains(position)) {
                writer.print(overrideChar)
            } else {
                writer.print(element)
            }
            if (position.col == width - 1) writer.print('\n')
        }.last()
    }

    /**
     * Determines all shortest paths possible from [start] to [goal] with the following conditions:
     * - The [isVisitable] defines the cells that are no obstacle - the obstacle defaults to the Grid's [nullElement].
     * - No path outside the Grid is possible.
     * - Only direct neighbours (no diagonal ones) are considered.
     * - The cost of moving to a neighbour equals 1.
     */
    fun shortestPaths(
        start: Coordinates,
        goal: Coordinates,
        isVisitable: (Coordinates) -> Boolean = { getAt(it) != nullElement }
    ): List<ShortestPath<Coordinates>> {
        val neighbours: (Coordinates) -> List<Coordinates> = { position ->
            position.neighbours().filter(isVisitable)
        }

        val d: (Coordinates, Coordinates) -> Int = { a, b ->
            require(a taxiDistanceTo b == 1) { "a and b have to be neighbours" }
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal }

        return aStarAllPaths(start, { this == goal }, neighbours, d, h)
    }

    fun shortestPaths(
        start: Coordinates,
        goals: List<Coordinates>,
        isObstacle: (T) -> Boolean = { it == nullElement },
    ): List<ShortestPath<Coordinates>> {
        val graph = Graph(
            vertices = forEachCoordinates { position, element ->
                if (element == nullElement) null else position
            }.filterNotNull().toList(),
            edges = { from, to ->
                if (to in from.neighbours().filter { !isObstacle(getAt(it)) }) 1 else null
            }
        )
        return dijkstra(graph, start, goals)
    }

    /**
     * Clusters all cells into regions of adjacent cells having the same value.
     *
     * @param value If specified, only cells with that value are taken into account. Else, all cells get clustered.
     *
     * @return A list containing one list of [Coordinates] per cluster.
     */
    fun clusterRegions(value: T? = null): List<List<Coordinates>> {
        val assigned = mutableSetOf<Coordinates>()
        return forEachCoordinates { position, element ->
            if ((value == null || element == value) && position !in assigned) {
                val region = collectRegionAt(position, element)
                assigned.addAll(region)
                region
            } else {
                null
            }
        }
            .filterNotNull()
            .toList()
    }

    private fun collectRegionAt(
        position: Coordinates,
        value: T,
        visited: MutableSet<Coordinates> = mutableSetOf(position),
        regionsCoordinates: MutableList<Coordinates> = mutableListOf(position),
    ): List<Coordinates> {
        position.neighbours().forEach { coordinates ->
            if (getAt(coordinates) == value && visited.add(coordinates)) {
                regionsCoordinates.add(coordinates)
                collectRegionAt(coordinates, value, visited, regionsCoordinates)
            }
        }
        return regionsCoordinates
    }
}
