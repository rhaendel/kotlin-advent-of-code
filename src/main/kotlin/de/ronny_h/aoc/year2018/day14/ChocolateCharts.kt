package de.ronny_h.aoc.year2018.day14

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.numbers.digitsReversed

fun main() = ChocolateCharts().run("5992684592", "20181148")

class ChocolateCharts : AdventOfCode<String>(2018, 14) {
    override fun part1(input: List<String>): String {
        val numberOfRecipes = input.single().toInt()
        return ScoreBoard()
            .createNewRecipesUntil { it.size == numberOfRecipes + 10 }
            .substring(numberOfRecipes, numberOfRecipes + 10)
    }

    override fun part2(input: List<String>): String {
        val inputString = input.single()
        val puzzleInput = inputString.toList().map(Char::digitToInt)
        return ScoreBoard()
            .createNewRecipesUntil { it.endsWithSubList(puzzleInput) }
            .substringBefore(inputString).length.toString()
    }
}

private fun List<Int>.endsWithSubList(sublist: List<Int>): Boolean {
    if (size < sublist.size) return false
    return subList(size - sublist.size, size) == sublist
}

private class ScoreBoard {
    private val scores = mutableListOf(3, 7)

    fun createNewRecipesUntil(stopCondition: (List<Int>) -> Boolean): String {
        var indexOne = 0
        var indexTwo = 1
        while (true) {
            val scoreOne = scores[indexOne]
            val scoreTwo = scores[indexTwo]
            (scoreOne + scoreTwo).digitsReversed().forEach {
                scores.add(it)
                if (stopCondition(scores)) {
                    return scores.joinToString("")
                }
            }
            indexOne = (indexOne + 1 + scoreOne) % scores.size
            indexTwo = (indexTwo + 1 + scoreTwo) % scores.size
        }
    }
}
