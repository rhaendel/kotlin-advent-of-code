import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction
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


    println("$day part 2")

    fun part2(input: List<String>) = Farm(input)
        .separateRegions()
        .sumOf { it.area * it.numberOfSides }

    printAndCheck(
        """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent().lines(),
        ::part2, 80
    )

    printAndCheck(
        """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent().lines(),
        ::part2, 436
    )

    printAndCheck(
        """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """.trimIndent().lines(),
        ::part2, 236
    )

    printAndCheck(
        """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent().lines(),
        ::part2, 368
    )

    printAndCheck(testInput, ::part2, 1206)
    printAndCheck(input, ::part2, 978590)
}

private data class Region(val area: Int, val perimeter: Int, val numberOfSides: Int)

private class Farm(input: List<String>) : Grid<Char>(input) {
    private val offMap = ' '
    override val nullElement = offMap
    override fun Char.toElementType() = this

    val assigned = mutableSetOf<Coordinates>()

    fun separateRegions(): List<Region> = forEachCoordinates { position, plant ->
        if (position !in assigned) {
            val region = collectRegionPlotsAt(position, plant)
            val perimeter = perimeterOf(region, plant)
            val numberOfSides = numberOfSidesOf(region, plant)
            assigned.addAll(region)
            if (verbose) {
                val regionAsSet = region.toSet()
                check(region.size == regionAsSet.size)
                printGrid(regionAsSet)
                println("---------- area: ${region.size} perimeter: $perimeter")
            }
            Region(region.size, perimeter, numberOfSides)
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

    private fun numberOfSidesOf(region: List<Coordinates>, plant: Char): Int {
        val boundary = region.filter { it.neighbours().any { n -> getAt(n) != plant } }
        val visited = mutableSetOf<Pair<Coordinates, Direction>>()
        val sides = boundary.sumOf { start ->
            val walk = walkAroundTheBoundary(start, plant) { position, direction ->
                val goOn = visited.add(position to direction)
                if (verbose) {
                    println("-- $plant -------------------- continue: $goOn")
                    printGrid(
                        visited.map { it.first }.toSet(),
                        highlightPosition = position,
                        highlightDirection = direction
                    )
                }
                goOn
            }
            if (verbose) println(" walk around $plant: $walk")
            walk
        }
        if (verbose) println("sides of plant $plant: $sides")
        return sides
    }

    fun walkAroundTheBoundary(
        start: Coordinates,
        plant: Char,
        visit: (Coordinates, Direction) -> Boolean,
    ): Int {
        var direction = findStartDirectionFor(start, plant)
        var position = start
        var sideCount = 0

        while (visit(position, direction)) {
            val stepAhead = position + direction
            // check if next tile is part of the same side
            if (getAt(stepAhead) == plant) {
                if (leftOf(stepAhead, direction) == plant) {
                    sideCount++
                    position = stepAhead
                    direction = direction.turnLeft()
                } // else the tile step ahead is part of the same side -> continue in that direction
                position += direction
            } else {
                sideCount++
                when (plant) {
                    leftOf(position, direction) -> {
                        direction =direction.turnLeft()
                        position += direction
                    }
                    else -> direction = direction.turnRight()
                }
            }
        }
        return sideCount
    }

    private fun leftOf(position: Coordinates, direction: Direction) = getAt(position + direction.turnLeft())

    private fun findStartDirectionFor(start: Coordinates, plant: Char): Direction {
        val startDirection = NORTH
        var direction = startDirection
        var inLastDirection = getAt(start + direction)
        var inDirection = inLastDirection
        do {
            direction = direction.turnRight()
            inLastDirection = inDirection
            inDirection = getAt(start + direction)
        } while (!(inLastDirection != plant && inDirection == plant) && direction != startDirection) // last condition prohibits endless loops on 1-tile gardens
        return direction
    }

}

private fun Coordinates.neighbours() = listOf(this + EAST, this + SOUTH, this + WEST, this + NORTH)
