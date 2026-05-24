package de.ronny_h.aoc.year2024.day06

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val GuardGallivantTest by testSuite {
    val smallInput = listOf(
        ".#..",
        "...#",
        ".^..",
        "..#."
    )
    val mediumInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.asList()

    testSuite(
        "part 1: Distinct positions the guard visits before leaving the mapped area",
        mapOf(
            smallInput to 5,
            mediumInput to 41,
        ),
    ) { input, result ->
        GuardGallivant().part1(input) shouldBe result
    }

    testSuite(
        "part 2: Different positions for the obstruction",
        mapOf(
            smallInput to 1,
            mediumInput to 6,
        ),
    ) { input, result ->
        GuardGallivant().part2(input) shouldBe result
    }
}
