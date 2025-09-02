package de.ronny_h.aoc.year2018.day02

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class InventoryManagementSystemTest : StringSpec({

    "part 1: the checksum (product of number of letters appearing twice and 3 times exactly)" {
        val input = """
            abcdef
            bababc
            abbcde
            abcccd
            aabcdd
            abcdee
            ababab
        """.asList()

        InventoryManagementSystem().part1(input) shouldBe "12"
    }

    "part 2: the common letters between the two correct box IDs" {
        val input = """
            abcde
            fghij
            klmno
            pqrst
            fguij
            axcye
            wvxyz
        """.asList()
        InventoryManagementSystem().part2(input) shouldBe "fgij"
    }

    "The Levenshtein distance" {
        forAll(
            row("", "", 0),
            row("", "a", 1),
            row("a", "", 1),
            row("abc", "abc", 0),
            row("abc", "a_c", 1),
            row("abc", "xyz", 3),
            row("abc", "abcxyz", 3),
        ) { a, b, distance ->
            lev(a, b) shouldBe distance
        }
    }

    "The Levenshtein distance with an upper bound" {
        forAll(
            row("", "", 1, 0),
            row("", "a", 1, 1),
            row("a", "", 1, 1),
            row("abc", "abc", 1, 0),
            row("abc", "a_c", 1, 1),
            row("abc", "axbyc", 1, 2),
            row("abc", "axbycz", 1, 2),
            row("abc", "xyz", 1, 2),
            row("abc", "abcxyz", 1, 3),
        ) { a, b, maxDistance, distance ->
            lev(a, b, maxDistance) shouldBe distance
        }
    }
})
