package de.ronny_h.aoc.extensions.grids

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class CoordinatesTest : StringSpec({

    "Coordinates are added" {
        forAll(
            row(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            row(Coordinates(0, 0), Coordinates(1, 1), Coordinates(1, 1)),
            row(Coordinates(1, 2), Coordinates(3, 4), Coordinates(4, 6)),
        ) { first, second, result ->
            first + second shouldBe result
        }
    }

    "Coordinates are subtracted" {
        forAll(
            row(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            row(Coordinates(0, 0), Coordinates(1, 1), Coordinates(-1, -1)),
            row(Coordinates(3, 5), Coordinates(2, 1), Coordinates(1, 4)),
        ) { first, second, result ->
            first - second shouldBe result
        }
    }

    "Multiplication with a scalar" {
        forAll(
            row(0, Coordinates(5, 7), Coordinates(0, 0)),
            row(7, Coordinates(0, 0), Coordinates(0, 0)),
            row(3, Coordinates(5, 7), Coordinates(15, 21)),
            row(-3, Coordinates(5, 7), Coordinates(-15, -21)),
        ) { scalar, coordinates, result ->
            scalar * coordinates shouldBe result
            coordinates * scalar shouldBe result
        }
    }

    "Coordinates of ZERO" {
        Coordinates.ZERO shouldBe Coordinates(0, 0)
    }

    "Add a direction" {
        forAll(
            row(Coordinates(5, 5), Direction.NORTH, Coordinates(4, 5)),
            row(Coordinates(5, 5), Direction.SOUTH, Coordinates(6, 5)),
            row(Coordinates(5, 5), Direction.EAST, Coordinates(5, 6)),
            row(Coordinates(5, 5), Direction.WEST, Coordinates(5, 4)),
        ) { coordinates, direction, result ->
            coordinates + direction shouldBe result
        }
    }

    "Neighbours" {
        Coordinates(5, 5).neighbours() shouldContainAll listOf(
            Coordinates(4, 5),
            Coordinates(6, 5),
            Coordinates(5, 4),
            Coordinates(5, 6),
        )
    }

    "Neighbours including diagonals" {
        Coordinates(5, 5).neighboursIncludingDiagonals() shouldContainAll listOf(
            Coordinates(4, 5),
            Coordinates(6, 5),
            Coordinates(5, 4),
            Coordinates(5, 6),
            Coordinates(4, 6),
            Coordinates(6, 6),
            Coordinates(6, 4),
            Coordinates(4, 4),
        )
    }

    "Directed neighbours" {
        Coordinates(5, 5).directedNeighbours() shouldContainAll listOf(
            Direction.NORTH to Coordinates(4, 5),
            Direction.SOUTH to Coordinates(6, 5),
            Direction.EAST to Coordinates(5, 6),
            Direction.WEST to Coordinates(5, 4),
        )
    }

    "Direction turnRight() turns right" {
        Direction.NORTH.turnRight() shouldBe Direction.EAST
        Direction.EAST.turnRight() shouldBe Direction.SOUTH
        Direction.SOUTH.turnRight() shouldBe Direction.WEST
        Direction.WEST.turnRight() shouldBe Direction.NORTH
    }

    "Direction turnLeft() turns left" {
        Direction.NORTH.turnLeft() shouldBe Direction.WEST
        Direction.EAST.turnLeft() shouldBe Direction.NORTH
        Direction.SOUTH.turnLeft() shouldBe Direction.EAST
        Direction.WEST.turnLeft() shouldBe Direction.SOUTH
    }

    "asChar gives a graphical representation" {
        Direction.NORTH.asChar() shouldBe '↑'
        Direction.EAST.asChar() shouldBe '→'
        Direction.SOUTH.asChar() shouldBe '↓'
        Direction.WEST.asChar() shouldBe '←'
    }

    "A Direction's orientation is checked right" {
        Direction.NORTH.isVertical() shouldBe true
        Direction.NORTH.isHorizontal() shouldBe false
        Direction.SOUTH.isVertical() shouldBe true
        Direction.SOUTH.isHorizontal() shouldBe false

        Direction.EAST.isVertical() shouldBe false
        Direction.EAST.isHorizontal() shouldBe true
        Direction.WEST.isVertical() shouldBe false
        Direction.WEST.isHorizontal() shouldBe true
    }

    "Opposite directions" {
        Direction.NORTH.isOpposite(Direction.SOUTH) shouldBe true
        Direction.SOUTH.isOpposite(Direction.NORTH) shouldBe true
        Direction.EAST.isOpposite(Direction.WEST) shouldBe true
        Direction.WEST.isOpposite(Direction.EAST) shouldBe true

        Direction.NORTH.isOpposite(Direction.EAST) shouldBe false
        Direction.SOUTH.isOpposite(Direction.EAST) shouldBe false
        Direction.EAST.isOpposite(Direction.SOUTH) shouldBe false
        Direction.WEST.isOpposite(Direction.SOUTH) shouldBe false
    }

    "Difference between directions" {
        Direction.NORTH - Direction.NORTH shouldBe 0
        Direction.NORTH - Direction.EAST shouldBe 1
        Direction.NORTH - Direction.SOUTH shouldBe 2
        Direction.NORTH - Direction.WEST shouldBe 1
    }

    "toString returns the abbreviation letter" {
        Direction.NORTH.toString() shouldBe "N"
        Direction.SOUTH.toString() shouldBe "S"
        Direction.EAST.toString() shouldBe "E"
        Direction.WEST.toString() shouldBe "W"
    }
})
