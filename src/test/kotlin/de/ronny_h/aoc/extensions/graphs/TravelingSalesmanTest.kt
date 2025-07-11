package de.ronny_h.aoc.extensions.graphs

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TravelingSalesmanTest : StringSpec({

    "TSP for a graph with 4 nodes given as adjacency matrix" {
        val adj: Array<IntArray> = arrayOf(
            intArrayOf(0, 10, 15, 20),
            intArrayOf(10, 0, 35, 25),
            intArrayOf(15, 35, 0, 30),
            intArrayOf(20, 25, 30, 0)
        )

        val shortestPath = TravelingSalesman(adj).tsp()

        shortestPath.length shouldBe 80
        shortestPath.path shouldBe listOf(0, 1, 3, 2, 0)
    }

})
