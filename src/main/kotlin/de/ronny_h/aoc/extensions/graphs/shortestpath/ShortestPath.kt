package de.ronny_h.aoc.extensions.graphs.shortestpath

data class ShortestPath<N>(val path: List<N>, val distance: Int)

data class ShortestPathDouble<N>(val path: List<N>, val distance: Double) {
    fun toShortestPath(): ShortestPath<N> = ShortestPath(path, distance.toInt())
}
