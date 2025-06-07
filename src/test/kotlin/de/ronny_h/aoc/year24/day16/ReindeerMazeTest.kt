package de.ronny_h.aoc.year24.day16

import de.ronny_h.aoc.extensions.asList
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

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
        forall(
            row(smallInput1, 7036),
            row(smallInput2, 11048),
        ) { input, result ->
            ReindeerMazeRunner().part1(input) shouldBe result
        }
    }

    "part 2: Number of tiles that are part of at least one of the best paths" {
        forall(
            row(smallInput1, 45),
            row(smallInput2, 64),
        ) { input, result ->
            ReindeerMazeRunner().part2(input) shouldBe result
        }
    }
})
