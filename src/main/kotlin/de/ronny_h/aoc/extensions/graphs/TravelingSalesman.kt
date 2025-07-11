package de.ronny_h.aoc.extensions.graphs

import java.util.*
import javax.annotation.processing.Generated
import kotlin.math.ceil


data class TravelingSalesmanProblemSolution(val length: Int, val path: List<Int>)

// should be larger than any other single weight in the graph
const val MAX_WEIGHT = 10_000_000
private const val UNSET = -1

/**
 * A TSP implementation inspired from https://www.geeksforgeeks.org/dsa/traveling-salesman-problem-using-branch-and-bound-2/
 *
 * @param adj An adjacency matrix containing all edge weights.
 */
class TravelingSalesman(private val adj: Array<IntArray>) {
    private val N: Int = adj.size

    // finalPath[] stores the final solution ie, the path of the salesman.
    private val finalPath: IntArray = IntArray(N + 1)

    // visited[] keeps track of the already visited nodes in a particular path
    private val visited: BooleanArray = BooleanArray(N)

    // Stores the final minimum weight of shortest tour.
    private var finalLength: Int = Int.MAX_VALUE

    // Function to copy temporary solution to the final solution
    private fun copyToFinal(currentPath: IntArray) {
        for (i in 0..<N) {
            finalPath[i] = currentPath[i]
        }
        finalPath[N] = currentPath[0]
    }

    // Function to find the minimum edge cost having an end at the vertex i
    private fun firstMin(i: Int): Int {
        var min = MAX_WEIGHT
        for (k in 0..<N) {
            if (adj[i][k] < min && i != k) min = adj[i][k]
        }
        return min
    }

    // function to find the second minimum edge cost having an end at the vertex i
    private fun secondMin(i: Int): Int {
        var first = MAX_WEIGHT
        var second = MAX_WEIGHT
        for (j in 0..<N) {
            if (i == j) continue

            if (adj[i][j] <= first) {
                second = first
                first = adj[i][j]
            } else if (adj[i][j] <= second && adj[i][j] != first) {
                second = adj[i][j]
            }
        }
        return second
    }

    /**
     * Calculates the shortest tour recursively.
     *
     * @param boundSoFar lower bound of the root node
     * @param weightSoFar stores the weight of the path so far
     * @param level current level while moving in the search space tree
     * @param currentPath where the solution is being stored which would later be copied to [finalPath]
     */
    private fun tspRecursive(boundSoFar: Int, weightSoFar: Int, level: Int, currentPath: IntArray) {
        var currentBound = boundSoFar
        var currentWeight = weightSoFar

        // base case is when we have reached level N which
        // means we have covered all the nodes once
        if (level == N) {
            // check if there is an edge from last vertex in path back to the first vertex
            if (adj[currentPath[level - 1]][currentPath[0]] != MAX_WEIGHT) {
                // currentLength has the total weight of the solution we got
                val currentLength = currentWeight + adj[currentPath[level - 1]][currentPath[0]]

                // Update final result and final path if current result is better.
                if (currentLength < finalLength) {
                    copyToFinal(currentPath)
                    finalLength = currentLength
                }
            }
            return
        }

        // for any other level iterate for all vertices to build the search space tree recursively
        for (i in 0..<N) {
            // Consider next vertex if it is not same (diagonal
            // entry in adjacency matrix) and not visited already
            if (i != currentPath[level - 1] && !visited[i]) {
                val temp = currentBound
                currentWeight += adj[currentPath[level - 1]][i]

                // different computation of currentBound for level 2 from the other levels
                currentBound -= if (level == 1) {
                    ceil((firstMin(currentPath[0]) + firstMin(i)) / 2.0).toInt()
                } else {
                    ceil((secondMin(currentPath[level - 1]) + firstMin(i)) / 2.0).toInt()
                }

                // currentBound + currentWeight is the actual lower bound for the node that we have arrived on
                // If current lower bound < finalLength, we need to explore the node further
                if (currentBound + currentWeight < finalLength) {
                    currentPath[level] = i
                    visited[i] = true

                    // call for the next level
                    tspRecursive(currentBound, currentWeight, level + 1, currentPath)
                }

                // Else we have to prune the node by resetting
                // all changes to currentWeight and currentBound
                currentWeight -= adj[currentPath[level - 1]][i]
                currentBound = temp

                // Also reset the visited array
                Arrays.fill(visited, false)
                for (j in 0..<level) {
                    if (currentPath[j] != UNSET) {
                        visited[currentPath[j]] = true
                    }
                }
            }
        }
    }

