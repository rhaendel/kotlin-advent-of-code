package de.ronny_h.aoc.year24.day04

import de.ronny_h.aoc.extensions.asList
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class CeresSearchTest : StringSpec({
    val smallInput1 = """
        ..X.......
        .SAMX....S
        .A..A...A.
        XMAS.S.M..
        .X....X...
    """.asList()
    val mediumInput1 = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.asList()
    val smallInput2 = """
        M.S
        .A.
        M.S
    """.asList()
    val mediumInput2 = """
        .M.S......
        ..A..MSMS.
        .M.S.MAA..
        ..A.ASMSM.
        .M.S.M....
        ..........
        S.S.S.S.S.
        .A.A.A.A..
        M.M.M.M.M.
        ..........
    """.asList()

    "part 1 - small input 1 contains XMAS five times" {
        CeresSearch().part1(smallInput1) shouldBe 5
    }

    "part 1 - medium input 1 contains XMAS 18 times" {
        CeresSearch().part1(mediumInput1) shouldBe 18
    }

    "part 2 - small input 2 contains MAS in the form of an X once" {
        CeresSearch().part2(smallInput2) shouldBe 1
    }

    "part 2 - medium input 2 contains MAS in the form of an X 9 times" {
        CeresSearch().part2(mediumInput2) shouldBe 9
    }
})
