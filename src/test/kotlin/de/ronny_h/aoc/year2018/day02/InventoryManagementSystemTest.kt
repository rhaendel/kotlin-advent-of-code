package de.ronny_h.aoc.year2018.day02

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val InventoryManagementSystemTest by testSuite {

    test("part 1: the checksum (product of number of letters appearing twice and 3 times exactly)") {
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

    test("part 2: the common letters between the two correct box IDs") {
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

    data class Row(val a: String, val b: String, val distance: Int)

    testSuite("The Levenshtein distance") {
        listOf(
            Row("", "", 0),
            Row("", "a", 1),
            Row("a", "", 1),
            Row("abc", "abc", 0),
            Row("abc", "a_c", 1),
            Row("abc", "xyz", 3),
            Row("abc", "abcxyz", 3),
        ).forEach { (a, b, distance) ->
            test("$a, $b, $distance") {
                lev(a, b) shouldBe distance
            }
        }
    }

    data class R(val a: String, val b: String, val maxDistance: Int, val distance: Int)

    testSuite("The Levenshtein distance with an upper bound") {
        listOf(
            R("", "", 1, 0),
            R("", "a", 1, 1),
            R("a", "", 1, 1),
            R("abc", "abc", 1, 0),
            R("abc", "a_c", 1, 1),
            R("abc", "axbyc", 1, 2),
            R("abc", "axbycz", 1, 2),
            R("abc", "xyz", 1, 2),
            R("abc", "abcxyz", 1, 3),
        ).forEach { (a, b, maxDistance, distance) ->
            test("$a, $b, $maxDistance") {
                lev(a, b, maxDistance) shouldBe distance
            }
        }
    }
}
