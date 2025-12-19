package de.ronny_h.aoc.extensions.graphs.shortestpath

import java.util.*

// NOTE: This A* implementation is a 1:1 equivalent in Kotlin to the pseudo code on the Wikipedia page
//       https://en.wikipedia.org/wiki/A*_search_algorithm

private fun <N> reconstructPath(cameFrom: Map<N, N>, last: N): List<N> {
    var current = last
    val totalPath = mutableListOf(current)
    while (current in cameFrom.keys) {
        current = cameFrom.getValue(current)
        totalPath.add(0, current)
    }
    return totalPath
}

/**
 * The maximum value for edge weights. In pseudocode of path-searching algorithms
 * this is typically denoted as infinity (= a value larger than all others).
 */
const val LARGE_VALUE = Int.MAX_VALUE / 2
const val LARGE_DOUBLE_VALUE = Double.MAX_VALUE / 2

/**
 * A* finds a path from `start` to `goal`.
 * @param start the start node
 * @param isGoal predicate deciding if a node is a goal
 * @param neighbors is a function that returns the list of neighbours for a given node.
 * @param d is the distance/cost function. `d(m,n)` provides the distance (or cost) to reach node `n` from node `m`.
 * @param h is the heuristic function. `h(n)` estimates the cost to reach goal from node `n`. `h` must be admissible,
 *        i.e. it never overestimates the cost of reaching the goal.
 */
fun <N> aStar(
    start: N, isGoal: N.() -> Boolean, neighbors: (N) -> List<N>, d: (N, N) -> Int, h: (N) -> Int,
    printIt: (visited: Set<N>, current: N, additionalInfo: () -> String) -> Unit = { _, _, _ -> }
): ShortestPath<N> = aStarDouble(
    start,
    isGoal,
    neighbors,
    { from, to -> d(from, to).toDouble() },
    { h(it).toDouble() },
    printIt
).toShortestPath()

fun <N> aStarDouble(
    start: N, isGoal: N.() -> Boolean, neighbors: (N) -> List<N>, d: (N, N) -> Double, h: (N) -> Double,
    printIt: (visited: Set<N>, current: N, additionalInfo: () -> String) -> Unit = { _, _, _ -> }
): ShortestPathDouble<N> {
    // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
    // how cheap a path could be from start to finish if it goes through n.
    val fScore = mutableMapOf<N, Double>().withDefault { _ -> LARGE_DOUBLE_VALUE } // map with default value of Infinity

    // The set of discovered nodes that may need to be (re-)expanded.
    // Initially, only the start node is known.
    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
    val openSet = PriorityQueue<N> { a, b -> fScore.getValue(a).compareTo(fScore.getValue(b)) }
    openSet.add(start)

    // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from the start
    // to n currently known.
    val cameFrom = mutableMapOf<N, N>()

    // For node n, gScore[n] is the currently known cost of the cheapest path from start to n.
    val gScore = mutableMapOf<N, Double>().withDefault { _ -> LARGE_DOUBLE_VALUE } // map with default value of Infinity
    gScore[start] = 0.0

    fScore[start] = h(start)

    while (openSet.isNotEmpty()) {
        // This operation can occur in O(Log(N)) time if openSet is a min-heap or a priority queue
        val current = openSet.peek()
        if (isGoal(current)) {
            return ShortestPathDouble(reconstructPath(cameFrom, current), gScore.getValue(current))
        }

        openSet.remove(current)
        for (neighbor in neighbors(current)) {
            // d(current,neighbor) is the weight of the edge from current to neighbor
            // tentative_gScore is the distance from start to the neighbor through current
            val tentativeGScore = gScore.getValue(current) + d(current, neighbor)
            if (tentativeGScore < gScore.getValue(neighbor)) {
                // This path to neighbor is better than any previous one. Record it!
                cameFrom[neighbor] = current
                gScore[neighbor] = tentativeGScore
                fScore[neighbor] = tentativeGScore + h(neighbor)
                if (neighbor !in openSet) {
                    openSet.add(neighbor)
                }
            }
            printIt(cameFrom.keys, neighbor) {
                "current: $current=${fScore[current]}, neighbor: $neighbor=${fScore[neighbor]}, open: " +
                        openSet.take(5).joinToString { "$it=${fScore[it]}" }
            }
        }
    }

    // Open set is empty but goal was never reached
    error("No path found from $start to goal")
}
