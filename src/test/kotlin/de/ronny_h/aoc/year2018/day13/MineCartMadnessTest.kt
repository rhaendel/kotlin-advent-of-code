package de.ronny_h.aoc.year2018.day13

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MineCartMadnessTest : StringSpec({

    val input = """
        /->-\        
        |   |  /----\
        | /-+--+-\  |
        | | |  | v  |
        \-+-/  \-+--/
          \------/   
    """.asList()

    "part 1: the location of the first crash" {
        MineCartMadness().part1(input) shouldBe "7,3"
    }

    "part 2" {
        val input = listOf("")
        MineCartMadness().part2(input) shouldBe ""
    }
})
