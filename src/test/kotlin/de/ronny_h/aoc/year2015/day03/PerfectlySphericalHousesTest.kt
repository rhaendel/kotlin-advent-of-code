package de.ronny_h.aoc.year2015.day03

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PerfectlySphericalHousesTest : StringSpec({

    "part 1: Santa delivers at least one present to a number of houses" {
        PerfectlySphericalHouses().part1(listOf(">")) shouldBe 2
        PerfectlySphericalHouses().part1(listOf("^>v<")) shouldBe 4
        PerfectlySphericalHouses().part1(listOf("^v^v^v^v^v")) shouldBe 2
    }

    "part 2: Santa and Robo-Santa deliver at least one present to a number of houses" {
        PerfectlySphericalHouses().part2(listOf("^v")) shouldBe 3
        PerfectlySphericalHouses().part2(listOf("^>v<")) shouldBe 3
        PerfectlySphericalHouses().part2(listOf("^v^v^v^v^v")) shouldBe 11
    }
})
