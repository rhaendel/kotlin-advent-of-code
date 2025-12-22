package de.ronny_h.aoc.year2018.day18

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SettlersOfTheNorthPoleTest : StringSpec({

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

    "the acre after 1 minute" {
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

    "the acre after 10 minutes" {
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

    "part 1: the total resource value of the lumber collection area after 10 minutes" {
        SettlersOfTheNorthPole().part1(input) shouldBe 1147
    }

    "part 2" {
        SettlersOfTheNorthPole().part2(input) shouldBe 0
    }
})
