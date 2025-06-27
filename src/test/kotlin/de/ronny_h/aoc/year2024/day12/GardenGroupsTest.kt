package de.ronny_h.aoc.year2024.day12

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class GardenGroupsTest : StringSpec({
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

    "part 1: The total price of fencing all regions" {
        forAll(
            row(smallInput, 140),
            row(mediumInput1, 772),
            row(mediumInput2, 1930),
        ) { input, result ->
            GardenGroups().part1(input) shouldBe result
        }
    }

    "part 2: The total price of fencing all regions with a bulk discount" {
        forAll(
            row(smallInput, 80),
            row(mediumInput1, 436),
            row(mediumInput2, 1206),
            row(mediumInput3, 236),
            row(mediumInput4, 368),
        ) { input, result ->
            GardenGroups().part2(input) shouldBe result
        }
    }
})
