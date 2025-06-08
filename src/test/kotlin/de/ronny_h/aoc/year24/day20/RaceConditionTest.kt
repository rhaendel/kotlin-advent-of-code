package de.ronny_h.aoc.year24.day20

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RaceConditionTest : StringSpec({
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

    "part 1: Number of 2ps cheats saving at least 10 picoseconds" {
        RaceCondition().part1Small(input) shouldBe 10
    }

    "part 2: Number of 20ps cheats saving at least 76 picoseconds" {
        RaceCondition().part2Small(input) shouldBe 3
    }
})
