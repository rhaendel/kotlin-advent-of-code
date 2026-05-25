package de.ronny_h.aoc.year2018.day13

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val MineCartMadnessTest by testSuite {

    test("part 1: the location of the first crash") {
        val input = """
        /->-\        
        |   |  /----\
        | /-+--+-\  |
        | | |  | v  |
        \-+-/  \-+--/
          \------/   
    """.asList()

        MineCartMadness().part1(input) shouldBe "7,3"
    }

    test("part 2: the location of the last cart") {
        val input = """
            />-<\  
            |   |  
            | /<+-\
            | | | v
            \>+</ |
              |   ^
              \<->/
          """.asList()
        MineCartMadness().part2(input) shouldBe "6,4"
    }
}
