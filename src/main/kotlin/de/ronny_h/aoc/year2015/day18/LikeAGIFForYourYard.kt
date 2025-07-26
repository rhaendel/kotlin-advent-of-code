package de.ronny_h.aoc.year2015.day18

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = LikeAGIFForYourYard().run(768, 0)

class LikeAGIFForYourYard : AdventOfCode<Int>(2015, 18) {

    override fun part1(input: List<String>): Int = GameOfLightGrid(input).animateSteps(100).countLightsOn()

    override fun part2(input: List<String>): Int =
        GameOfLightGrid(input).animateStepsWithCornersStuckOn(100).countLightsOn()

    companion object {
        fun GameOfLightGrid.animateSteps(steps: Int): GameOfLightGrid {
            var grid = this
            repeat(steps) { grid = grid.animateStep() }
            return grid
        }

        fun GameOfLightGrid.animateStepsWithCornersStuckOn(steps: Int): GameOfLightGrid {
            var grid = this.withCornersStuckOn()
            repeat(steps) { grid = grid.animateStep().withCornersStuckOn() }
            return grid
        }

        private fun GameOfLightGrid.animateStep(): GameOfLightGrid {
            val nextGeneration = GameOfLightGrid(this.height, this.width)
            forEachCoordinates { position, light ->
                val neighboursOn = position.neighboursIncludingDiagonals().count { getAt(it) == on }
                val nextState = if (neighboursOn == 3 || light == on && neighboursOn == 2) on else off
                nextGeneration.setAt(position, nextState)
            }.last()
            return nextGeneration
        }

        private fun GameOfLightGrid.withCornersStuckOn(): GameOfLightGrid {
            setAt(Coordinates(0, 0), on)
            setAt(Coordinates(0, width - 1), on)
            setAt(Coordinates(height - 1, 0), on)
            setAt(Coordinates(height - 1, width - 1), on)
            return this
        }
    }
}

private const val on = '#'
private const val off = '.'

class GameOfLightGrid(height: Int, width: Int) : Grid<Char>(height, width, nullElement = off) {

    constructor(input: List<String>) : this(input.size, input[0].length) {
        initGrid(input)
    }

    override fun Char.toElementType() = this

    fun countLightsOn(): Int = forEachElement { _, _, light ->
        if (light == on) 1 else 0
    }.sum()
}
