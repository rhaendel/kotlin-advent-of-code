package de.ronny_h.aoc.extensions.grids

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.grids.Direction.*
import de.ronny_h.aoc.extensions.grids.Turn.*
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

val CoordinatesTest by testSuite {

    testSuite(
        "Coordinates are added",
        listOf(
            Triple(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            Triple(Coordinates(0, 0), Coordinates(1, 1), Coordinates(1, 1)),
            Triple(Coordinates(2, 1), Coordinates(4, 3), Coordinates(6, 4)),
        )
    ) { (first, second, result) ->
        first + second shouldBe result
    }

    testSuite(
        "Coordinates are subtracted",
        listOf(
            Triple(Coordinates(1, 1), Coordinates(0, 0), Coordinates(1, 1)),
            Triple(Coordinates(0, 0), Coordinates(1, 1), Coordinates(-1, -1)),
            Triple(Coordinates(5, 3), Coordinates(1, 2), Coordinates(4, 1)),
        )
    ) { (first, second, result) ->
        first - second shouldBe result
    }

    testSuite(
        "Multiplication with a scalar",
        listOf(
            Triple(0, Coordinates(7, 5), Coordinates(0, 0)),
            Triple(7, Coordinates(0, 0), Coordinates(0, 0)),
            Triple(3, Coordinates(7, 5), Coordinates(21, 15)),
            Triple(-3, Coordinates(7, 5), Coordinates(-21, -15)),
        )
    ) { (scalar, coordinates, result) ->
        scalar * coordinates shouldBe result
        coordinates * scalar shouldBe result
    }

    test("Coordinates of ZERO") {
        Coordinates.ZERO shouldBe Coordinates(0, 0)
    }

    testSuite(
        "Add a direction",
        listOf(
            Triple(Coordinates(5, 5), NORTH, Coordinates(5, 4)),
            Triple(Coordinates(5, 5), SOUTH, Coordinates(5, 6)),
            Triple(Coordinates(5, 5), EAST, Coordinates(6, 5)),
            Triple(Coordinates(5, 5), WEST, Coordinates(4, 5)),
        )
    ) { (coordinates, direction, result) ->
        coordinates + direction shouldBe result
    }

    test("Neighbours") {
        Coordinates(5, 5).neighbours() shouldContainAll listOf(
            Coordinates(5, 4),
            Coordinates(5, 6),
            Coordinates(4, 5),
            Coordinates(6, 5),
        )
    }

    test("Neighbours including diagonals") {
        Coordinates(5, 5).neighboursIncludingDiagonals() shouldContainAll listOf(
            Coordinates(5, 4),
            Coordinates(5, 6),
            Coordinates(4, 5),
            Coordinates(6, 5),
            Coordinates(6, 4),
            Coordinates(6, 6),
            Coordinates(4, 6),
            Coordinates(4, 4),
        )
    }

    test("Directed neighbours") {
        Coordinates(5, 5).directedNeighbours() shouldContainAll listOf(
            NORTH to Coordinates(5, 4),
            SOUTH to Coordinates(5, 6),
            EAST to Coordinates(6, 5),
            WEST to Coordinates(4, 5),
        )
    }

    testSuite(
        "Coordinates are comparable and can be sorted",
        mapOf(
            listOf(Coordinates.ZERO) to listOf(Coordinates.ZERO),
            listOf(Coordinates(0, 1), Coordinates.ZERO) to listOf(Coordinates.ZERO, Coordinates(0, 1)),
            listOf(Coordinates(1, 1), Coordinates(0, 1), Coordinates.ZERO) to
                    listOf(Coordinates.ZERO, Coordinates(0, 1), Coordinates(1, 1))
        ),
    ) { list, sorted ->
        list.sorted() shouldBe sorted
    }

    test("Direction turnRight() turns right") {
        NORTH.turnRight() shouldBe EAST
        EAST.turnRight() shouldBe SOUTH
        SOUTH.turnRight() shouldBe WEST
        WEST.turnRight() shouldBe NORTH
    }

    test("Direction turnLeft() turns left") {
        NORTH.turnLeft() shouldBe WEST
        EAST.turnLeft() shouldBe NORTH
        SOUTH.turnLeft() shouldBe EAST
        WEST.turnLeft() shouldBe SOUTH
    }

    test("Direction reverse() does a u-turn") {
        NORTH.reverse() shouldBe SOUTH
        EAST.reverse() shouldBe WEST
        SOUTH.reverse() shouldBe NORTH
        WEST.reverse() shouldBe EAST
    }

    test("asChar gives a graphical representation") {
        NORTH.asChar() shouldBe '↑'
        EAST.asChar() shouldBe '→'
        SOUTH.asChar() shouldBe '↓'
        WEST.asChar() shouldBe '←'
    }

    test("A Direction's orientation is checked right") {
        NORTH.isVertical() shouldBe true
        NORTH.isHorizontal() shouldBe false
        SOUTH.isVertical() shouldBe true
        SOUTH.isHorizontal() shouldBe false

        EAST.isVertical() shouldBe false
        EAST.isHorizontal() shouldBe true
        WEST.isVertical() shouldBe false
        WEST.isHorizontal() shouldBe true
    }

    test("Opposite directions") {
        NORTH.isOpposite(SOUTH) shouldBe true
        SOUTH.isOpposite(NORTH) shouldBe true
        EAST.isOpposite(WEST) shouldBe true
        WEST.isOpposite(EAST) shouldBe true

        NORTH.isOpposite(EAST) shouldBe false
        SOUTH.isOpposite(EAST) shouldBe false
        EAST.isOpposite(SOUTH) shouldBe false
        WEST.isOpposite(SOUTH) shouldBe false
    }

    test("Difference between directions") {
        NORTH - NORTH shouldBe 0
        NORTH - EAST shouldBe 1
        NORTH - SOUTH shouldBe 2
        NORTH - WEST shouldBe 1
    }

    test("toString returns the abbreviation letter") {
        NORTH.toString() shouldBe "N"
        SOUTH.toString() shouldBe "S"
        EAST.toString() shouldBe "E"
        WEST.toString() shouldBe "W"
    }

    testSuite(
        "turning unsing Turns",
        listOf(
            Triple(NORTH, STRAIGHT, NORTH),
            Triple(NORTH, LEFT, WEST),
            Triple(NORTH, RIGHT, EAST),
            Triple(SOUTH, STRAIGHT, SOUTH),
            Triple(SOUTH, LEFT, EAST),
            Triple(SOUTH, RIGHT, WEST),
            Triple(EAST, STRAIGHT, EAST),
            Triple(EAST, LEFT, NORTH),
            Triple(EAST, RIGHT, SOUTH),
            Triple(WEST, STRAIGHT, WEST),
            Triple(WEST, LEFT, SOUTH),
            Triple(WEST, RIGHT, NORTH),
        )
    ) { (direction, turn, newDirection) ->
        direction.turn(turn) shouldBe newDirection
    }
}
