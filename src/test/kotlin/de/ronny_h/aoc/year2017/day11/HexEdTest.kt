package de.ronny_h.aoc.year2017.day11

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.year2017.day11.HexagonalGrid.Direction.*
import de.ronny_h.aoc.year2017.day11.HexagonalGrid.Hextile
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

val HexEdTest by testSuite {

    testSuite("input can be parsed") {
        mapOf(
            "n" to listOf(NORTH),
            "ne" to listOf(NORTH_EAST),
            "se" to listOf(SOUTH_EAST),
            "s" to listOf(SOUTH),
            "sw" to listOf(SOUTH_WEST),
            "nw" to listOf(NORTH_WEST),
            "nw,s,sw" to listOf(NORTH_WEST, SOUTH, SOUTH_WEST),
        ).forEach { (input, expected) ->
            test("$input, $expected") {
                input.parseDirections() shouldBe expected
            }
        }
    }

    testSuite("go in directions leads to target hextile") {
        mapOf(
            "ne,ne,ne" to Hextile(-3,  3),
            "ne,ne,sw,sw" to Hextile(0,  0),
            "ne,ne,s,s" to Hextile(2,  2),
            "se,sw,se,sw,sw" to  Hextile(5,  -1),
        ).forEach { (directions, hextile) ->
            test("$directions, $hextile") {
                val hexGrid = HexagonalGrid()
                directions.parseDirections().forEach { hexGrid.goInDirection(it) }
                hexGrid.currentPosition shouldBe hextile
            }
        }
    }

    testSuite("only valid hextiles can be created") {
        mapOf(
            1 to 0,
            0 to 1,
            11 to 10,
        ).forEach { (row, col) ->
            test("$row, $col") {
                shouldThrow<IllegalStateException> {
                    Hextile(row, col)
                }
            }
        }
    }

    data class row(val first: Hextile, val second: Hextile, val distance: Int)

    testSuite("hextile distances") {
        listOf(
            row(Hextile(0, 0), Hextile(0, 0), 0),
            row(Hextile(0, 0), Hextile(2, 0), 1),
            row(Hextile(0, 0), Hextile(10, 0), 5),
            row(Hextile(-10, 0), Hextile(0, 0), 5),
            row(Hextile(0, 0), Hextile(1, 1), 1),
            row(Hextile(0, 0), Hextile(0, 2), 2),
            row(Hextile(0, 0), Hextile(1, 3), 3),
            row(Hextile(0, 0), Hextile(3, 3), 3),
            row(Hextile(-3, -3), Hextile(0, 0), 3),
            row(Hextile(-3, -3), Hextile(3, 3), 6),
        ).forEach { (first, second, distance) ->
            test("$first, $second, $distance") {
                first distanceTo second shouldBe distance
            }
        }
    }

    testSuite("part 1: the fewest number of steps to reach the target hextile") {
        mapOf(
            "ne,ne,ne" to 3,
            "ne,ne,sw,sw" to 0,
            "ne,ne,s,s" to 2,
            "se,sw,se,sw,sw" to 3,
        ).forEach { (directions, distance) ->
            test("$directions, $distance") {
                val input = listOf(directions)
                HexEd().part1(input) shouldBe distance
            }
        }
    }

    testSuite("part 2: the furthest steps away on the path") {
        mapOf(
            "ne,ne,ne" to 3,
            "ne,ne,sw,sw" to 2,
            "ne,ne,s,s" to 2,
            "se,sw,se,sw,sw" to 3,
        ).forEach { (directions, distance) ->
            test("$directions, $distance") {
                val input = listOf(directions)
                HexEd().part2(input) shouldBe distance
            }
        }
    }
}
