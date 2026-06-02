package de.ronny_h.aoc.year2018.day20

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Direction

fun main() = ARegularMap().run(3046, 8545)

class ARegularMap : AdventOfCode<Int>(2018, 20) {
    override fun part1(input: List<String>): Int {
        return BaseConstructionProject(regexFrom(input)).shortestPathToFurthestRoom()
    }

    override fun part2(input: List<String>): Int {
        val project = BaseConstructionProject(regexFrom(input))
        project.shortestPathToFurthestRoom()
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

    private data class State(val position: Coordinates, val distance: Int, val index: Int)

    private fun shortestPathToFurthestRoom(
        entryPosition: Coordinates,
        distanceSoFar: Int,
        index: Int,
    ): State {
        var s = State(entryPosition, 0, index)

        while (s.index < regex.length) {
            s = when (val char = regex[s.index]) {
                '(' -> {
                    val (branchPosition, branchLength, lastReadIndex) = shortestPathToFurthestRoom(
                        s.position,
                        s.distance + distanceSoFar,
                        s.index + 1
                    )
                    State(branchPosition, s.distance + branchLength, lastReadIndex + 1)
                }

                ')' -> return s
                '|' -> {
                    val (branchPosition, branchLength, lastReadIndex) = shortestPathToFurthestRoom(
                        entryPosition,
                        distanceSoFar,
                        s.index + 1
                    )
                    return if (branchLength == 0) {
                        // empty option: |)
                        State(entryPosition, 0, lastReadIndex)
                    } else if (branchLength > s.distance) {
                        State(branchPosition, branchLength, lastReadIndex)
                    } else {
                        State(s.position, s.distance, lastReadIndex)
                    }
                }

                else -> State(s.position + Direction.of(char), s.distance + 1, s.index + 1)
            }
            shortestDistances.computeIfAbsent(s.position) { s.distance + distanceSoFar }
        }

        return s
    }

    fun shortestPathToFurthestRoom() = shortestPathToFurthestRoom(Coordinates(0, 0), 0, 0).distance

    fun countPathsHavingAtLeastDoors(min: Int) = shortestDistances.count { it.value >= min }
}
