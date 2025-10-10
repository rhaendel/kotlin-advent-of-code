package de.ronny_h.aoc.year2018.day09

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day09Test : StringSpec({

    "part 1: the highest score with the given number of players and marbles" {
        forAll(
            row(9, 25, 32),
            row(10, 1618, 8317),
            row(13, 7999, 146373),
            row(17, 1104, 2764),
            row(21, 6111, 54718),
            row(30, 5807, 37305),
        ) { players, marbles, highScore ->
            val input = listOf("$players players; last marble is worth $marbles points")
            Day09().part1(input) shouldBe highScore
        }
    }

    "part 2: the highest score with the given number of players and 100 times the number of marbles" {
        val input = listOf("9 players; last marble is worth 25 points")
        Day09().part2(input) shouldBe 22563
    }
})
