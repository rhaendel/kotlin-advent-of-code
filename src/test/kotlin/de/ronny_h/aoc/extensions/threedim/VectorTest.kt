package de.ronny_h.aoc.extensions.threedim

import de.ronny_h.aoc.extensions.threedim.Vector.Companion.ZERO
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class VectorTest : StringSpec({
    "vectors can be added" {
        forAll(
            row(Vector(0, 0, 0), Vector(0, 0, 0), Vector(0, 0, 0)),
            row(Vector(10, 20, 30), Vector(1, 2, 3), Vector(11, 22, 33)),
            row(Vector(10, 20, 30), Vector(-1, -2, -3), Vector(9, 18, 27)),
        ) { a, b, sum ->
            a + b shouldBe sum
        }
    }

    "vectors can be subtracted" {
        forAll(
            row(Vector(0, 0, 0), Vector(0, 0, 0), Vector(0, 0, 0)),
            row(Vector(10, 20, 30), Vector(1, 2, 3), Vector(9, 18, 27)),
            row(Vector(10, 20, 30), Vector(-1, -2, -3), Vector(11, 22, 33)),
        ) { a, b, difference ->
            a - b shouldBe difference
        }
    }

    "the taxi distance of vectors" {
        forAll(
            row(Vector(0, 0, 0), Vector(0, 0, 0), 0),
            row(Vector(10, 20, 30), Vector(11, 22, 33), 6),
            row(Vector(10, 20, 30), Vector(9, 18, 27), 6),
        ) { a, b, distance ->
            a taxiDistanceTo b shouldBe distance
        }
    }

    "the absolute of a vector" {
        forAll(
            row(Vector(0, 0, 0), 0.0),
            row(Vector(3, 3, 3), 3.0),
        ) { v, absolute ->
            v.abs() shouldBe absolute
        }
    }

    "scalar multiplication with an Int" {
        forAll(
            row(0, Vector(1, 2, 3), ZERO),
            row(1, Vector(1, 2, 3), Vector(1, 2, 3)),
            row(10, Vector(1, 2, 3), Vector(10, 20, 30)),
        ) { a, v, product ->
            a * v shouldBe product
        }
    }

    "scalar multiplication with a Long" {
        forAll(
            row(0L, Vector(1, 2, 3), ZERO),
            row(1L, Vector(1, 2, 3), Vector(1, 2, 3)),
            row(10L, Vector(1, 2, 3), Vector(10, 20, 30)),
        ) { a, v, product ->
            a * v shouldBe product
        }
    }
})
