package de.ronny_h.aoc.year2018.day17

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ReservoirResearchTest : StringSpec({

    val input = """
        x=495, y=2..7
        y=7, x=495..501
        x=501, y=3..7
        x=498, y=2..4
        x=506, y=1..2
        x=498, y=10..13
        x=504, y=10..13
        y=13, x=498..504
    """.asList()

    "input can be parsed" {
        val expectedSlice = """
            ......+.......
            ............#.
            .#..#.......#.
            .#..#..#......
            .#..#..#......
            .#.....#......
            .#.....#......
            .#######......
            ..............
            ..............
            ....#.....#...
            ....#.....#...
            ....#.....#...
            ....#######...
        """.trimIndent()
        VerticalSliceOfGround(input).toString() shouldBe expectedSlice
    }

    "part 1" {
        val input = listOf("")
        ReservoirResearch().part1(input) shouldBe 0
    }

    "part 2" {
        val input = listOf("")
        ReservoirResearch().part2(input) shouldBe 0
    }
})
