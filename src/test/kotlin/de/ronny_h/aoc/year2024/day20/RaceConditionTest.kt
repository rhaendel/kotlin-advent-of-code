package de.ronny_h.aoc.year2024.day20

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val RaceConditionTest by testSuite {
    val input = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
    """.asList()

    test("part 1: Number of 2ps cheats saving at least 10 picoseconds") {
        RaceCondition().part1Small(input) shouldBe 10
    }

    test("part 2: Number of 20ps cheats saving at least 76 picoseconds") {
        RaceCondition().part2Small(input) shouldBe 3
    }
}
