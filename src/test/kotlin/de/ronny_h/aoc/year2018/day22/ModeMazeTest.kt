package de.ronny_h.aoc.year2018.day22

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.year2018.day22.RegionType.*
import io.kotest.matchers.shouldBe

val ModeMazeTest by testSuite {
    val input = """
            depth: 510
            target: 10,10
        """.asList()

    test("input can be parsed") {
        val input = """
            depth: 510
            target: 10,11
        """.asList()

        val cave = CaveSystem(input)
        cave.depth shouldBe 510
        cave.target shouldBe Coordinates(10, 11)
    }

    testSuite("geologic index") {
        mapOf(
            Coordinates(0, 0) to 0,
            Coordinates(1, 0) to 16807,
            Coordinates(0, 1) to 48271,
            Coordinates(1, 1) to 145722555,
            Coordinates(10, 10) to 0,
        ).forEach { (c, index) ->
            test("$c: $index") {
                CaveSystem(input).geologicIndex(c) shouldBe index
            }
        }
    }

    testSuite("erosion level") {
        mapOf(
            Coordinates(0, 0) to 510,
            Coordinates(1, 0) to 17317,
            Coordinates(0, 1) to 8415,
            Coordinates(1, 1) to 1805,
            Coordinates(10, 10) to 510,
        ).forEach { (c, index) ->
            test("$c: $index") {
                CaveSystem(input).erosionLevel(c) shouldBe index
            }
        }
    }

    testSuite("region type") {
        mapOf(
            Coordinates(0, 0) to ROCKY,
            Coordinates(1, 0) to WET,
            Coordinates(0, 1) to ROCKY,
            Coordinates(1, 1) to NARROW,
            Coordinates(10, 10) to ROCKY,
        ).forEach { (c, index) ->
            test("$c: $index") {
                CaveSystem(input).typeOf(c) shouldBe index
            }
        }
    }

    test("part 1: the area risk level") {
        ModeMaze().part1(input) shouldBe 114
    }

    test("part 2: the fewest number of minutes to reach the target") {
        ModeMaze().part2(input) shouldBe 45
    }
}
