package de.ronny_h.aoc.year2018.day14

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ChocolateChartsTest : StringSpec({

    "part 1: the scores of the next 10 recipes after" {
        forAll(
            row(5, "0124515891"),
            row(9, "5158916779"),
            row(18, "9251071085"),
            row(2018, "5941429882"),
        ) { after, scores ->
            ChocolateCharts().part1(listOf("$after")) shouldBe scores
        }
    }

    "part 2: the number of recipes appear on the scoreboard to the left of the score sequence in the puzzle input" {
        forAll(
            row("01245", "5"),
            row("51589", "9"),
            row("92510", "18"),
            row("59414", "2018"),
        ) { puzzleInput, numberOfRecipesBefore ->
            ChocolateCharts().part2(listOf(puzzleInput)) shouldBe numberOfRecipesBefore
        }
    }
})
