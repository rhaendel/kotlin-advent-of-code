package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.graphs.ShortestPath
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class SimpleCharGridTest : StringSpec({

    "in a grid with a unique shortest path that path is found" {
        SimpleCharGrid(listOf("12", "34")).shortestPaths(Coordinates(0, 0), Coordinates(0, 1)) shouldBe
                listOf(ShortestPath(listOf(Coordinates(0, 0), Coordinates(0, 1)), 1))
    }

    "in a small grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("12", "34")).shortestPaths(Coordinates(0, 0), Coordinates(1, 1)) shouldBe
                listOf(
                    ShortestPath(listOf(Coordinates(0, 0), Coordinates(0, 1), Coordinates(1, 1)), 2),
                    ShortestPath(listOf(Coordinates(0, 0), Coordinates(1, 0), Coordinates(1, 1)), 2),
                )
    }

    "in a slightly larger grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("123", "456", "789")).shortestPaths(
            Coordinates(0, 0),
            Coordinates(2, 2)
        ) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(0, 1),
                            Coordinates(0, 2),
                            Coordinates(1, 2),
                            Coordinates(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(0, 1),
                            Coordinates(1, 1),
                            Coordinates(1, 2),
                            Coordinates(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(0, 1),
                            Coordinates(1, 1),
                            Coordinates(2, 1),
                            Coordinates(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(1, 0),
                            Coordinates(1, 1),
                            Coordinates(1, 2),
                            Coordinates(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(1, 0),
                            Coordinates(1, 1),
                            Coordinates(2, 1),
                            Coordinates(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            Coordinates(0, 0),
                            Coordinates(1, 0),
                            Coordinates(2, 0),
                            Coordinates(2, 1),
                            Coordinates(2, 2)
                        ), 4
                    ),
                )
    }

    "in a non-rectangular grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("#12", "345"), '#').shortestPaths(
            Coordinates(0, 1),
            Coordinates(1, 2)
        ) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(listOf(Coordinates(0, 1), Coordinates(0, 2), Coordinates(1, 2)), 2),
                    ShortestPath(listOf(Coordinates(0, 1), Coordinates(1, 1), Coordinates(1, 2)), 2),
                )
    }
})
