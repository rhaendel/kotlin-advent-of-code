package de.ronny_h.aoc.year2015.day09

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.graphs.Edge
import de.ronny_h.aoc.extensions.graphs.TravelingSalesman
import de.ronny_h.aoc.extensions.graphs.TravelingSalesmanProblemSolution
import de.ronny_h.aoc.extensions.graphs.toAdjacencyMatrix
import java.io.File

fun main() = AllInASingleNight().run(251, 898)

class AllInASingleNight : AdventOfCode<Int>(2015, 9) {
    override fun part1(input: List<String>) = input.parseEdges().shortestRoundtrip().length

    companion object {
        fun List<String>.parseEdges() = this.map { line ->
            val (locations, distance) = line.split(" = ")
            val (from, to) = locations.split(" to ")
            Edge(from, to, distance.toInt())
        }
    }

    override fun part2(input: List<String>): Int {
        val distances = input.parseEdges()
        val maxWeight = distances.maxOf { it.weight } + 1
        val invertedDistances = distances.map { it.copy(weight = maxWeight - it.weight) }
        val shortestRoundtrip = invertedDistances.shortestRoundtrip()
        return (shortestRoundtrip.path.size - 3) * maxWeight - shortestRoundtrip.length
    }

    private fun List<Edge<String>>.shortestRoundtrip(): TravelingSalesmanProblemSolution {
        val (edges, adjacencyMatrix) = toAdjacencyMatrix(
            symmetrical = true,
            nullNode = "SyntheticStart"
        )
        val shortestRoundtrip = TravelingSalesman(adjacencyMatrix.content).calculateShortestRoundTrip()
        println("nodes: ${adjacencyMatrix.headers}")
        println("path: ${shortestRoundtrip.path}")
        // writeToDotFile("part2", edges, adjacencyMatrix.headers, shortestRoundtrip.path)
        return shortestRoundtrip
    }

    private fun writeToDotFile(name: String, distances: List<Edge<String>>, headers: List<String>, path: List<Int>) {
        val indexOf = headers.mapIndexed { i, name -> name to i }.toMap()

        File("${sourcePath()}/$name.dot").printWriter().use { out ->
            out.println("strict graph {")
            out.println("  edge [fontname=Arial fontsize=9]")
            out.println("  node [fontname=Arial]")

            headers.forEachIndexed { i, name ->
                out.println("  $i [label=\"$i $name\"]")
            }
            distances.forEach {
                val color = if (it.weight == 0) " color=grey48 fontcolor=grey48" else ""
                out.println("  ${indexOf[it.from]} -- ${indexOf[it.to]} [label=${it.weight}$color]")
            }
            path.windowed(2) { (from, to) ->
                out.println("  $from -- $to [color=red]")
            }

            out.println("}")
        }
    }

    private fun sourcePath(): String = "src/main/kotlin/de/ronny_h/aoc/year$year/day${paddedDay()}"
}
