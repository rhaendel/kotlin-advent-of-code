package de.ronny_h.aoc.year2017.day12

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DigitalPlumberTest : StringSpec({

    val input = """
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5
    """.asList()

    "part 1: the number of programs in the group that contains program ID 0" {
        DigitalPlumber().part1(input) shouldBe 6
    }

    "part 2: the number of groups in total" {
        DigitalPlumber().part2(input) shouldBe 2
    }
})
