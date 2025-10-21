package de.ronny_h.aoc.year2018.day12

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split

private val verbose = false

fun main() = SubterraneanSustainability().run(3798, 3900000002212)

class SubterraneanSustainability : AdventOfCode<Long>(2018, 12) {
    override fun part1(input: List<String>): Long {
        val pots = GameOfPlants(input, 20)
        pots.simulateGenerations()
        return pots.sumOfPlantContainingPotNumbers()
    }

    override fun part2(input: List<String>): Long {
        val generations = 50000000000
        val pots = GameOfPlants(input, generations)
        val (generationCount, shifted) = pots.simulateGenerations()
        return pots.sumOfPlantContainingPotNumbers(generations - generationCount, shifted)
    }
}

class GameOfPlants(input: List<String>, private val generations: Long) {
    private val plant = '#'
    private val noPlant = '.'
    private val initialState = input.first().substringAfter("initial state: ").toList()

    // a rule has the form of: LLCRR => N
    private val plantProducingPatterns = input.split()[1]
        .map { it.split(" => ") }
        .filter { it[1] == "$plant" }
        .map { it.first().toList() }
        .toSet()
    private val patternLength = 5
    private val patternCenter = 2

    // per generation the plants grow max 2 pots wider in each direction
    private val paddingLength = 40
    private var offset = 0
    private val padding = List(paddingLength) { noPlant }

    private var currentGeneration = initialState.toMutableList()
    private var nextGeneration = currentGeneration.toMutableList()

    fun nextGeneration(): List<Char> {
        var firstPlantIndex = currentGeneration.indexOf(plant)
        if (firstPlantIndex < patternLength) {
            firstPlantIndex += addPaddingAtFront()
        }
        if (currentGeneration.lastIndexOf(plant) > currentGeneration.lastIndex - patternLength) {
            addPaddingAtEnd()
        }
        for (index in firstPlantIndex - patternLength..nextGeneration.lastIndex - patternLength) {
            nextGeneration[index + patternCenter] =
                if (currentGeneration.subList(index, index + patternLength) in plantProducingPatterns) {
                    plant
                } else {
                    noPlant
                }
        }
        swapGenerations()
        return currentGeneration
    }

    private fun swapGenerations() {
        val tmp = currentGeneration
        currentGeneration = nextGeneration
        nextGeneration = tmp
    }

    private fun addPaddingAtFront(): Int {
        if (verbose) println("adding padding at the beginning")
        offset += paddingLength
        currentGeneration = (padding + currentGeneration).toMutableList()
        nextGeneration = currentGeneration.toMutableList()
        return paddingLength
    }

    private fun addPaddingAtEnd() {
        if (verbose) println("adding padding at the end")
        currentGeneration = (currentGeneration + padding).toMutableList()
        nextGeneration = currentGeneration.toMutableList()
    }

    fun sumOfPlantContainingPotNumbers(remainingGenerations: Long = 0L, shiftPerGeneration: Int = 0): Long {
        val correction = remainingGenerations * shiftPerGeneration - offset
        return currentGeneration.foldIndexed(0L) { i, acc, pot ->
            if (pot == plant) acc + i + correction else acc
        }
    }

    // returns: a pair of
    //  - the number of the generation at which the configuration of plants does not change anymore and
    //  - the number of positions the pattern is shifted compared to the previous generation
    fun simulateGenerations(): Pair<Long, Int> {
        if (verbose) println("generation  0:${currentGeneration.joinToString("")}")
        var generationCount = 1L
        var lastPlantCount = currentGeneration.count { it == plant }
        while (generationCount <= generations) {
            nextGeneration()
            if (generationCount < 202 || generationCount % 10000000 == 0L) {
                if (verbose) println("generation ${"%2d".format(generationCount)}: ${currentGeneration.joinToString("")}")
            }
            val thisPlantCount = currentGeneration.count { it == plant }
            if (thisPlantCount == lastPlantCount) {
                if (currentGeneration.allPlantsContainingSubList() == nextGeneration.allPlantsContainingSubList()) {
                    if (verbose) println("generation $generationCount: plants configuration did not change")
                    return generationCount to currentGeneration.indexOf(plant) - nextGeneration.indexOf(plant)
                }
            }
            generationCount++
            lastPlantCount = thisPlantCount
        }
        return generationCount to 0
    }

    private fun MutableList<Char>.allPlantsContainingSubList(): MutableList<Char> =
        subList(indexOf(plant), lastIndexOf(plant) + 1)
}
