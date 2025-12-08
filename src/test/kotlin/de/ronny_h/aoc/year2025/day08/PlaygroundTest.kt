package de.ronny_h.aoc.year2025.day08

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.threedim.Vector
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PlaygroundTest : StringSpec({

    val input = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
    """.asList()

    "input can be parsed" {
        """
            162,817,812
            57,618,57
            906,360,560
        """.asList()
            .parseJunctionBoxes() shouldBe listOf(
            Vector(162, 817, 812),
            Vector(57, 618, 57),
            Vector(906, 360, 560),
        )
    }

    "pairwiseDistances" {
        listOf(
            Vector(0, 0, 0),
            Vector(1, 0, 0),
            Vector(2, 0, 0),
        ).pairwiseDistances() shouldBe listOf(
            BoxDistance(
                Vector(0, 0, 0) to
                        Vector(1, 0, 0), 1.0
            ),
            BoxDistance(
                Vector(0, 0, 0) to
                        Vector(2, 0, 0), 2.0
            ),
            BoxDistance(
                Vector(1, 0, 0) to
                        Vector(2, 0, 0), 1.0
            ),
        )
    }

    "part 1" {
        JunctionBoxConnector(input).connectShortest(10) shouldBe 40
    }

    "part 2" {
        Playground().part2(input) shouldBe 25272
    }
})
