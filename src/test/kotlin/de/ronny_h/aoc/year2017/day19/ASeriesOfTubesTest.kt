package de.ronny_h.aoc.year2017.day19

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.matchers.shouldBe

val ASeriesOfTubesTest by testSuite {

    val input = """
        #     |          
        #     |  +--+    
        #     A  |  C    
        # F---|----E|--+ 
        #     |  |  |  D 
        #     +B-+  +--+ 
    """.trimMargin("#").asList()

    test("input can be parsed") {
        RoutingDiagram(input).findStartCoordinates() shouldBe Coordinates(4, 0)
    }

    test("part 1: the letters on the path from the beginning to the end") {
        ASeriesOfTubes().part1(input) shouldBe "ABCDEF"
    }

    test("part 2: the package takes a total of 38 steps") {
        ASeriesOfTubes().part2(input) shouldBe "38"
    }
}
