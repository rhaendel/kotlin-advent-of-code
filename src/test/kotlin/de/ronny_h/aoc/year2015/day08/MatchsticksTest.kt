package de.ronny_h.aoc.year2015.day08

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val MatchsticksTest by testSuite {

    testSuite("the number of characters of code") {
        val matchsticks = Matchsticks()
        mapOf(
            """""""" to 2,
            """"abc"""" to 5,
            """"aaa\"aaa"""" to 10,
            """"\x27"""" to 6,
            """"\\"""" to 4,
            """"\\\\"""" to 6,
            """"\\\\""""" to 7,
        ).forEach { (string, expected) ->
            test("$string, $expected") {
                matchsticks.numberOfCharactersOfCode(string) shouldBe expected
            }
        }
    }

    testSuite("the number of characters of the in-memory string") {
        val matchsticks = Matchsticks()
        mapOf(
            """""""" to 0,
            """"abc"""" to 3,
            """"aaa\"aaa"""" to 7,
            """"\x27"""" to 1,
            """"\\x27"""" to 2,
            """"\\"""" to 1,
            """"\\\\"""" to 2,
            """"\\\\""""" to 3,
            """"\\\""""" to 2,
            """"\\""""" to 2,
        ).forEach { (string, expected) ->
            test("$string, $expected") {
                matchsticks.numberOfCharactersInMemory(string) shouldBe expected
            }
        }
    }

    test("unescape ascii characters from hexadecimal escapes") {
        """"\x27"""".unescape() shouldBe """_"""
        """"\xb7"""".unescape() shouldBe """_"""
    }

    test("part 1: the total number of code chars minus number of in-memory chars") {
        val input = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27"""",
        )
        Matchsticks().part1(input) shouldBe 12
    }

    testSuite("the number of characters of encoded string") {
        val matchsticks = Matchsticks()
        mapOf(
            """""""" to 6,
            """"abc"""" to 9,
            """"aaa\"aaa"""" to 16,
            """"\x27"""" to 11,
        ).forEach { (string, expected) ->
            test("$string, $expected") {
                matchsticks.numberOfCharactersEncoded(string) shouldBe expected
            }
        }
    }

    test("part 2: the total number of newly encoded chars minus number of code chars") {
        val input = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27"""",
        )
        Matchsticks().part2(input) shouldBe 19
    }
}
