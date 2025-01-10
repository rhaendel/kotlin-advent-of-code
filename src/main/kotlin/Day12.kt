import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction.*
import de.ronny_h.extensions.Grid

const val verbose = false

fun main() {
    val day = "Day12"

    println("$day part 1")

    fun part1(input: List<String>) = Farm(input)
        .separateRegions()
        .sumOf { it.area * it.perimeter }

    printAndCheck(
        """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent().lines(),
        ::part1, 140
    )

    printAndCheck(
        """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent().lines(),
        ::part1, 772
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 1930)

    val input = readInput(day)
    printAndCheck(input, ::part1, 1546338)
}

private data class Region(val area: Int, val perimeter: Int)

private class Farm(input: List<String>) : Grid<Char>(input) {
    override val nullElement = ' '
    override fun Char.toElementType() = this

    val assigned = mutableSetOf<Coordinates>()

    fun separateRegions(): List<Region> = forEachCoordinates { position, plant ->
        if (position !in assigned) {
            val region = collectRegionPlotsAt(position, plant)
            val perimeter = perimeterOf(region, plant)
            assigned.addAll(region)
            if (verbose) {
                val regionAsSet = region.toSet()
                check(region.size == regionAsSet.size)
                printGrid(regionAsSet)
                println("---------- area: ${region.size} perimeter: $perimeter")
            }
            Region(region.size, perimeter)
        } else {
            null
        }
    }
        .filterNotNull()
        .toList()

    private fun perimeterOf(region: List<Coordinates>, plant: Char) = region.sumOf {
        it.neighbours().filter { n -> getAt(n) != plant }.size
    }

    private fun collectRegionPlotsAt(
        position: Coordinates,
        plant: Char,
        regionsCoordinates: MutableList<Coordinates> = mutableListOf(position),
        visited: MutableSet<Coordinates> = mutableSetOf(position)
    ): List<Coordinates> {
        position.neighbours().forEach { coordinates ->
            if (getAt(coordinates) == plant && visited.add(coordinates)) {
                regionsCoordinates.add(coordinates)
                collectRegionPlotsAt(coordinates, plant, regionsCoordinates, visited)
            }
        }
        return regionsCoordinates
    }
}

private fun Coordinates.neighbours() = listOf(this + EAST, this + SOUTH, this + WEST, this + NORTH)
