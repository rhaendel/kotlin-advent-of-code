package de.ronny_h.aoc.extensions.graphs.shortestpath

import java.util.*
import kotlin.Int.Companion.MAX_VALUE

// NOTE: This A* implementation is a 1:1 equivalent in Kotlin to the pseudo code on the Wikipedia page
//       https://en.wikipedia.org/wiki/A*_search_algorithm

/**
 * The maximum value for edge weights. In pseudocode of path-searching algorithms
 * this is typically denoted as infinity (= a value larger than all others).
 */
const val LARGE_VALUE = MAX_VALUE / 2

/**
 * A* finds a path from `start` to `goal`.
 * @param start the start node
 * @param isGoal predicate deciding if a node is a goal
 * @param neighbors is a function that returns the list of neighbors for a given node.
 * @param d is the distance/cost function. `d(m,n)` provides the distance (or cost) to reach node `n` from node `m`.
 * @param h is the heuristic function. `h(n)` estimates the cost to reach goal from node `n`. `h` must be admissible,
 *        i.e. it never overestimates the cost of reaching the goal.
 */
open class AStar<N>(
    private val start: N,
    private val isGoal: N.() -> Boolean,
    private val neighbors: (N) -> List<N>,
    private val d: (N, N) -> Int,
    private val h: (N) -> Int,
    private val printIt: (visited: Set<N>, current: N, additionalInfo: () -> String) -> Unit = { _, _, _ -> }
) {

    fun shortestPath() = aStarGeneral(
        backtrackingMap = object : MutableBacktrackingMap<N, N> {
            private val map = mutableMapOf<N, N>()

            override val keys: Set<N>
                get() = map.keys

            override fun addOrReplace(key: N, replace: Boolean, value: N) {
                if (replace) {
                    map[key] = value
                }
            }

            override fun getValue(key: N) = map.getValue(key)

            override fun reconstructPaths(last: N): List<List<N>> {
                var current = last
                val totalPath = mutableListOf(current)
                while (current in keys) {
                    current = getValue(current)
                    totalPath.add(0, current)
                }
                return listOf(totalPath)
            }
        },
        isLowerThan = { this < it },
        searchFirstOnly = true,
    ).first()

    fun allPaths() = aStarGeneral(
        backtrackingMap = object : MutableBacktrackingMap<N, MutableSet<N>> {
            private val map = mutableMapOf<N, MutableSet<N>>()

            override val keys: Set<N>
                get() = map.keys

            override fun addOrReplace(key: N, replace: Boolean, value: N) {
                if (replace) {
                    map[key] = mutableSetOf(value)
                } else {
                    map.getOrPut(key) { mutableSetOf() } += value
                }
            }

            override fun getValue(key: N) = map.getValue(key)

            override fun reconstructPaths(last: N): List<List<N>> {
                if (!map.contains(last)) {
                    return listOf(listOf(last))
                }
                return getValue(last)
                    .flatMap { pred -> reconstructPaths(pred) }
                    .map { path -> path + last }
            }
        },
        isLowerThan = { this <= it },
        searchFirstOnly = false,
    )

    interface MutableBacktrackingMap<K, V> {
        val keys: Set<K>

        fun addOrReplace(key: K, replace: Boolean, value: K)

        fun getValue(key: K): V

        fun reconstructPaths(last: K): List<List<K>>
    }

    private fun <V> aStarGeneral(
        backtrackingMap: MutableBacktrackingMap<N, V>,
        isLowerThan: Int.(Int) -> Boolean,
        searchFirstOnly: Boolean,
    ): List<ShortestPath<N>> {
        // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
        // how cheap a path could be from start to finish if it goes through n.
        val fScore = mutableMapOf<N, Int>().withDefault { _ -> LARGE_VALUE } // map with default value of Infinity

        // The set of discovered nodes that may need to be (re-)expanded.
        // Initially, only the start node is known.
        // This is usually implemented as a min-heap or priority queue rather than a hash-set.
        val openSet = PriorityQueue<N> { a, b -> fScore.getValue(a).compareTo(fScore.getValue(b)) }
        openSet.add(start)

        // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from the start
        // to n currently known.
        val cameFrom = backtrackingMap

        // For node n, gScore[n] is the currently known cost of the cheapest path from start to n.
        val gScore = mutableMapOf<N, Int>().withDefault { _ -> LARGE_VALUE } // map with default value of Infinity
        gScore[start] = 0
        fScore[start] = h(start)

        while (openSet.isNotEmpty()) {
            // This operation can occur in O(Log(N)) time if openSet is a min-heap or a priority queue
            val current = openSet.remove()
            if (isGoal(current)) {
                // Search for nodes in openSet with fScore[node] <= gScore[current]
                // If the heuristic function is admissible (it never overestimates the actual cost to get to the goal)
                // we can be sure to expand all possible paths.
                if (searchFirstOnly || openSet.all { n -> fScore.getValue(n) > gScore.getValue(current) }) {
                    return cameFrom.reconstructPaths(current).map { x -> ShortestPath(x, gScore.getValue(current)) }
                }
            }

            for (neighbor in neighbors(current)) {
                // d(current,neighbor) is the weight of the edge from current to neighbor
                // tentative_gScore is the distance from start to the neighbor through current
                val tentativeGScore = gScore.getValue(current) + d(current, neighbor)
                if (tentativeGScore.isLowerThan(gScore.getValue(neighbor))) {
                    // This path to neighbor is better than any previous one. Record it!
                    cameFrom.addOrReplace(neighbor, tentativeGScore < gScore.getValue(neighbor), current)
                    gScore[neighbor] = tentativeGScore
                    fScore[neighbor] = tentativeGScore + h(neighbor)
                    if (neighbor !in openSet) {
                        openSet.add(neighbor)
                    }
                }
                printIt(cameFrom.keys, neighbor) {
                    "current: $current=${fScore[current]}, neighbor: $neighbor=${fScore[neighbor]}, open: " + openSet.joinToString { "$it=${fScore[it]}" }
                }
            }
        }

        // Open set is empty but goal was never reached
        return emptyList()
    }
}
