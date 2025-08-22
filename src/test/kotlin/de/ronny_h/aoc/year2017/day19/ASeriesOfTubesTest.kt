package de.ronny_h.aoc.year2017.day19

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ASeriesOfTubesTest : StringSpec({

    val input = """
        #     |          
        #     |  +--+    
        #     A  |  C    
        # F---|----E|--+ 
        #     |  |  |  D 
        #     +B-+  +--+ 
    """.trimMargin("#").asList()

    "input can be parsed" {
        RoutingDiagram(input).findStartCoordinates() shouldBe Coordinates(0, 4)
    }

    "part 1: the letters on the path from the beginning to the end" {
        ASeriesOfTubes().part1(input) shouldBe "ABCDEF"
    }

    "part 2: the package takes a total of 38 steps" {
        ASeriesOfTubes().part2(input) shouldBe "38"
    }
})
