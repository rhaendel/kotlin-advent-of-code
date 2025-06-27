package de.ronny_h.aoc.year2024.day16

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ReindeerMazeTest : StringSpec({
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

    "part 1: The lowest score a Reindeer could possibly get" {
        forAll(
            row(smallInput1, 7036),
            row(smallInput2, 11048),
        ) { input, result ->
            ReindeerMaze().part1(input) shouldBe result
        }
    }

    "part 2: Number of tiles that are part of at least one of the best paths" {
        forAll(
            row(smallInput1, 45),
            row(smallInput2, 64),
        ) { input, result ->
            ReindeerMaze().part2(input) shouldBe result
        }
    }
})
