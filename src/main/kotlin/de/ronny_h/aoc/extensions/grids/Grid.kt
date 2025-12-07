package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.animation.AnimationRecorder
import de.ronny_h.aoc.extensions.graphs.shortestpath.Graph
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStarAllPaths
import de.ronny_h.aoc.extensions.graphs.shortestpath.dijkstraShortestPaths
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
    height: Int, width: Int,
    val nullElement: T,
    private val fallbackElement: T = nullElement,
    protected val grid: GridBackend<T> = ListGridBackend(width, height, nullElement),
) {
    val height: Int get() = grid.height
    val width: Int get() = grid.width

    var recorder: AnimationRecorder? = null

    /**
     * A function that maps each `Char` that may occur in the `input: List<String>` to a value of type [T].
     */
    abstract fun Char.toElementType(): T

    /**
     * Overwrite in sub-classes to perform actions before the [value] is set at [position].
     */
    open fun preSet(position: Coordinates, value: T) {}

    /**
     * Constructs a Grid with the specified dimensions filled with the [nullElement] as default value and all
     * coordinates in [overrides] set to [overrideElement].
     */
    constructor(
        height: Int,
        width: Int,
        nullElement: T,
        overrideElement: T,
        overrides: List<Coordinates> = emptyList()
    ) : this(height, width, nullElement, fallbackElement = overrideElement) {
        overrides.forEach { grid[it] = overrideElement }
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

    protected fun initGrid(input: List<String>) = input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            grid[x, y] = char.toElementType()
        }
    }

    /**
     * @return The value at the specified cell.
     */
    operator fun get(x: Int, y: Int): T = grid.getOrNull(x, y) ?: fallbackElement

    operator fun set(x: Int, y: Int, value: T) {
        preSet(Coordinates(x, y), value)
        grid[x, y] = value
        recorder?.record(toString())
    }

    operator fun get(position: Coordinates) = grid.getOrNull(position) ?: fallbackElement
    operator fun set(position: Coordinates, element: T) {
        preSet(position, element)
        grid[position] = element
        recorder?.record(toString())
    }

    operator fun set(x: Int, yRange: IntRange, value: T) {
        for (y in yRange) {
            this[Coordinates(x, y)] = value
        }
    }

    operator fun set(xRange: IntRange, y: Int, value: T) {
        for (x in xRange) {
            this[Coordinates(x, y)] = value
        }
    }

    /**
     * @return a view of the portion of this Grid between the specified [x], [y] (inclusive) and [x] + [width], [y] + [height] (exclusive).
     * The returned list of lists is backed by this Grid, so non-structural changes in the returned list are reflected
     * in this Grid, and vice-versa.
     * Structural changes in the base Grid make the behavior of the view undefined.
     */
    fun subGridAt(x: Int, y: Int, width: Int, height: Int = width): List<List<T>> = grid.subGridAt(x, y, width, height)

    fun <R> forEachIndex(action: (x: Int, y: Int) -> R): Sequence<R> = grid.mapToSequence(action)

    fun <R> forEachElement(action: (x: Int, y: Int, element: T) -> R): Sequence<R> = grid.mapToSequence { x, y ->
        action(x, y, get(x, y))
    }

    fun <R> forEachCoordinates(action: (position: Coordinates, element: T) -> R): Sequence<R> =
        grid.mapToSequence { x, y ->
            action(Coordinates(x, y), get(x, y))
        }

    /**
     * Returns the first element matching the given [value].
     *
     * @throws NoSuchElementException If no matching element can be found.
     */
    fun find(value: T): Coordinates = forEachElement { x, y, element ->
        if (element == value) Coordinates(x, y) else null
    }.filterNotNull().first()

    override fun toString(): String = toString(setOf())

    /**
     * @return A String representation of this [Grid] with all coordinates in [overrides] overridden
     * by the Char specified as [overrideChar].
     */
    fun toString(
        overrides: Set<Coordinates> = setOf(),
        overrideChar: Char = '#',
        padding: Int = 0,
    ): String {
        val out = ByteArrayOutputStream()
        PrintStream(out, true, StandardCharsets.UTF_8).use {
            printGrid(it, overrides = overrides, overrideChar = overrideChar, padding = padding)
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
        padding: Int = 0,
    ) {
        val paddingString = "$fallbackElement".repeat(padding)
        forEachCoordinates { position, element ->
            if (position.x == grid.minX) writer.print(paddingString)
            if (highlightPosition == position && highlightDirection != null) {
                writer.print(highlightDirection.asChar())
            } else if (path.contains(position)) {
                writer.print(path[position])
            } else if (overrides.contains(position)) {
                writer.print(overrideChar)
            } else {
                writer.print(element)
            }
            if (position.x == grid.maxX) writer.print("$paddingString\n")
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
        isVisitable: (Coordinates) -> Boolean = { get(it) != nullElement }
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
        stopAfterMinimalPathsAreFound: Boolean = false,
        isObstacle: (T) -> Boolean = { it == nullElement },
    ): List<ShortestPath<Coordinates>> {
        val graph = Graph(
            vertices = forEachCoordinates { position, element ->
                if (element == nullElement) null else position
            }.filterNotNull().toList(),
            edges = { from, to ->
                if (to in from.neighbours().filter { !isObstacle(get(it)) }) 1 else null
            }
        )
        return dijkstraShortestPaths(graph, start, goals, stopAfterMinimalPathsAreFound)
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
            if (get(coordinates) == value && visited.add(coordinates)) {
                regionsCoordinates.add(coordinates)
                collectRegionAt(coordinates, value, visited, regionsCoordinates)
            }
        }
        return regionsCoordinates
    }
}
