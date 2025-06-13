package de.ronny_h.aoc.extensions

open class SimpleCharGrid(input: List<String>, nullElement: Char = '#') : Grid<Char>(input, nullElement) {
    override fun Char.toElementType() = this

    fun shortestPaths(start: Coordinates, goal: Coordinates): List<ShortestPath<Coordinates>> {
        val neighbours: (Coordinates) -> List<Coordinates> = { position ->
            Direction
                .entries
                .map { position + it }
                .filter { getAt(it) != nullElement }
        }

        val d: (Coordinates, Coordinates) -> Int = { a, b ->
            check(a taxiDistanceTo b == 1) // pre-condition: a and b a neighbours
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal }

        return aStarAllPaths(start, { this == goal }, neighbours, d, h)
    }
}