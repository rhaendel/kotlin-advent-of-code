package de.ronny_h.aoc.year2018.day07

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2018.day07.TheSumOfItsParts.Companion.createDependencyGraph
import de.ronny_h.aoc.year2018.day07.TheSumOfItsParts.Companion.simulateSteps
import io.kotest.matchers.shouldBe

val TheSumOfItsPartsTest by testSuite {

    val input = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.
    """.asList()

    test("Input can be parsed") {
        input.parseSteps() shouldBe listOf(
            "C" to "A",
            "C" to "F",
            "A" to "B",
            "A" to "D",
            "B" to "E",
            "D" to "E",
            "F" to "E",
        )
    }

    test("part 1: the order the steps in the instructions should be completed") {
        TheSumOfItsParts().part1(input) shouldBe "CABDFE"
    }

    test("simulate steps with two workers") {
        input.createDependencyGraph().simulateSteps(2, 0) shouldBe "15"
    }

    test("part 2: the number of seconds it takes with 5 workers") {
        TheSumOfItsParts().part2(input) shouldBe "253"
    }
}
