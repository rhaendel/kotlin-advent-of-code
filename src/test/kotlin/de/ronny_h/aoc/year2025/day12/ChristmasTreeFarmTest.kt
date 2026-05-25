package de.ronny_h.aoc.year2025.day12

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val ChristmasTreeFarmTest by testSuite {

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

    test("parse presents") {
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

    testSuite(
        "PresentShape's number of tiles",
        mapOf(
            listOf("...", "...", "...") to 0,
            listOf("#.#", ".##", ".#.") to 5,
            listOf("###", "##.", "##.") to 7,
            listOf("###", "###", "###") to 9,
        ),
    ) { input, expected ->
        PresentShape(0, input).numberOfTiles shouldBe expected
    }

    test("part 1: the number of regions that can fit all of the presents listed") {
        ChristmasTreeFarm().part1(input) shouldBe 2
    }

    test("part 2 was to solve all of the year's previous puzzles") {
        ChristmasTreeFarm().part2(listOf()) shouldBe 0
    }
}
