package de.ronny_h.aoc.extensions.graphs.shortestpath

/**
 * A [Graph] consists of a set of [vertices] and a function [edges] that returns the weight of the edge
 * between two vertices, `null` if there is no edge between them.
 */
data class Graph<V>(val vertices: Set<V>, val edges: (V, V) -> Int?)

/**
 * The [DijkstraResult] consists of [distances], storing the minimal distance from the source to each vertex,
 * and [predecessors] containing all backwards edges on the minimal path to each vertex.
 */
data class DijkstraResult<V>(val distances: Map<V, Int>, val predecessors: Map<V, V>)

/**
 * @return A list containing one shortest path from [source] to each target in [targets] in the [graph].
 */
fun <V> dijkstra(graph: Graph<V>, source: V, targets: List<V>): List<ShortestPath<V>> {
    val dijkstraResult = dijkstra(graph, source)
    return targets.mapNotNull { reconstructPath(dijkstraResult, source, it) }
}

// NOTE: This implementation of Dijkstra's Algorithm is a 1:1 equivalent in Kotlin to the pseudo code on the Wikipedia page
//       https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

/**
 * Dijkstra's algorithm finds the shortest path from node [source] to all vertices in the [graph].
 */
fun <V> dijkstra(graph: Graph<V>, source: V): DijkstraResult<V> {
    val dist = mutableMapOf(source to 0).withDefault { LARGE_VALUE }
    val prev = mutableMapOf<V, V>()
    val q = graph.vertices.toMutableSet()

    while (q.isNotEmpty()) {
        // TODO if minimum is not unique, choose by a configurable criteria (with 1st as default)
        val u = q.minBy { dist.getValue(it) }
        q.remove(u)

        for (v in q) {
            val edgeWeight = graph.edges(u, v) ?: continue
            val alt = dist.getValue(u) + edgeWeight
            // TODO use <= and take the prev with highest precedence
            if (alt < dist.getValue(v)) {
                dist[v] = alt
                prev[v] = u
            }
        }
    }

    return DijkstraResult(dist.toMap(), prev.toMap())
}

private fun <V> reconstructPath(dijkstraResult: DijkstraResult<V>, source: V, target: V): ShortestPath<V>? {
    if (dijkstraResult.predecessors[target] == null && target != source) {
        return null
    }

    val s = mutableListOf<V>()
    var u: V? = target
    while (u != null) {
        s.add(u)
        u = dijkstraResult.predecessors[u]
    }
    return ShortestPath(s.reversed(), dijkstraResult.distances.getValue(target))
}
