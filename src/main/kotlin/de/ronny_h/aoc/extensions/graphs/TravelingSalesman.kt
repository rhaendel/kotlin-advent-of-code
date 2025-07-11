package de.ronny_h.aoc.extensions.graphs

import java.util.*


data class TravelingSalesmanProblemSolution(val length: Int, val path: List<Int>)

// TSP implementation inspired from https://www.geeksforgeeks.org/dsa/traveling-salesman-problem-using-branch-and-bound-2/
class TravelingSalesman(private val adj: Array<IntArray>) {
    private val N: Int = adj.size

    // finalPath[] stores the final solution ie, the path of the salesman.
    private val finalPath: IntArray = IntArray(N + 1)

    // visited[] keeps track of the already visited nodes in a particular path
    private val visited: BooleanArray = BooleanArray(N)

    // Stores the final minimum weight of shortest tour.
    private var finalLength: Int = Int.Companion.MAX_VALUE

    // Function to copy temporary solution to the final solution
    private fun copyToFinal(currentPath: IntArray) {
        for (i in 0..<N) {
            finalPath[i] = currentPath[i]
        }
        finalPath[N] = currentPath[0]
    }

    // Function to find the minimum edge cost having an end at the vertex i
    private fun firstMin(i: Int): Int {
        var min = Int.MAX_VALUE
        for (k in 0..<N) {
            if (adj[i][k] < min && i != k) min = adj[i][k]
        }
        return min
    }

    // function to find the second minimum edge cost having an end at the vertex i
    private fun secondMin(i: Int): Int {
        var first = Int.MAX_VALUE
        var second = Int.MAX_VALUE
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

    /** function that takes as arguments:
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
            if (adj[currentPath[level - 1]][currentPath[0]] != 0) {
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
            // entry in adjacency matrix and not visited already)
            if (adj[currentPath[level - 1]][i] != 0 && !visited[i]) {
                val temp = currentBound
                currentWeight += adj[currentPath[level - 1]][i]

                // different computation of curr_bound for level 2 from the other levels
                currentBound -= if (level == 1) {
                    (firstMin(currentPath[0]) + firstMin(i)) / 2
                } else {
                    (secondMin(currentPath[level - 1]) + firstMin(i)) / 2
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
                // all changes to curr_weight and curr_bound
                currentWeight -= adj[currentPath[level - 1]][i]
                currentBound = temp

                // Also reset the visited array
                Arrays.fill(visited, false)
                for (j in 0..level - 1) {
                    visited[currentPath[j]] = true
                }
            }
        }
    }

    // This function sets up final_path[]
    fun tsp(): TravelingSalesmanProblemSolution {
        val currentPath = IntArray(N + 1)

        // Calculate initial lower bound for the root node
        // using the formula 1/2 * (sum of first min + second min) for all edges.
        // Also initialize the currentPath and visited array
        var currentBound = 0
        Arrays.fill(currentPath, -1)
        Arrays.fill(visited, false)

        // Compute initial bound
        for (i in 0..<N) {
            currentBound += (firstMin(i) + secondMin(i))
        }

        // Rounding off the lower bound to an integer
        currentBound = if (currentBound == 1) 1 else currentBound / 2

        // We start at vertex 1 so the first vertex in currentPath[] is 0
        visited[0] = true
        currentPath[0] = 0

        tspRecursive(currentBound, 0, 1, currentPath)

        return TravelingSalesmanProblemSolution(finalLength, finalPath.toList())
    }
}
