package de.ronny_h.aoc.extensions.graphs

import kotlin.math.ceil


data class TravelingSalesmanProblemSolution(val length: Int, val path: List<Int>)

// should be larger than any other single weight in the graph
const val MAX_WEIGHT = 10_000_000

/**
 * A TSP implementation inspired from https://www.geeksforgeeks.org/dsa/traveling-salesman-problem-using-branch-and-bound-2/
 *
 * @param adj An adjacency matrix containing all edge weights.
 */
class TravelingSalesman(private val adj: List<List<Int>>) {
    private val N: Int = adj.size

    // visited keeps track of the already visited nodes in a particular path
    private var visited = listOf<Int>()

    // finalPath stores the final solution, i.e. the path of the salesman.
    private var finalPath = listOf<Int>()

    // Stores the final minimum weight of shortest tour.
    private var finalLength: Int = Int.MAX_VALUE

    /**
     * Find the minimum edge cost having an end at the vertex [node]
     */
    private fun firstMin(node: Int) = adj[node].filterIndexed { i, _ -> i != node }.min()

    /**
     * Find the second minimum edge cost having an end at the vertex [node]
     */
    private fun secondMin(node: Int): Int {
        var first = MAX_WEIGHT
        var second = MAX_WEIGHT
        for (otherNode in 0..<N) {
            if (node == otherNode) continue

            if (adj[node][otherNode] <= first) {
                second = first
                first = adj[node][otherNode]
            } else if (adj[node][otherNode] <= second && adj[node][otherNode] != first) {
                second = adj[node][otherNode]
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
    private fun tspRecursive(boundSoFar: Int, weightSoFar: Int, level: Int, currentPath: List<Int>) {
        var currentBound = boundSoFar
        var currentWeight = weightSoFar

        // base case is when we have reached level N which
        // means we have covered all the nodes once
        if (level == N) {
            if (adj[currentPath.last()][currentPath.first()] != MAX_WEIGHT) {
                val currentLength = currentWeight + adj[currentPath.last()][currentPath.first()]

                if (currentLength < finalLength) {
                    finalPath = currentPath
                    finalLength = currentLength
                }
            }
            return
        }

        // for any other level iterate for all vertices to build the search space tree recursively
        for (node in 0..<N) {
            // Consider next vertex if it is not same (diagonal
            // entry in adjacency matrix) and not visited already
            if (node != currentPath.last() && !visited.contains(node)) {
                val tempBound = currentBound
                currentWeight += adj[currentPath.last()][node]

                currentBound -= if (level == 1) {
                    ceil((firstMin(currentPath.last()) + firstMin(node)) / 2.0).toInt()
                } else {
                    ceil((secondMin(currentPath.last()) + firstMin(node)) / 2.0).toInt()
                }

                // currentBound + currentWeight is the actual lower bound for the node that we have arrived on
                // If current lower bound < finalLength, we need to explore the node further
                if (currentBound + currentWeight < finalLength) {
                    visited += node
                    tspRecursive(currentBound, currentWeight, level + 1, currentPath + node)
                }

                // Else we have to prune the node by resetting
                // all changes to currentWeight and currentBound
                currentWeight -= adj[currentPath.last()][node]
                currentBound = tempBound
                visited = currentPath
            }
        }
    }

    fun calculateShortestRoundTrip(): TravelingSalesmanProblemSolution {
        // Calculate initial lower bound for the root node using the formula
        // 1/2 * (sum of first min + second min) for all edges, rounded off to an integer
        val currentBound = ((0..<N).sumOf { firstMin(it) + secondMin(it) } / 2.0).toInt()

        // We start at vertex 1 so the first vertex in currentPath is 0
        visited = listOf(0)

        tspRecursive(currentBound, 0, 1, listOf(0))
        return TravelingSalesmanProblemSolution(finalLength, finalPath + finalPath[0])
    }
}

data class Edge<N>(val from: N, val to: N, val weight: Int)

data class AdjacencyMatrix<N>(val headers: List<N>, val content: List<List<Int>>) {
    init {
        require(headers.size == content.size) { "adjacency matrix's headers (${headers.size}) do not match its content (${content.size})" }
        require(content.all { it.size == headers.size }) { "adjacency matrix is not quadratical" }
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
    val adj = List(nodes.size) { i ->
        MutableList(nodes.size) { j -> if (i == j) 0 else MAX_WEIGHT }
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
