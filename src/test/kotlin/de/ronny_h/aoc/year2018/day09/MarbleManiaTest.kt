package de.ronny_h.aoc.year2018.day09

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val MarbleManiaTest by testSuite {

    data class Row(val players: Int, val marbles: Int, val highScore: Int)

    testSuite("part 1: the highest score with the given number of players and marbles") {
        listOf(
            Row(9, 25, 32),
            Row(10, 1618, 8317),
            Row(13, 7999, 146373),
            Row(17, 1104, 2764),
            Row(21, 6111, 54718),
            Row(30, 5807, 37305),
        ).forEach { (players, marbles, highScore) ->
            test("$players, $marbles, $highScore") {
                val input = listOf("$players players; last marble is worth $marbles points")
                MarbleMania().part1(input) shouldBe highScore
            }
        }
    }

    test("part 2: the highest score with the given number of players and 100 times the number of marbles") {
        val input = listOf("9 players; last marble is worth 25 points")
        MarbleMania().part2(input) shouldBe 22563
    }
}
