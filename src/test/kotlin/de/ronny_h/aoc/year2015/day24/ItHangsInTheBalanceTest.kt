package de.ronny_h.aoc.year2015.day24

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ItHangsInTheBalanceTest : StringSpec({

    "input can be parsed" {
        listOf("1", "2", "3").parseWeights() shouldBe listOf(1, 2, 3)
    }

    "When grouping the example into 3 groups of equal weight, there is a unique group 1 of minimal weight" {
        val groupings = listOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11).takeMinSizedGroupOneOfGroupsOfSameWeight(3)

        groupings shouldBe setOf(listOf(11, 9))
    }

    "the quantum entanglement is the product of all weights in a group" {
        listOf(7).quantumEntanglement() shouldBe 7
        listOf(11, 9).quantumEntanglement() shouldBe 99
        listOf(10, 4, 3, 2, 1).quantumEntanglement() shouldBe 240
    }

    "part 1 (three groups of equal weight): in the example, the packages 11 and 9 win" {
        val input = listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")
        ItHangsInTheBalance().part1(input) shouldBe 99
    }

    "part 2 (four groups of equal weight): in the example, the packages 11 and 4 win" {
        val input = listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")
        ItHangsInTheBalance().part2(input) shouldBe 44
    }
})
