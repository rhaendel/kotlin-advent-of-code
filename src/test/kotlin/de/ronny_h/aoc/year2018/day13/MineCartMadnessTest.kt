package de.ronny_h.aoc.year2018.day13

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MineCartMadnessTest : StringSpec({

    "part 1: the location of the first crash" {
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

    "part 2: the location of the last cart" {
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
})
