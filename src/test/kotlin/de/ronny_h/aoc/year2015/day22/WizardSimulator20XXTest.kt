package de.ronny_h.aoc.year2015.day22

import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.drain
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.magicMissile
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.poison
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.recharge
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.shield
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class WizardSimulator20XXTest : StringSpec({

    "can parse the input" {
        val input = listOf(
            "Hit Points: 50",
            "Damage: 5",
        )
        input.parseBossStats() shouldBe BossStats(50, 5)
    }

    "the player wins in the first example" {
        val playerStats = PlayerStats(10, 250)
        val bossStats = BossStats(13, 8)

        val result = findPlayerWins(bossStats, playerStats)
        result.playerWins shouldBe true
        result.manaSpent shouldBe 226
        result.spellsCast shouldBe listOf(
            poison,
            magicMissile,
        )
    }

    "the player wins in the second example" {
        val playerStats = PlayerStats(10, 250)
        val bossStats = BossStats(14, 8)

        val result = findPlayerWins(bossStats, playerStats)
        result.playerWins shouldBe true
        result.manaSpent shouldBe 641
        result.spellsCast shouldBe listOf(
            recharge,
            shield,
            drain,
            poison,
            magicMissile,
        )
    }

    "part1: the player wins with the calculated spells" {
        val playerStats = PlayerStats(50, 500)
        val bossStats = BossStats(14, 9)

        val result = findPlayerWins(bossStats, playerStats)
        result.playerWins shouldBe true
        result.manaSpent shouldBe 212
        result.spellsCast shouldBe listOf(
            magicMissile,
            magicMissile,
            magicMissile,
            magicMissile,
        )
    }

    "part 1: the least amount of mana to win the fight" {
        val input = listOf(
            "Hit Points: 14",
            "Damage: 9",
        )
        WizardSimulator20XX().part1(input) shouldBe 212
    }

    "part 2: the least amount of mana to win the fight on hard difficulty" {
        val input = listOf(
            "Hit Points: 14",
            "Damage: 9",
        )
        WizardSimulator20XX().part2(input) shouldBe 212
    }
})
