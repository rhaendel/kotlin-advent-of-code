package de.ronny_h.extensions

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

typealias At = Coordinates

data class Node(val position: At, val id: Long = nextId) {
    companion object {
        private var nextId = 0L
    }

    init {
        nextId++
    }
}


class ShortestPathTest : StringSpec({

    "Node ids are generated consecutively" {
        Node(At(0, 0)).id shouldBe Node(At(0, 0)).id - 1
    }

    "The manhattan distance of nodes with identical coordinates is 0" {
        At(7, 11) taxiDistanceTo At(7, 11) shouldBe 0
    }

    "The manhattan distance of nodes in the same row equals the difference of columns" {
        At(7, 0) taxiDistanceTo At(7, 11) shouldBe 11
    }

    "The manhattan distance of nodes in the same column equals the difference of rows" {
        At(0, 11) taxiDistanceTo At(7, 11) shouldBe 7
    }

    "The manhattan distance of nodes is the sum of the differences of rows and columns" {
        At(0, 0) taxiDistanceTo At(7, 11) shouldBe (7 + 11)
    }

    "The shortest path between 2 nodes in a Graph containing only one edge between them is that edge" {
        val start = Node(At(0, 0))
        val goal = Node(At(1, 1))
        val neighbours: (Node) -> List<Node> = { n ->
            when (n) {
                start -> listOf(goal)
                goal -> listOf(start)
                else -> error("No neighbor defined for node $n")
            }
        }

        val d: (Node, Node) -> Int = { _, _ -> 1 }
        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        aStar(start, goal, neighbours, d, h) shouldBe ShortestPath(listOf(start, goal), 1)
    }

    "With 2 different nodes between start and goal, the shorter path is taken" {
        // start -> a -> goal
        //      \       /
        //       -> b ->
        val start = Node(At(0, 0))
        val goal = Node(At(0, 10))
        val a = Node(At(0, 5))
        val b = Node(At(5, 5))
        val neighbours = mapOf(
            start to listOf(a, b),
            a to listOf(goal),
            b to listOf(goal),
        )
        val distances = mapOf(
            (start to a) to 5,
            (start to b) to 7,
            (a to goal) to 5,
            (b to goal) to 7,
        )

        val d: (Node, Node) -> Int = { m, n -> distances.getValue(m to n) }
        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        aStar(start, goal, { n -> neighbours.getValue(n) }, d, h) shouldBe ShortestPath(listOf(start, a, goal), 10)
    }

    "When direct distance from start to goal is longer, the path through a third node is taken" {
        // start -------> goal
        //      \       /
        //       -> a ->
        val start = Node(At(0, 0))
        val goal = Node(At(0, 10))
        val a = Node(At(5, 10))
        val neighbours = mapOf(
            start to listOf(goal, a),
            a to listOf(goal),
        )
        val distances = mapOf(
            (start to goal) to 10,
            (start to a) to 3,
            (a to goal) to 6,
        )

        val d: (Node, Node) -> Int = { m, n -> distances.getValue(m to n) }
        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        aStar(start, goal, { n -> neighbours.getValue(n) }, d, h) shouldBe ShortestPath(listOf(start, a, goal), 9)
    }

    "The shortest path in a not directed graph is found" {
        // start -------- goal
        //      \       /
        //       -- a --
        val start = Node(At(0, 0))
        val goal = Node(At(0, 10))
        val a = Node(At(5, 10))
        val neighbours = mapOf(
            start to listOf(goal, a),
            a to listOf(goal, start),
            goal to listOf(start, a),
        )
        val distances = mapOf(
            (start to goal) to 10,
            (goal to start) to 10,
            (start to a) to 3,
            (a to start) to 3,
            (a to goal) to 6,
            (goal to a) to 6,
        )

        val d: (Node, Node) -> Int = { m, n -> distances.getValue(m to n) }
        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        aStar(start, goal, { n -> neighbours.getValue(n) }, d, h) shouldBe ShortestPath(listOf(start, a, goal), 9)
    }

    "Distances of 0 can be taken and nodes with same coordinates don't cause problems" {
        // start ---------------> goal
        //      \              /
        //       -> a -0-> b ->
        val start = Node(At(0, 0))
        val goal = Node(At(0, 10))
        val a = Node(At(5, 10))
        val b = Node(At(5, 10))
        val neighbours = mapOf(
            start to listOf(goal, a),
            a to listOf(b),
            b to listOf(goal),
        )
        val distances = mapOf(
            (start to goal) to 10,
            (start to a) to 3,
            (a to b) to 0,
            (b to goal) to 6,
        )

        val d: (Node, Node) -> Int = { m, n -> distances.getValue(m to n) }
        val h: (Node) -> Int = { n -> n.position taxiDistanceTo goal.position }

        aStar(start, goal, { n -> neighbours.getValue(n) }, d, h) shouldBe ShortestPath(listOf(start, a, b, goal), 9)
    }
})
