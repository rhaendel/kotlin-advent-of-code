package de.ronny_h.aoc.year2018.day14

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ChocolateChartsTest by testSuite {

    testSuite("part 1: the scores of the next 10 recipes after") {
        mapOf(
            5 to "0124515891",
            9 to "5158916779",
            18 to "9251071085",
            2018 to "5941429882",
        ).forEach { (after, scores) ->
            test("$after, $scores") {
                ChocolateCharts().part1(listOf("$after")) shouldBe scores
            }
        }
    }

    testSuite("part 2: the number of recipes appear on the scoreboard to the left of the score sequence in the puzzle input") {
        mapOf(
            "01245" to "5",
            "51589" to "9",
            "92510" to "18",
            "59414" to "2018",
        ).forEach { (puzzleInput, numberOfRecipesBefore) ->
            test("$puzzleInput, $numberOfRecipesBefore") {
                ChocolateCharts().part2(listOf(puzzleInput)) shouldBe numberOfRecipesBefore
            }
        }
    }
}
