package de.ronny_h.aoc.year2018.day09

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.MutableRingList

fun main() = Day09().run(413188, 0)

class Day09 : AdventOfCode<Int>(2018, 9) {
    override fun part1(input: List<String>): Int {
        val (numberOfPlayers, numberOfMarbles) = input.parse()
        return playTheGame(numberOfMarbles, numberOfPlayers)
    }

    override fun part2(input: List<String>): Int {
        val (numberOfPlayers, numberOfMarbles) = input.parse()
        return playTheGame(numberOfMarbles * 100, numberOfPlayers)
    }

    private fun playTheGame(numberOfMarbles: Int, numberOfPlayers: Int): Int {
        val circle = MutableRingList<Int>(0)
        circle.insert(0)
        val scores = mutableMapOf<Int, Int>().withDefault { 0 }
        var player = 1
        for (currentMarbleNumber in 1..numberOfMarbles) {
            if (currentMarbleNumber % 23 == 0) {
                circle.shiftRight(7)
                scores[player] = scores.getValue(player) + currentMarbleNumber + circle.removeFirst()
            } else {
                circle.shiftLeft(2)
                circle.insert(currentMarbleNumber)
            }
            player = (player % numberOfPlayers) + 1
        }
        return scores.values.max()
    }

    private fun List<String>.parse(): Pair<Int, Int> {
        val pattern = "(\\d+) players; last marble is worth (\\d+) points".toPattern()
        val matcher = pattern.matcher(this.first())
        require(matcher.matches())
        val numberOfPlayers = matcher.group(1).toInt()
        val numberOfMarbles = matcher.group(2).toInt()
        return Pair(numberOfPlayers, numberOfMarbles)
    }
}
