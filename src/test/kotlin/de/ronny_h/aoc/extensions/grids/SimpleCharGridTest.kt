package de.ronny_h.aoc.extensions.grids

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.graphs.shortestpath.ShortestPath
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import de.ronny_h.aoc.extensions.grids.Coordinates as C

class SimpleCharGridTest : StringSpec({

    "in a grid with a unique shortest path that path is found" {
        val grid = SimpleCharGrid(listOf("12", "34"))
        val shortestPaths = listOf(ShortestPath(listOf(C(0, 0), C(1, 0)), 1))
        grid.shortestPaths(C(0, 0), C(1, 0)) shouldBe shortestPaths
        grid.shortestPaths(
            start = C(0, 0),
            goals = listOf(C(1, 0)),
            isObstacle = { it == grid.nullElement },
        ) shouldBe shortestPaths
    }

    "in a small grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("12", "34")).shortestPaths(C(0, 0), C(1, 1)) shouldBe
                listOf(
                    ShortestPath(listOf(C(0, 0), C(1, 0), C(1, 1)), 2),
                    ShortestPath(listOf(C(0, 0), C(0, 1), C(1, 1)), 2),
                )
    }

    "in a slightly larger grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("123", "456", "789")).shortestPaths(
            C(0, 0),
            C(2, 2)
        ) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(1, 0),
                            C(2, 0),
                            C(2, 1),
                            C(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(1, 0),
                            C(1, 1),
                            C(2, 1),
                            C(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(1, 0),
                            C(1, 1),
                            C(1, 2),
                            C(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(0, 1),
                            C(1, 1),
                            C(2, 1),
                            C(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(0, 1),
                            C(1, 1),
                            C(1, 2),
                            C(2, 2)
                        ), 4
                    ),
                    ShortestPath(
                        listOf(
                            C(0, 0),
                            C(0, 1),
                            C(0, 2),
                            C(1, 2),
                            C(2, 2)
                        ), 4
                    ),
                )
    }

    "in a non-rectangular grid with multiple shortest paths all paths are found" {
        SimpleCharGrid(listOf("#12", "345"), '#').shortestPaths(
            C(1, 0),
            C(2, 1)
        ) shouldContainExactlyInAnyOrder
                listOf(
                    ShortestPath(listOf(C(1, 0), C(2, 0), C(2, 1)), 2),
                    ShortestPath(listOf(C(1, 0), C(1, 1), C(2, 1)), 2),
                )
    }

    "obstacles are not part of the shortest path" {
        val grid = SimpleCharGrid(listOf("0#2", "3#5", "678"), '#')
        val expected = listOf(
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(0, 2),
                    C(1, 2), C(2, 2), C(2, 1), C(2, 0)
                ), 6
            ),
        )

        // A Star
        grid.shortestPaths(C(0, 0), C(2, 0)) shouldBe expected

        // Dijkstra
        grid.shortestPaths(C(0, 0), listOf(C(2, 0))) shouldBe expected
    }

    "customized obstacles are not part of the shortest path" {
        val grid = SimpleCharGrid(listOf("0X2", "3#5", "678"), '#')
        val expected = listOf(
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(0, 2),
                    C(1, 2), C(2, 2), C(2, 1), C(2, 0)
                ), 6
            ),
        )

        // A Star
        grid.shortestPaths(
            start = C(0, 0),
            goal = C(2, 0),
            isVisitable = { grid.getAt(it) !in listOf('X', '#') },
        ) shouldBe expected

        // Dijkstra
        grid.shortestPaths(
            start = C(0, 0),
            goals = listOf(C(2, 0)),
            isObstacle = { it in listOf('X', '#') },
        ) shouldBe expected
    }

    "the Dijkstra implementation finds the shortest path to all goals" {
        val input = """
            1##2
            3456
            7##8
        """.asList()
        SimpleCharGrid(input)
            .shortestPaths(
                start = C(0, 0),
                goals = listOf(C(3, 0), C(0, 2), C(3, 2))
            ) shouldBe listOf(
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(1, 1),
                    C(2, 1), C(3, 1), C(3, 0),
                ), 5
            ),
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(0, 2),
                ), 2
            ),
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(1, 1),
                    C(2, 1), C(3, 1), C(3, 2),
                ), 5
            ),
        )
    }

    "the Dijkstra implementation stopAfterMinimalPathsAreFound if that option is set" {
        val input = """
            SoG
            o.o
            G.G
            ..G
        """.asList()
        SimpleCharGrid(input)
            .shortestPaths(
                start = C(0, 0),
                goals = listOf(C(2, 0), C(0, 2), C(2, 2), C(2, 3)),
                stopAfterMinimalPathsAreFound = true,
            ) shouldBe listOf(
            ShortestPath(
                listOf(
                    C(0, 0), C(1, 0), C(2, 0),
                ), 2
            ),
            ShortestPath(
                listOf(
                    C(0, 0), C(0, 1), C(0, 2),
                ), 2
            ),
            // to know when all minimal paths are found, the algorithm has to continue to the next larger one
            ShortestPath(
                listOf(
                    C(0, 0), C(1, 0), C(2, 0), C(2, 1), C(2, 2),
                ), 4
            ),
        )
    }

    "the Dijkstra implementation ignores unreachable goals" {
        val input = """
            1#2
            3#4
            5#6
        """.asList()
        SimpleCharGrid(input)
            .shortestPaths(
                start = C(0, 0),
                goals = listOf(C(2, 0), C(0, 2), C(2, 2))
            ) shouldBe listOf(ShortestPath(listOf(C(0, 0), C(0, 1), C(0, 2)), 2))
    }

    "the Dijkstra implementation takes the 'reading order' as vertex precedence" {
        forAll(
            row(
                """
                        Sooo
                        ...o
                        ...o
                        ...G
                    """,
                C(0, 0), C(3, 3),
                listOf(
                    C(0, 0), C(1, 0), C(2, 0), C(3, 0),
                    C(3, 1), C(3, 2), C(3, 3),
                ),
            ),
            row(
                """
                        .ooG
                        #o..
                        oo..
                        S...
                    """,
                C(0, 3), C(3, 0),
                listOf(
                    C(0, 3), C(0, 2), C(1, 2), C(1, 1),
                    C(1, 0), C(2, 0), C(3, 0),
                )
            ),
        ) { input, start, goal, path ->
            SimpleCharGrid(input.asList())
                .shortestPaths(
                    start = start,
                    goals = listOf(goal)
                ) shouldBe listOf(
                ShortestPath(
                    path, 6
                ),
            )
        }
    }

    "cluster regions of same char with a single region" {
        val input = """
            xx
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(C(0, 0), C(1, 0)),
        )
    }

    "cluster regions of same char with two regions" {
        val input = """
            ..xx
            ..xx
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(C(0, 0), C(1, 0), C(1, 1), C(0, 1)),
            listOf(C(2, 0), C(3, 0), C(3, 1), C(2, 1)),
        )
    }

    "cluster regions of same char with four regions" {
        val input = """
            ..xx
            oo__
        """.asList()
        SimpleCharGrid(input).clusterRegions() shouldBe listOf(
            listOf(C(0, 0), C(1, 0)),
            listOf(C(2, 0), C(3, 0)),
            listOf(C(0, 1), C(1, 1)),
            listOf(C(2, 1), C(3, 1)),
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
                C(2, 0), C(2, 1), C(3, 1),
                C(2, 2), C(1, 1)
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
