package de.ronny_h.aoc.extensions.graphs

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TravelingSalesmanTest : StringSpec({

    "TSP for a complete round-trip on a graph with 4 nodes" {
        val adj: Array<IntArray> = arrayOf(
            intArrayOf(0, 10, 15, 20),
            intArrayOf(10, 0, 35, 25),
            intArrayOf(15, 35, 0, 30),
            intArrayOf(20, 25, 30, 0)
        )

        val shortestPath = TravelingSalesman(adj).calculateShortestRoundTrip()

        shortestPath.length shouldBe 80
        shortestPath.path shouldBe listOf(0, 1, 3, 2, 0)
    }

    "convert a map of a single distance to an adjacency matrix" {
        val distances = listOf(Edge("A", "B", 1))
        val expectedAdjacencyMatrix = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(MAX_WEIGHT, 0),
        )
        distances.toAdjacencyMatrix() shouldBe (distances to AdjacencyMatrix(listOf("A", "B"), expectedAdjacencyMatrix))
    }

    "convert a map of multiple distances to an adjacency matrix" {
        val distances = listOf(Edge("A", "B", 1), Edge("B", "C", 2))
        val expectedAdjacencyMatrix = arrayOf(
            intArrayOf(0, 1, MAX_WEIGHT),
            intArrayOf(MAX_WEIGHT, 0, 2),
            intArrayOf(MAX_WEIGHT, MAX_WEIGHT, 0),
        )
        distances.toAdjacencyMatrix() shouldBe (distances to AdjacencyMatrix(
            listOf("A", "B", "C"),
            expectedAdjacencyMatrix
        ))
    }

    "convert a map of multiple distances to an adjacency matrix symmetrically" {
        val distances = listOf(Edge("A", "B", 1), Edge("B", "C", 2))
        val expectedAdjacencyMatrix = arrayOf(
            intArrayOf(0, 1, MAX_WEIGHT),
            intArrayOf(1, 0, 2),
            intArrayOf(MAX_WEIGHT, 2, 0),
        )
        distances.toAdjacencyMatrix(symmetrical = true) shouldBe (distances to AdjacencyMatrix(
            listOf("A", "B", "C"),
            expectedAdjacencyMatrix
        ))
    }

    "when providing a nullNode, synthetic edges of length 0 are added" {
        val distances = listOf(Edge("A", "B", 1), Edge("B", "C", 2))

        val syntheticEdges = listOf(
            Edge("ThisNodeIsArtificial", "A", 0),
            Edge("ThisNodeIsArtificial", "B", 0),
            Edge("ThisNodeIsArtificial", "C", 0),
        )
        val expectedAdjacencyMatrix = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 1, MAX_WEIGHT),
            intArrayOf(0, 1, 0, 2),
            intArrayOf(0, MAX_WEIGHT, 2, 0),
        )
        distances.toAdjacencyMatrix(
            symmetrical = true,
            "ThisNodeIsArtificial"
        ) shouldBe (syntheticEdges + distances to AdjacencyMatrix(
            listOf("ThisNodeIsArtificial", "A", "B", "C"),
            expectedAdjacencyMatrix
        ))
    }
})
