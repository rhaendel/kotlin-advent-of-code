package de.ronny_h.aoc.year2018.day10

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates

// the text of part one: GFANEHKJ
fun main() = TheStarsAlign().run(10086, 10086)

class TheStarsAlign : AdventOfCode<Int>(2018, 10) {
    override fun part1(input: List<String>): Int {
        val starAlignmentGrid = StarAlignmentGrid.of(input)
        val iterations = starAlignmentGrid.moveUntilMessageIsClear()
        starAlignmentGrid.printStars()
        return iterations
    }

    override fun part2(input: List<String>): Int {
        return part1(input)
    }
}

data class Star(val position: Coordinates, val velocity: Coordinates)

class StarAlignmentGrid(initStars: List<Star>) {
    var stars: List<Star>
        private set

    init {
        stars = initStars
    }

    fun move(): StarAlignmentGrid {
        stars = stars.map { Star(it.position + it.velocity, it.velocity) }
        return this
    }

    fun moveUntilMessageIsClear(): Int {
        var count = 0
        do {
            move()
            count++
        } while (!aMessageCanBeSeen())
        return count
    }

    fun printStars() {
        val (min, max) = findBorders()
        for (row in min.row..max.row) {
            for (col in min.col..max.col) {
                if (Coordinates(row, col) in starPositions()) {
                    print('#')
                } else {
                    print(' ')
                }
            }
            println()
        }
    }

    private fun starPositions(): Set<Coordinates> = stars.map { it.position }.toSet()

    private fun aMessageCanBeSeen(): Boolean {
        val (min, max) = findBorders()
        if (max.row - min.row > 50 || max.col - min.col > 100) {
            // if the stars are too far away from each other, we don't need to check
            return false
        }
        // a message can be seen if there are at least 5 stars are in a row AND 7 stars in a column
        return hasAtLeastStarsInSequence(7, coordinatesColumnWise(min, max))
                && hasAtLeastStarsInSequence(5, coordinatesRowWise(min, max))
    }

    private fun hasAtLeastStarsInSequence(minInSequence: Int, sequence: Sequence<Coordinates>): Boolean {
        var count = 0
        sequence.forEach {
            if (it in starPositions()) {
                count++
                if (count >= minInSequence) {
                    return true
                }
            } else {
                count = 0
            }
        }
        return false
    }

    private fun coordinatesColumnWise(min: Coordinates, max: Coordinates) = sequence {
        for (col in min.col..max.col) {
            for (row in min.row..max.row) {
                yield(Coordinates(row, col))
            }
        }
    }

    private fun coordinatesRowWise(min: Coordinates, max: Coordinates) = sequence {
        for (row in min.row..max.row) {
            for (col in min.col..max.col) {
                yield(Coordinates(row, col))
            }
        }
    }

    private fun findBorders(): Pair<Coordinates, Coordinates> {
        val minRow = stars.minOf { it.position.row }
        val minCol = stars.minOf { it.position.col }
        val maxRow = stars.maxOf { it.position.row }
        val maxCol = stars.maxOf { it.position.col }
        return Coordinates(minRow, minCol) to Coordinates(maxRow, maxCol)
    }

    companion object {
        fun of(input: List<String>): StarAlignmentGrid = StarAlignmentGrid(input.parsePoints())

        private val inputPattern = "position=<([- 0-9]+),([- 0-9]+)> velocity=<([- 0-9]+),([- 0-9]+)>".toPattern()
        private fun List<String>.parsePoints(): List<Star> = map { line ->
            val matcher = inputPattern.matcher(line)
            require(matcher.matches())
            val (pCol, pRow, vCol, vRow) = (1..4).map {
                matcher.group(it).trim().toInt()
            }
            Star(Coordinates(pRow, pCol), Coordinates(vRow, vCol))
        }
    }
}
