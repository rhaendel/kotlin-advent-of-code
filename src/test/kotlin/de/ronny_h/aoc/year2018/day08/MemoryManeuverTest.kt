package de.ronny_h.aoc.year2018.day08

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MemoryManeuverTest : StringSpec({

    val input = listOf("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")

    "tree nodes can be parsed" {
        input.parseTreeNodes() shouldBe Node(
            listOf(1, 1, 2), listOf(
                Node(listOf(10, 11, 12), emptyList()),
                Node(
                    listOf(2), listOf(
                        Node(listOf(99), emptyList())
                    )
                )
            )
        )
    }

    "part 1: the sum of all metadata entries" {
        MemoryManeuver().part1(input) shouldBe 138
    }

    "part 2: the value dependent on having child nodes" {
        MemoryManeuver().part2(input) shouldBe 66
    }
})
