package de.ronny_h.aoc.year2018.day20

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction

fun main() = ARegularMap().run(3046, 8545)

class ARegularMap : AdventOfCode<Int>(2018, 20) {
    override fun part1(input: List<String>): Int {
        return BaseConstructionProject(regexFrom(input)).longestPathToFurthestDoor()
    }

    override fun part2(input: List<String>): Int {
        val project = BaseConstructionProject(regexFrom(input))
        project.longestPathToFurthestDoor()
        return project.countPathsHavingAtLeastDoors(1000)
    }

    private fun regexFrom(input: List<String>): String {
        val regex = input.single()
        require(regex.startsWith("^"))
        require(regex.endsWith("$"))
        return regex.substring(1, regex.length - 1)
    }
}

class BaseConstructionProject(private val regex: String) {
    private val shortestDistances = mutableMapOf<Coordinates, Int>()

    private data class RecursiveResult(val position: Coordinates, val branchLength: Int, val index: Int)

    private fun longestPathToFurthestDoor(
        entryPosition: Coordinates,
        index: Int,
        distanceSoFar: Int
    ): RecursiveResult {
        var distance = 0
        var position = entryPosition
        var i = index

        while (i < regex.length) {
            val char = regex[i]
            position = when (char) {
                '(' -> {
                    val (branchPosition, branchLength, lastReadIndex) = longestPathToFurthestDoor(
                        position,
                        i + 1,
                        distanceSoFar + distance
                    )
                    distance += branchLength
                    i = lastReadIndex + 1
                    branchPosition
                }

                ')' -> return RecursiveResult(position, distance, i)
                '|' -> {
                    val (branchPosition, branchLength, lastReadIndex) = longestPathToFurthestDoor(
                        entryPosition,
                        i + 1,
                        distanceSoFar
                    )
                    return if (branchLength == 0) {
                        // empty option: |)
                        RecursiveResult(entryPosition, 0, lastReadIndex)
                    } else if (branchLength > distance) {
                        RecursiveResult(branchPosition, branchLength, lastReadIndex)
                    } else {
                        RecursiveResult(position, distance, lastReadIndex)
                    }
                }

                else -> {
                    distance++
                    i++
                    position + Direction.of(char)
                }
            }
            shortestDistances.computeIfAbsent(position) { distance + distanceSoFar }
        }

        return RecursiveResult(position, distance, regex.length)
    }

    fun longestPathToFurthestDoor() = longestPathToFurthestDoor(Coordinates(0, 0), 0, 0).branchLength

    fun countPathsHavingAtLeastDoors(min: Int) = shortestDistances.count { it.value >= min }
}
