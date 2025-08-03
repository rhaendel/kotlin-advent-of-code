package de.ronny_h.aoc.year2015.day21

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RPGSimulator20XXTest : StringSpec({

    "can parse the input" {
        val input = listOf(
            "Hit Points: 100",
            "Damage: 7",
            "Armor: 3",
        )
        input.parseBossStats() shouldBe Stats(100, 7, 3)
    }

    "the player wins in the given example" {
        val bossStats = Stats(hitPoints = 12, damage = 7, armor = 2)
        val playerStats = Stats(hitPoints = 8, damage = 5, armor = 5)
        playerWins(playerStats, bossStats) shouldBe true
    }

    "part 1: the min cost to win - a Dagger for 8 is enough" {
        val input = listOf(
            "Hit Points: 100",
            "Damage: 1",
            "Armor: 1",
        )
        RPGSimulator20XX().part1(input) shouldBe 8
    }

    "part 2: the max cost to loose - a Dagger and a Damage +1 ring" {
        val input = listOf(
            "Hit Points: 100",
            "Damage: 3",
            "Armor: 3",
        )
        RPGSimulator20XX().part2(input) shouldBe 33
    }
})
