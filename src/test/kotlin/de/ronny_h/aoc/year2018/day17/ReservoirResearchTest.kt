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
        VerticalSliceOfGround(input).toString(1) shouldBe expectedSlice
    }

    "let the water flow according to the puzzle's example" {
        val expectedSlice = """
            ......+.......
            ......|.....#.
            .#..#||||...#.
            .#..#~~#|.....
            .#..#~~#|.....
            .#~~~~~#|.....
            .#~~~~~#|.....
            .#######|.....
            ........|.....
            ...|||||||||..
            ...|#~~~~~#|..
            ...|#~~~~~#|..
            ...|#~~~~~#|..
            ...|#######|..
        """.trimIndent()

        val slice = VerticalSliceOfGround(input)
        slice.waterFlow(slice.springCoordinates)
        slice.toString(1) shouldBe expectedSlice
    }

    "overflow within a bin" {
        val input = """
            x=498, y=2..6
            x=505, y=2..6
            x=502, y=3..4
            y=6, x=498..505
        """.asList()
        val expectedInit = """
            ...+......
            ..........
            .#......#.
            .#...#..#.
            .#...#..#.
            .#......#.
            .########.
        """.trimIndent()
        val expectedFlooded = """
            ....+.......
            .||||||||||.
            .|#~~~~~~#|.
            .|#~~~#~~#|.
            .|#~~~#~~#|.
            .|#~~~~~~#|.
            .|########|.
        """.trimIndent()

        val slice = VerticalSliceOfGround(input)
        slice.toString(1) shouldBe expectedInit
        slice.waterFlow(slice.springCoordinates)
        slice.toString(1) shouldBe expectedFlooded
    }

    "plateau within a bin" {
        val input = """
            x=496, y=2..6
            x=503, y=2..6
            x=499, y=3..4
            x=500, y=3..4
            y=6, x=496..503
        """.asList()
        val expectedInit = """
            .....+....
            ..........
            .#......#.
            .#..##..#.
            .#..##..#.
            .#......#.
            .########.
        """.trimIndent()
        val expectedFlooded = """
            ......+.....
            .||||||||||.
            .|#~~~~~~#|.
            .|#~~##~~#|.
            .|#~~##~~#|.
            .|#~~~~~~#|.
            .|########|.
        """.trimIndent()

        val slice = VerticalSliceOfGround(input)
        slice.toString(1) shouldBe expectedInit
        slice.waterFlow(slice.springCoordinates)
        slice.toString(1) shouldBe expectedFlooded
    }

    "two springs into one bin" {
        val input = """
            x=500, y=2..2
            x=499, y=5..7
            x=503, y=3..7
            y=7, x=499..503
        """.asList()
        val expectedInit = """
            ..+....
            .......
            ..#....
            .....#.
            .....#.
            .#...#.
            .#...#.
            .#####.
        """.trimIndent()
        val expectedFlooded = """
            ...+....
            ..|||...
            ..|#|...
            ..|.|.#.
            .|||||#.
            .|#~~~#.
            .|#~~~#.
            .|#####.
        """.trimIndent()

        val slice = VerticalSliceOfGround(input)
        slice.toString(1) shouldBe expectedInit
        slice.waterFlow(slice.springCoordinates)
        slice.toString(1) shouldBe expectedFlooded
    }

    "part 1: the number of tiles the water can reach" {
        ReservoirResearch().part1(input) shouldBe 57
    }

    "part 2: the number of tiles with resting water" {
        ReservoirResearch().part2(input) shouldBe 29
    }
})
