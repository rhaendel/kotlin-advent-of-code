package de.ronny_h.aoc.year24.day18

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid
import de.ronny_h.aoc.extensions.ShortestPath
import de.ronny_h.aoc.extensions.aStar
import printAndCheck
import readInput

fun main() {
    val day = "Day18"
    val input = readInput(day)
    val ramRun = RAMRun()

    println("$day part 1")
    printAndCheck(input.subList(0, 1024), ramRun::part1Big, 416)

    println("$day part 2")
    printAndCheck(input, ramRun::part2Big, "50,23")
}

class RAMRun {
    fun List<String>.toCoordinates(): List<Coordinates> = map {
        val (x, y) = it.split(',')
        Coordinates(x.toInt(), y.toInt())
    }

    fun part1(input: List<String>, width: Int): Int {
        val memorySpace = MemorySpace(width, input.toCoordinates())
        memorySpace.printGrid()
        println("-----------------")
        val shortestPath = memorySpace.shortestPath(Coordinates(0,0), Coordinates(width-1, width-1))
        memorySpace.printGrid(path = shortestPath.path.associateWith { 'O' })
        return shortestPath.distance
    }

    fun part1Small(input: List<String>) = part1(input, 7) // 0..6
    fun part1Big(input: List<String>) = part1(input, 71)  // 0..70

    fun part2(input: List<String>, width: Int, startIndex: Int): String {
        for (n in startIndex..input.lastIndex) {
            val memorySpace = MemorySpace(width, input.subList(0, n).toCoordinates())
            try {
                memorySpace.shortestPath(Coordinates(0, 0), Coordinates(width - 1, width - 1))
            } catch (e: IllegalStateException) {
                return input[n-1]
            }
        }
        return ""
    }

    fun part2Small(input: List<String>) = part2(input, 7, 12) // 0..6
    fun part2Big(input: List<String>) = part2(input, 71, 1024) // 0..70
}

private class MemorySpace(width: Int, corrupted: List<Coordinates>) : Grid<Char>(width, width, '.', '#', corrupted) {
    private val corrupted = '#'
    override fun Char.toElementType() = this

    fun shortestPath(start: Coordinates, goal: Coordinates): ShortestPath<Coordinates> {
        val neighbours: (Coordinates) -> List<Coordinates> = { node ->
            Direction
                .entries
                .map { node + it }
                .filter { getAt(node) != corrupted }
        }

        val d: (Coordinates, Coordinates) -> Int = { a, b ->
            // pre-condition: a and b are neighbours
            1
        }

        val h: (Coordinates) -> Int = { it taxiDistanceTo goal}

        return aStar(start, goal, neighbours, d, h)
    }
}
