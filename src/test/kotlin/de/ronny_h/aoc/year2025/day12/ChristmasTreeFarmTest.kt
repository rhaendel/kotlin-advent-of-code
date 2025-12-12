package de.ronny_h.aoc.year2025.day12

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ChristmasTreeFarmTest : StringSpec({

    val input = """
        0:
        ###
        ##.
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 2 1 5 3 2
    """.asList()

    "parse presents" {
        val input = """
            0:
            ###
            ##.
            ##.
    
            1:
            ###
            ##.
            .##
    
            4x4: 0 0
            12x5: 1 0
        """.asList()

        input.parsePresents() shouldBe Presents(
            shapes = listOf(
                PresentShape(0, listOf("###", "##.", "##.")),
                PresentShape(1, listOf("###", "##.", ".##")),
            ),
            regions = listOf(
                Region(4, 4, listOf(0, 0)),
                Region(12, 5, listOf(1, 0)),
            )
        )
    }

    "PresentShape's number of tiles" {
        forAll(
            row(listOf("...", "...", "..."), 0),
            row(listOf("#.#", ".##", ".#."), 5),
            row(listOf("###", "##.", "##."), 7),
            row(listOf("###", "###", "###"), 9),
        ) { input, expected ->
            PresentShape(0, input).numberOfTiles shouldBe expected
        }
    }

    "part 1: the number of regions that can fit all of the presents listed" {
        ChristmasTreeFarm().part1(input) shouldBe 2
    }

    "part 2 was to solve all of the year's previous puzzles" {
        ChristmasTreeFarm().part2(listOf()) shouldBe 0
    }
})
