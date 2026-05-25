package de.ronny_h.aoc.extensions.threedim

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.threedim.Vector.Companion.ZERO
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val VectorTest by testSuite {
    testSuite(
        "vectors can be added",
        listOf(
            Triple(Vector(0, 0, 0), Vector(0, 0, 0), Vector(0, 0, 0)),
            Triple(Vector(10, 20, 30), Vector(1, 2, 3), Vector(11, 22, 33)),
            Triple(Vector(10, 20, 30), Vector(-1, -2, -3), Vector(9, 18, 27)),
        ),
    ) { (a, b, sum) ->
        a + b shouldBe sum
    }

    testSuite(
        "vectors can be subtracted",
        listOf(
            Triple(Vector(0, 0, 0), Vector(0, 0, 0), Vector(0, 0, 0)),
            Triple(Vector(10, 20, 30), Vector(1, 2, 3), Vector(9, 18, 27)),
            Triple(Vector(10, 20, 30), Vector(-1, -2, -3), Vector(11, 22, 33)),
        ),
    ) { (a, b, difference) ->
        a - b shouldBe difference
    }

    testSuite(
        "the taxi distance of vectors",
        listOf(
            Triple(Vector(0, 0, 0), Vector(0, 0, 0), 0),
            Triple(Vector(10, 20, 30), Vector(11, 22, 33), 6),
            Triple(Vector(10, 20, 30), Vector(9, 18, 27), 6),
        ),
    ) { (a, b, distance) ->
        a taxiDistanceTo b shouldBe distance
    }

    testSuite(
        "the absolute of a vector",
        mapOf(
            Vector(0, 0, 0) to 0.0,
            Vector(3, 3, 3) to 3.0,
        ),
    ) { v, absolute ->
        v.abs() shouldBe absolute
    }

    testSuite(
        "scalar multiplication with an Int",
        listOf(
            Triple(0, Vector(1, 2, 3), ZERO),
            Triple(1, Vector(1, 2, 3), Vector(1, 2, 3)),
            Triple(10, Vector(1, 2, 3), Vector(10, 20, 30)),
        ),
    ) { (a, v, product) ->
        a * v shouldBe product
    }

    testSuite(
        "scalar multiplication with a Long",
        listOf(
            Triple(0L, Vector(1, 2, 3), ZERO),
            Triple(1L, Vector(1, 2, 3), Vector(1, 2, 3)),
            Triple(10L, Vector(1, 2, 3), Vector(10, 20, 30)),
        ),
    ) { (a, v, product) ->
        a * v shouldBe product
    }
}
