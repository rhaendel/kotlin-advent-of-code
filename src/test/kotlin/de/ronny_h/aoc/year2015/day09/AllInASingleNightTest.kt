package de.ronny_h.aoc.year2015.day09

import de.ronny_h.aoc.extensions.graphs.Edge
import de.ronny_h.aoc.year2015.day09.AllInASingleNight.Companion.parseEdges
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AllInASingleNightTest : StringSpec({

    "input can be parsed" {
        listOf("London to Dublin = 464").parseEdges() shouldBe listOf(Edge("London", "Dublin", 464))
    }

    "a map with a set as key returns the values regardless of the input order" {
        val map = mapOf(setOf("London", "Dublin") to 464)
        map[setOf("London", "Dublin")] shouldBe 464
        map[setOf("Dublin", "London")] shouldBe 464
    }

    "part 1: The shortest distance to visit London, Dublin and Belfast" {
        val input = listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141",
        )
        AllInASingleNight().part1(input) shouldBe 605
    }

    "part 1: The input distances are symmetrical" {
        val input = listOf(
            "A to B = 1",
            "C to B = 2",
        )
        AllInASingleNight().part1(input) shouldBe 3
    }

    "part 2: The longest distance to visit London, Dublin and Belfast" {
        val input = listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141",
        )
        AllInASingleNight().part2(input) shouldBe 982
    }

    "part 2: The longest distance to visit ..." {
        val input = listOf(
            "A to B = 1",
            "B to C = 1",
            "B to D = 1",
            "C to D = 1",
            "A to C = 2",
        )
        AllInASingleNight().part2(input) shouldBe 4
    }
})
