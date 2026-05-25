package de.ronny_h.aoc.year2024.day16

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val ReindeerMazeTest by testSuite {
    val smallInput1 = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############
    """.asList()
    val smallInput2 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.asList()

    testSuite(
        "part 1: The lowest score a Reindeer could possibly get",
        mapOf(
            smallInput1 to 7036,
            smallInput2 to 11048,
        ),
    ) { input, result ->
        ReindeerMaze().part1(input) shouldBe result
    }

    testSuite(
        "part 2: Number of tiles that are part of at least one of the best paths",
        mapOf(
            smallInput1 to 45,
            smallInput2 to 64,
        ),
    ) { input, result ->
        ReindeerMaze().part2(input) shouldBe result
    }
}
