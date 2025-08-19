package de.ronny_h.aoc.extensions.grids


open class SimpleCharGrid(input: List<String>, nullElement: Char = '#') : Grid<Char>(input, nullElement) {
    override fun Char.toElementType() = this

    fun clusterRegions(char: Char? = null): List<List<Coordinates>> {
        val assigned = mutableSetOf<Coordinates>()
        return forEachCoordinates { position, element ->
            if ((char == null || element == char) && position !in assigned) {
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
        char: Char,
        visited: MutableSet<Coordinates> = mutableSetOf(position),
        regionsCoordinates: MutableList<Coordinates> = mutableListOf(position),
    ): List<Coordinates> {
        position.neighbours().forEach { coordinates ->
            if (getAt(coordinates) == char && visited.add(coordinates)) {
                regionsCoordinates.add(coordinates)
                collectRegionAt(coordinates, char, visited, regionsCoordinates)
            }
        }
        return regionsCoordinates
    }
}
