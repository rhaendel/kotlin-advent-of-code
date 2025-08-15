package de.ronny_h.aoc.year2017.day09

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class StreamProcessingTest : StringSpec({

    "the number of groups" {
        forAll(
            row("{}", 1),
            row("{{{}}}", 3),
            row("{{},{}}", 3),
            row("{{{},{},{{}}}}", 6),
            row("{<{},{},{{}}>}", 1),
            row("{<a>,<a>,<a>,<a>}", 1),
            row("{{<a>},{<a>},{<a>},{<a>}}", 5),
            row("{{<!>},{<!>},{<!>},{<a>}}", 2),
        ) { input, numberOfGroups ->
            input.parseGroups().count shouldBe numberOfGroups
        }
    }

    "the total score for all groups in the input" {
        forAll(
            row("{}", 1),
            row("{{{}}}", 6),
            row("{{},{}}", 5),
            row("{{{},{},{{}}}}", 16),
            row("{<a>,<a>,<a>,<a>}", 1),
            row("{{<ab>},{<ab>},{<ab>},{<ab>}}", 9),
            row("{{<!!>},{<!!>},{<!!>},{<!!>}}", 9),
            row("{{<a!>},{<a!>},{<a!>},{<ab>}}", 3),
        ) { input, score ->
            input.parseGroups().score shouldBe score
        }
    }

    "the number of non-canceled characters within the garbage" {
        forAll(
            row("{<>}", 0),
            row("{<random characters>}", 17),
            row("{<<<<>}", 3),
            row("{<{!>}>}", 2),
            row("{<!!>}", 0),
            row("{<!!!>>}", 0),
            row("{<{o\"i!a,<{i<a>}", 10),
        ) { input, garbage ->
            input.parseGroups().garbage shouldBe garbage
        }
    }

    "part 1: the total score for all groups in the input" {
        val input = listOf("{{<ab>},{<ab>},{<ab>},{<ab>}}")
        StreamProcessing().part1(input) shouldBe 9
    }

    "part 2: the number of non-canceled characters within the garbage" {
        val input = listOf("{<{!>}>}")
        StreamProcessing().part2(input) shouldBe 2
    }
})
