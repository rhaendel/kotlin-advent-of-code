package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
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

    "obstacles are not part of the shortest path" {
        SimpleCharGrid(listOf("0#2", "3#5", "678"), '#').shortestPaths(
            Coordinates(0, 0),
            Coordinates(0, 2)
        ) shouldBe listOf(
            ShortestPath(
                listOf(
                    Coordinates(0, 0), Coordinates(1, 0), Coordinates(2, 0),
                    Coordinates(2, 1), Coordinates(2, 2), Coordinates(1, 2), Coordinates(0, 2)
                ), 6
            ),
        )
    }

    "customized obstacles are not part of the shortest path" {
        val grid = SimpleCharGrid(listOf("0X2", "3#5", "678"), '#')
        grid.shortestPaths(
            start = Coordinates(0, 0),
            goal = Coordinates(0, 2),
            neighbourPredicate = { grid.getAt(it) !in listOf('X', '#') }
        ) shouldBe listOf(
            ShortestPath(
                listOf(
                    Coordinates(0, 0), Coordinates(1, 0), Coordinates(2, 0),
                    Coordinates(2, 1), Coordinates(2, 2), Coordinates(1, 2), Coordinates(0, 2)
                ), 6
            ),
        )
    }

    "cluster regions of same char with a single region" {
        val input = """
            xx
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(Coordinates(0, 0), Coordinates(0, 1)),
        )
    }

    "cluster regions of same char with two regions" {
        val input = """
            ..xx
            ..xx
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(Coordinates(0, 0), Coordinates(0, 1), Coordinates(1, 1), Coordinates(1, 0)),
            listOf(Coordinates(0, 2), Coordinates(0, 3), Coordinates(1, 3), Coordinates(1, 2)),
        )
    }

    "cluster regions of same char with four regions" {
        val input = """
            ..xx
            oo__
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(Coordinates(0, 0), Coordinates(0, 1)),
            listOf(Coordinates(0, 2), Coordinates(0, 3)),
            listOf(Coordinates(1, 0), Coordinates(1, 1)),
            listOf(Coordinates(1, 2), Coordinates(1, 3)),
        )
    }

    "cluster one region of a single, specified char" {
        val input = """
            ..x..
            .xxx.
            ..x..
        """.asList()
        SimpleCharGrid(input).clusterRegions('x') shouldBe listOf(
            listOf(
                Coordinates(0, 2), Coordinates(1, 2), Coordinates(1, 3),
                Coordinates(2, 2), Coordinates(1, 1)
            ),
        )
    }

    "cluster regions with no region matching the specified char" {
        val input = """
            ..x..
            .xxx.
            ..x..
        """.asList()
        SimpleCharGrid(input).clusterRegions('o') shouldBe emptyList()
    }
})
