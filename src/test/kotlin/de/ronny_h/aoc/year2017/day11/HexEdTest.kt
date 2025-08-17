package de.ronny_h.aoc.year2017.day11

import de.ronny_h.aoc.year2017.day11.HexagonalGrid.Direction.*
import de.ronny_h.aoc.year2017.day11.HexagonalGrid.Hextile
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class HexEdTest : StringSpec({

    "input can be parsed" {
        forAll(
            row("n", listOf(NORTH)),
            row("ne", listOf(NORTH_EAST)),
            row("se", listOf(SOUTH_EAST)),
            row("s", listOf(SOUTH)),
            row("sw", listOf(SOUTH_WEST)),
            row("nw", listOf(NORTH_WEST)),
            row("nw,s,sw", listOf(NORTH_WEST, SOUTH, SOUTH_WEST)),
        ) { input, expected ->
            input.parseDirections() shouldBe expected
        }
    }

    "go in directions leads to target hextile" {
        forAll(
            row("ne,ne,ne", Hextile(-3, 3)),
            row("ne,ne,sw,sw", Hextile(0, 0)),
            row("ne,ne,s,s", Hextile(2, 2)),
            row("se,sw,se,sw,sw", Hextile(5, -1)),
        ) { directions, hextile ->
            val hexGrid = HexagonalGrid()
            directions.parseDirections().forEach { hexGrid.goInDirection(it) }
            hexGrid.currentPosition shouldBe hextile
        }
    }

    "only valid hextiles can be created" {
        forAll(
            row(1, 0),
            row(0, 1),
            row(11, 10),
        ) { row, col ->
            shouldThrow<IllegalStateException> {
                Hextile(row, col)
            }
        }
    }

    "hextile distances" {
        forAll(
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
        ) { first, second, distance ->
            first distanceTo second shouldBe distance
        }
    }

    "part 1: the fewest number of steps to reach the target hextile" {
        forAll(
            row("ne,ne,ne", 3),
            row("ne,ne,sw,sw", 0),
            row("ne,ne,s,s", 2),
            row("se,sw,se,sw,sw", 3),
        ) { directions, distance ->
            val input = listOf(directions)
            HexEd().part1(input) shouldBe distance
        }
    }

    "part 2: the furthest steps away on the path" {
        forAll(
            row("ne,ne,ne", 3),
            row("ne,ne,sw,sw", 2),
            row("ne,ne,s,s", 2),
            row("se,sw,se,sw,sw", 3),
        ) { directions, distance ->
            val input = listOf(directions)
            HexEd().part2(input) shouldBe distance
        }
    }
})
