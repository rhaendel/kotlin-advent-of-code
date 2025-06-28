package de.ronny_h.aoc.year2015.day05

import de.ronny_h.aoc.AdventOfCode

fun main() = InternElves().run(255, 55)

class InternElves : AdventOfCode<Int>(2015, 5) {
    override fun part1(input: List<String>): Int = input
        .filter(::hasThreeVowels)
        .filter(::hasALetterTwiceInARow)
        .count(::doesNotContainBadStrings)

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private val badStrings = setOf("ab", "cd", "pq", "xy")

    fun hasThreeVowels(string: String) = string.filter { it in vowels }.length >= 3

    fun hasALetterTwiceInARow(string: String): Boolean {
        for (i in string.indices) {
            if (i != string.lastIndex && string[i] == string[i + 1]) return true
        }
        return false
    }

    fun doesNotContainBadStrings(string: String) = badStrings.all { !string.contains(it) }

    override fun part2(input: List<String>): Int = input
        .filter(::containsAPairOfLettersAtLeastTwiceWithoutOverlapping)
        .count(::containsALetterRepeatingWithExactlyOneLetterInBetween)

    fun containsAPairOfLettersAtLeastTwiceWithoutOverlapping(string: String): Boolean {
        for (i in string.indices) {
            if (i < string.lastIndex - 1) {
                val pair = "${string[i]}${string[i + 1]}"
                if (string.findAnyOf(listOf(pair), i + 2) != null) {
                    return true
                }
            }
        }
        return false
    }

    fun containsALetterRepeatingWithExactlyOneLetterInBetween(string: String): Boolean {
        for (i in string.indices) {
            if (i < string.lastIndex - 1) {
                if (string[i] == string[i + 2]) return true
            }
        }
        return false
    }
}
