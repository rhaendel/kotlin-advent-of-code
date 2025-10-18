package de.ronny_h.aoc.year2018.day10

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TheStarsAlignTest : StringSpec({

    val input = """
        position=< 9,  1> velocity=< 0,  2>
        position=< 7,  0> velocity=<-1,  0>
        position=< 3, -2> velocity=<-1,  1>
        position=< 6, 10> velocity=<-2, -1>
        position=< 2, -4> velocity=< 2,  2>
        position=<-6, 10> velocity=< 2, -2>
        position=< 1,  8> velocity=< 1, -1>
        position=< 1,  7> velocity=< 1,  0>
        position=<-3, 11> velocity=< 1, -2>
        position=< 7,  6> velocity=<-1, -1>
        position=<-2,  3> velocity=< 1,  0>
        position=<-4,  3> velocity=< 2,  0>
        position=<10, -3> velocity=<-1,  1>
        position=< 5, 11> velocity=< 1, -2>
        position=< 4,  7> velocity=< 0, -1>
        position=< 8, -2> velocity=< 0,  1>
        position=<15,  0> velocity=<-2,  0>
        position=< 1,  6> velocity=< 1,  0>
        position=< 8,  9> velocity=< 0, -1>
        position=< 3,  3> velocity=<-1,  1>
        position=< 0,  5> velocity=< 0, -1>
        position=<-2,  2> velocity=< 2,  0>
        position=< 5, -2> velocity=< 1,  2>
        position=< 1,  4> velocity=< 2,  1>
        position=<-2,  7> velocity=< 2, -2>
        position=< 3,  6> velocity=<-1, -1>
        position=< 5,  0> velocity=< 1,  0>
        position=<-6,  0> velocity=< 2,  0>
        position=< 5,  9> velocity=< 1, -2>
        position=<14,  7> velocity=<-2,  0>
        position=<-3,  6> velocity=< 2, -1>
    """.asList()

    val smallInput = """
            position=< 9,  1> velocity=< 0,  2>
            position=< 7,  0> velocity=<-1,  0>
        """.asList()

    "Input can be parsed" {
        StarAlignmentGrid.of(smallInput).stars shouldBe listOf(
            Star(Coordinates(1, 9), Coordinates(2, 0)),
            Star(Coordinates(0, 7), Coordinates(0, -1)),
        )
    }

    "Star movements can be calculated" {
        StarAlignmentGrid.of(smallInput).move().stars shouldBe listOf(
            Star(Coordinates(3, 9), Coordinates(2, 0)),
            Star(Coordinates(0, 6), Coordinates(0, -1)),
        )
    }

    "part 1: For the sample input, a message can be seen after 3 iterations" {
        TheStarsAlign().part1(input) shouldBe 3
    }

    "part 2: For the sample input, the Elves have to wait 3 seconds" {
        TheStarsAlign().part2(input) shouldBe 3
    }
})
