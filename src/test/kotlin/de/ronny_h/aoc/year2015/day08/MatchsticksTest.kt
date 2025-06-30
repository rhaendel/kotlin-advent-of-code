package de.ronny_h.aoc.year2015.day08

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class MatchsticksTest : StringSpec({

    "the number of characters of code" {
        val matchsticks = Matchsticks()
        forAll(
            row("""""""", 2),
            row(""""abc"""", 5),
            row(""""aaa\"aaa"""", 10),
            row(""""\x27"""", 6),
            row(""""\\"""", 4),
            row(""""\\\\"""", 6),
            row(""""\\\\""""", 7),
        ) { string, expected ->
            matchsticks.numberOfCharactersOfCode(string) shouldBe expected
        }
    }

    "the number of characters of the in-memory string" {
        val matchsticks = Matchsticks()
        forAll(
            row("""""""", 0),
            row(""""abc"""", 3),
            row(""""aaa\"aaa"""", 7),
            row(""""\x27"""", 1),
            row(""""\\x27"""", 2),
            row(""""\\"""", 1),
            row(""""\\\\"""", 2),
            row(""""\\\\""""", 3),
            row(""""\\\""""", 2),
            row(""""\\""""", 2),
        ) { string, expected ->
            matchsticks.numberOfCharactersInMemory(string) shouldBe expected
        }
    }

    "unescape ascii characters from hexadecimal escapes" {
        """"\x27"""".unescape() shouldBe """_"""
        """"\xb7"""".unescape() shouldBe """_"""
    }

    "part 1: the total number of code chars minus number of in-memory chars" {
        val input = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27"""",
        )
        Matchsticks().part1(input) shouldBe 12
    }

    "the number of characters of encoded string" {
        val matchsticks = Matchsticks()
        forAll(
            row("""""""", 6),
            row(""""abc"""", 9),
            row(""""aaa\"aaa"""", 16),
            row(""""\x27"""", 11),
        ) { string, expected ->
            matchsticks.numberOfCharactersEncoded(string) shouldBe expected
        }
    }

    "part 2: the total number of newly encoded chars minus number of code chars" {
        val input = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27"""",
        )
        Matchsticks().part2(input) shouldBe 19
    }
})
