package de.ronny_h.aoc.year2018.day12

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split

fun main() = SubterraneanSustainability().run(3798, 0)

class SubterraneanSustainability : AdventOfCode<Int>(2018, 12) {
    override fun part1(input: List<String>): Int {
        val pots = GameOfPlants(input, 20)
        pots.simulateGenerations()
        return pots.sumOfPlantContainingPotNumbers()
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class GameOfPlants(input: List<String>, val generations: Int) {
    private val plant = '#'
    private val noPlant = '.'
    private val initialState = input.first().substringAfter("initial state: ")

    // a rule has the form of: LLCRR => N
    private val plantProducingPatterns = input.split()[1]
        .map { it.split(" => ") }
        .filter { it[1] == "$plant" }
        .map { it.first() }
        .toSet()
    private val patternLength = 5
    private val patternCenter = 2

    // per generation the plants grow max 2 pots wider in each direction
    private val offset = generations * 2
    private val padding = "$noPlant".repeat(offset)

    var currentGeneration = padding + initialState + padding
        private set

    fun nextGeneration(): String {
        val nextGen = currentGeneration.toMutableList()

        for (index in 0..nextGen.lastIndex - patternLength) {
            nextGen[index + patternCenter] =
                if (currentGeneration.substring(index, index + patternLength) in plantProducingPatterns) {
                    plant
                } else {
                    noPlant
                }
        }
        currentGeneration = nextGen.joinToString("")
        return currentGeneration
    }

    fun sumOfPlantContainingPotNumbers(): Int =
        currentGeneration.foldIndexed(0) { i, acc, pot ->
            if (pot == plant) acc + i - offset else acc
        }

    fun simulateGenerations() {
        println("generation  0:$currentGeneration")
        repeat(generations) {
            nextGeneration()
            println("generation ${"%2d".format(it + 1)}: $currentGeneration")
        }
    }
}
