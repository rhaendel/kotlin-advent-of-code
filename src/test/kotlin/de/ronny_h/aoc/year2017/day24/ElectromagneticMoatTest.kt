package de.ronny_h.aoc.year2017.day24

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ElectromagneticMoatTest : StringSpec({

    val input = """
        0/2
        2/2
        2/3
        3/4
        3/5
        0/1
        10/1
        9/10
    """.asList()

    "components can be parsed" {
        input.parseComponents() shouldBe listOf(
            Component(0, 2),
            Component(2, 2),
            Component(2, 3),
            Component(3, 4),
            Component(3, 5),
            Component(0, 1),
            Component(10, 1),
            Component(9, 10),
        )
    }

    "part 1: the strongest bridge's strength is 31" {
        ElectromagneticMoat().part1(input) shouldBe 31
    }

    "part 2: the longest bridge's strength is 19" {
        ElectromagneticMoat().part2(input) shouldBe 19
    }
})
