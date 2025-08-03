package de.ronny_h.aoc.year2015.day21

import de.ronny_h.aoc.AdventOfCode
import kotlin.math.max

fun main() = RPGSimulator20XX().run(111, 188)

class RPGSimulator20XX : AdventOfCode<Int>(2015, 21) {

    // must buy exactly one
    private val weapons = listOf(
        Item("Dagger", 8, 4, 0),
        Item("Shortsword", 10, 5, 0),
        Item("Warhammer", 25, 6, 0),
        Item("Longsword", 40, 7, 0),
        Item("Greataxe", 74, 8, 0),
    )

    // optional: 0-1
    private val armors = listOf(
        Item("No Armor", 0, 0, 0),
        Item("Leather", 13, 0, 1),
        Item("Chainmail", 31, 0, 2),
        Item("Splintmail", 53, 0, 3),
        Item("Bandedmail", 75, 0, 4),
        Item("Platemail", 102, 0, 5),
    )

    // optional: 0-2
    private val rings = listOf(
        Item("No Ring 1", 0, 0, 0),
        Item("No Ring 2", 0, 0, 0),
        Item("Damage +1", 25, 1, 0),
        Item("Damage +2", 50, 2, 0),
        Item("Damage +3", 100, 3, 0),
        Item("Defense +1", 20, 0, 1),
        Item("Defense +2", 40, 0, 2),
        Item("Defense +3", 80, 0, 3),
    )

    override fun part1(input: List<String>): Int {
        val bossStats = input.parseBossStats()

        var minCost = Int.MAX_VALUE
        var items = emptyList<Item>()
        forAllPossibleItemCombinations { boughtItems ->
            val (cost, playerStats) = boughtItems.calcPlayerStats()
            if (playerWins(playerStats, bossStats) && cost < minCost) {
                minCost = cost
                items = boughtItems
            }
        }
        println("bought items: $items, cost: $minCost")
        return minCost
    }

    private fun List<Item>.calcPlayerStats(): Pair<Int, Stats> {
        val playerStats = Stats(100, sumOf(Item::damage), sumOf(Item::armor))
        return Pair(sumOf(Item::cost), playerStats)
    }

    private fun forAllPossibleItemCombinations(block: (List<Item>) -> Unit) {
        for (weapon in weapons) {
            for (armor in armors) {
                for (ring1 in rings) {
                    for (ring2 in (rings - ring1)) {
                        block(listOf(weapon, armor, ring1, ring2))
                    }
                }
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val bossStats = input.parseBossStats()

        var maxCost = 0
        var items = emptyList<Item>()
        forAllPossibleItemCombinations { boughtItems ->
            val (cost, playerStats) = boughtItems.calcPlayerStats()
            if (!playerWins(playerStats, bossStats) && cost > maxCost) {
                maxCost = cost
                items = boughtItems
            }
        }
        println("bought items: $items, cost: $maxCost")
        return maxCost
    }
}

data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

data class Stats(val hitPoints: Int, val damage: Int, val armor: Int)

fun List<String>.parseBossStats() = Stats(
    hitPoints = get(0).substringAfter("Hit Points: ").toInt(),
    damage = get(1).substringAfter("Damage: ").toInt(),
    armor = get(2).substringAfter("Armor: ").toInt(),
)

fun playerWins(player: Stats, boss: Stats): Boolean {
    var playerHitPoints = player.hitPoints
    var bossHitPoins = boss.hitPoints
    while (playerHitPoints > 0 && bossHitPoins > 0) {
        bossHitPoins -= max(player.damage - boss.armor, 1)
        playerHitPoints -= max(boss.damage - player.armor, 1)
    }
    return bossHitPoins <= 0
}
