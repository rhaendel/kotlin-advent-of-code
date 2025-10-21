package de.ronny_h.aoc.year2018.day12

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class SubterraneanSustainabilityTest : StringSpec({

    val input = """
        initial state: #..#.#..##......###...###

        ...## => #
        ..#.. => #
        .#... => #
        .#.#. => #
        .#.## => #
        .##.. => #
        .#### => #
        #.#.# => #
        #.### => #
        ##.#. => #
        ##.## => #
        ###.. => #
        ###.# => #
        ####. => #
    """.asList()

    "the first generation of the sample input" {
        GameOfPlants(input, 20).nextGeneration() shouldContain "...#...#....#.....#..#..#..#..........."
    }

    "part 1: the sum of the numbers of plant-containing pots after the 20th generation" {
        SubterraneanSustainability().part1(input) shouldBe 325
    }

    "part 2" {
        val input = listOf("")
        SubterraneanSustainability().part2(input) shouldBe 0
    }
})
