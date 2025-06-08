package de.ronny_h.aoc.year24.day06

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

class GuardGallivantTest : StringSpec({
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

    "part 1: Distinct positions the guard visits before leaving the mapped area" {
        forAll(
            row(smallInput, 5),
            row(mediumInput, 41),
        ) { input, result ->
            GuardGallivant().part1(input) shouldBe result
        }
    }

    "part 2: Different positions for the obstruction" {
        forAll(
            row(smallInput, 1),
            row(mediumInput, 6),
        ) { input, result ->
            runBlocking {
                GuardGallivant().part2(input) shouldBe result
            }
        }
    }
})
