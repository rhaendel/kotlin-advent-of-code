package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import de.ronny_h.aoc.extensions.Coordinates as C

class SimpleCharGridTest : StringSpec({

    "in a grid with a unique shortest path that path is found" {
        SimpleCharGrid(listOf("12", "34")).shortestPaths(C(0, 0), C(0, 1)) shouldBe
                listOf(ShortestPath(listOf(C(0, 0), C(0, 1)), 1))
    }

    "in a small grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("12", "34")).shortestPaths(C(0, 0), C(1, 1)) shouldBe
                listOf(
                    ShortestPath(listOf(C(0, 0), C(0, 1), C(1, 1)), 2),
                    ShortestPath(listOf(C(0, 0), C(1, 0), C(1, 1)), 2),
                )
    }

    "in a slightly larger grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("123", "456", "789")).shortestPaths(C(0, 0), C(2, 2)) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(listOf(C(0, 0), C(0, 1), C(0, 2), C(1, 2), C(2, 2)), 4),
                    ShortestPath(listOf(C(0, 0), C(0, 1), C(1, 1), C(1, 2), C(2, 2)), 4),
                    ShortestPath(listOf(C(0, 0), C(0, 1), C(1, 1), C(2, 1), C(2, 2)), 4),
                    ShortestPath(listOf(C(0, 0), C(1, 0), C(1, 1), C(1, 2), C(2, 2)), 4),
                    ShortestPath(listOf(C(0, 0), C(1, 0), C(1, 1), C(2, 1), C(2, 2)), 4),
                    ShortestPath(listOf(C(0, 0), C(1, 0), C(2, 0), C(2, 1), C(2, 2)), 4),
                )
    }

    "in a non-rectangular grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("#12", "345"), '#').shortestPaths(C(0, 1), C(1, 2)) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(listOf(C(0, 1), C(0, 2), C(1, 2)), 2),
                    ShortestPath(listOf(C(0, 1), C(1, 1), C(1, 2)), 2),
                )
    }
})
