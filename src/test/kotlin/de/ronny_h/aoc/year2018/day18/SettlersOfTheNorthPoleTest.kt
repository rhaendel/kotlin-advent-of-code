package de.ronny_h.aoc.year2018.day18

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val SettlersOfTheNorthPoleTest by testSuite {

    val input = """
        .#.#...|#.
        .....#|##|
        .|..|...#.
        ..|#.....#
        #.#|||#|#|
        ...#.||...
        .|....|...
        ||...#|.#|
        |.||||..|.
        ...#.|..|.
    """.asList()

    test("the acre after 1 minute") {
        val expected = """
            .......##.
            ......|###
            .|..|...#.
            ..|#||...#
            ..##||.|#|
            ...#||||..
            ||...|||..
            |||||.||.|
            ||||||||||
            ....||..|.
        """.asList()

        LumberAcre(input).changeAMinute() shouldBe LumberAcre(expected)
    }

    test("the acre after 10 minutes") {
        val expected = """
            .||##.....
            ||###.....
            ||##......
            |##.....##
            |##.....##
            |##....##|
            ||##.####|
            ||#####|||
            ||||#|||||
            ||||||||||
        """.asList()

        LumberAcre(input).afterTenMinutes() shouldBe LumberAcre(expected)
    }

    test("part 1: the total resource value of the lumber collection area after 10 minutes") {
        SettlersOfTheNorthPole().part1(input) shouldBe 1147
    }

    test("part 2") {
        SettlersOfTheNorthPole().part2(input) shouldBe 0
    }
}
