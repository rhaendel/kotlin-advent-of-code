package de.ronny_h.aoc.year2024.day12

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val GardenGroupsTest by testSuite {
    val smallInput = """
        AAAA
        BBCD
        BBCC
        EEEC
    """.asList()
    val mediumInput1 = """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
    """.asList()
    val mediumInput2 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.asList()
    val mediumInput3 = """
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE
    """.asList()
    val mediumInput4 = """
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA
    """.asList()

    testSuite(
        "part 1: The total price of fencing all regions",
        mapOf(
            smallInput to 140,
            mediumInput1 to 772,
            mediumInput2 to 1930,
        ),
    ) { input, result ->
        GardenGroups().part1(input) shouldBe result
    }


    testSuite(
        "part 2: The total price of fencing all regions with a bulk discount",
        mapOf(
            smallInput to 80,
            mediumInput1 to 436,
            mediumInput2 to 1206,
            mediumInput3 to 236,
            mediumInput4 to 368,
        ),
    ) { input, result ->
        GardenGroups().part2(input) shouldBe result
    }
}