    fun calculateShortestRoundTrip(): TravelingSalesmanProblemSolution {
        tsp()
        return TravelingSalesmanProblemSolution(finalLength, finalPath.toList())
    }

    private fun tsp() {
        // Calculate initial lower bound for the root node
        // using the formula 1/2 * (sum of first min + second min) for all edges.
        // Also initialize the currentPath and visited array
        var currentBound = 0
        val currentPath = IntArray(N + 1) { UNSET }
        Arrays.fill(visited, false)

        // Compute initial bound
        for (i in 0..<N) {
            currentBound += (firstMin(i) + secondMin(i))
        }

        // Rounding off the lower bound to an integer
        currentBound = ceil(currentBound / 2.0).toInt()

        // We start at vertex 1 so the first vertex in currentPath[] is 0
        visited[0] = true
        currentPath[0] = 0

        tspRecursive(currentBound, 0, 1, currentPath)
    }
}

data class Edge<N>(val from: N, val to: N, val weight: Int)
data class AdjacencyMatrix<N>(val headers: List<N>, val content: Array<IntArray>) {
    init {
        require(headers.size == content.size) { "adjacency matrix's headers (${headers.size}) do not match its content (${content.size})" }
        require(content.all { it.size == headers.size }) { "adjacency matrix is not quadratical" }
    }

    @Generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdjacencyMatrix<*>

        if (headers != other.headers) return false
        if (!content.contentDeepEquals(other.content)) return false

        return true
    }

    @Generated
    override fun hashCode(): Int {
        var result = headers.hashCode()
        result = 31 * result + content.contentDeepHashCode()
        return result
    }
}

/**
 * Converts a list of [Edge]s to an adjacency matrix.
 *
 * @param symmetrical If `true`, treats all connections in the list of edges symmetrically. That means, an edge from _a_ to _b_ with weight _w_
 * implies an edge from _b_ to _a_ with weight _w_. Else (the default), only edges that are explicitly in the list are added to the matrix.
 * @param nullNode If specified (i.e. not `null`), adds a synthetic start node to the matrix with edges of weight `0` to all other nodes.
 * This way, instead of a complete round-trip, the TSP algorithm calculates a single path containing all nodes - starting with an arbitrary one -
 * so that the path length is minimized.
 */
fun <N> List<Edge<N>>.toAdjacencyMatrix(
    symmetrical: Boolean = false,
    nullNode: N? = null
): Pair<List<Edge<N>>, AdjacencyMatrix<N>> {
    val originalNodes = flatMap { listOf(it.from, it.to) }.toSet().toList()

    // to solve the start node problem, add a "null node" with distance 0 to all others
    val nullNodes = nullNode?.let { listOf(it) } ?: emptyList()
    val nullEdges = nullNode?.let { n -> originalNodes.map { o -> Edge(n, o, 0) } } ?: emptyList()

    val nodes = nullNodes + originalNodes
    val indices = nodes.mapIndexed { i, node -> node to i }.toMap()
    val adj = Array(nodes.size) { i ->
        IntArray(nodes.size) { j -> if (i == j) 0 else MAX_WEIGHT }
    }

    val allEdges = nullEdges + this
    allEdges.forEach {
        val fromIndex = indices.getValue(it.from)
        val toIndex = indices.getValue(it.to)
        check(adj[fromIndex][toIndex] == MAX_WEIGHT) { "there is already an edge from node $fromIndex (${it.from}) to $toIndex (${it.to})" }
        check(it.weight < MAX_WEIGHT) { "this algorithm only works for weights smaller than $MAX_WEIGHT, current: ${it.weight}" }
        adj[fromIndex][toIndex] = it.weight
        if (symmetrical) {
            check(adj[toIndex][fromIndex] == MAX_WEIGHT) { "there is already an edge from node $toIndex (${it.to}) to $fromIndex (${it.from})" }
            adj[toIndex][fromIndex] = it.weight
        }
    }
    return allEdges to AdjacencyMatrix(nodes, adj)
}
