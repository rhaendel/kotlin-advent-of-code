package de.ronny_h.aoc.year2015.day22

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.instantly
import de.ronny_h.aoc.year2015.day22.WizardSimulator20XX.Companion.spells
import kotlin.math.max

fun main() = WizardSimulator20XX().run(1269, 1309)

class WizardSimulator20XX : AdventOfCode<Int>(2015, 22) {

    companion object {
        const val instantly = 0

        val magicMissile = Spell("Magic Missile", 53, 4, 0, 0, 0, instantly)
        val drain = Spell("Drain", 73, 2, 0, 2, 0, instantly)
        val shield = Spell("Shield", 113, 0, 7, 0, 0, 6)
        val poison = Spell("Poison", 173, 3, 0, 0, 0, 6)
        val recharge = Spell("Recharge", 229, 0, 0, 0, 101, 5)

        val spells = listOf(magicMissile, drain, shield, poison, recharge)
    }

    override fun part1(input: List<String>) = findPlayerWins(input.parseBossStats(), PlayerStats(50, 500)).manaSpent

    override fun part2(input: List<String>) = findPlayerWins(input.parseBossStats(), PlayerStats(50, 500), 1).manaSpent
}

data class Spell(
    val name: String,
    val manaCost: Int,
    val damage: Int,
    val armor: Int,
    val heal: Int,
    val manaRecharge: Int,
    val effectDuration: Int
)

data class PlayerStats(val hitPoints: Int, val mana: Int)
data class BossStats(val hitPoints: Int, val damage: Int)
data class ActiveEffect(val spell: Spell, var timer: Int)
data class Result(
    val playerWins: Boolean, val spellsCast: List<Spell>,
    val manaSpent: Int = spellsCast.sumOf(Spell::manaCost)
)

fun List<String>.parseBossStats() = BossStats(
    hitPoints = get(0).substringAfter("Hit Points: ").toInt(),
    damage = get(1).substringAfter("Damage: ").toInt(),
)

private data class State(
    val effects: Set<ActiveEffect>,
    val playerMana: Int,
    val playerHitPoints: Int,
    val bossHitPoints: Int,
) {
    fun playerArmor() = effects.sumOf { it.spell.armor }

    fun applyEffects() = State(
        effects.decreaseTimer(),
        playerMana + effects.sumOf { it.spell.manaRecharge },
        playerHitPoints,
        bossHitPoints - effects.sumOf { it.spell.damage },
    )

    fun isApplyable(spell: Spell) = spell.manaCost <= playerMana && (effects.none { it.spell == spell })

    fun cast(spell: Spell) = if (spell.effectDuration == instantly) {
        copy(
            playerMana = playerMana - spell.manaCost,
            bossHitPoints = bossHitPoints - spell.damage,
            playerHitPoints = playerHitPoints + spell.heal,
        )
    } else {
        copy(
            playerMana = playerMana - spell.manaCost,
            effects = effects + ActiveEffect(spell, spell.effectDuration),
        )
    }

    fun bossTurn(bossDamage: Int) = copy(
        playerHitPoints = playerHitPoints - max(bossDamage - playerArmor(), 1)
    )

    fun applyHardMode(hardModeHitPoints: Int) = copy(
        playerHitPoints = playerHitPoints - hardModeHitPoints
    )

    private fun Set<ActiveEffect>.decreaseTimer() =
        filter { it.timer > 1 }.map { it.copy(timer = it.timer - 1) }.toSet()
}

fun findPlayerWins(boss: BossStats, player: PlayerStats, hardModeHitPoints: Int = 0): Result {
    val cache = mutableMapOf<Pair<State, Spell>, Result>()

    fun findPlayerWins(
        state: State,
        spell: Spell,
        spellsCast: List<Spell>,
    ): Result {
        cache.get(state to spell)?.let { return it }

        // PLAYER TURN
        var newState = state
            .applyHardMode(hardModeHitPoints)
            .applyEffects()

        if (!newState.isApplyable(spell)) {
            return Result(false, spellsCast)
        }

        newState = newState.cast(spell)

        if (newState.bossHitPoints <= 0) {
            return Result(true, spellsCast + spell)
        }

        // BOSS TURN
        newState = newState.applyEffects()
        if (newState.bossHitPoints <= 0) {
            return Result(true, spellsCast + spell)
        }
        newState = newState.bossTurn(boss.damage)
        if (newState.playerHitPoints <= 0) {
            return Result(false, spellsCast + spell)
        }

        return spells
            .map { nextSpell ->
                findPlayerWins(newState, nextSpell, spellsCast + spell)
                    .also { result -> cache.put(newState to nextSpell, result) }
            }
            .filter { it.playerWins == true }
            .minByOrNull { it.manaSpent }
            ?: Result(false, emptyList(), Int.MAX_VALUE)
    }

    return spells
        .map { findPlayerWins(State(emptySet(), player.mana, player.hitPoints, boss.hitPoints), it, emptyList()) }
        .filter { it.playerWins == true }
        .minBy { it.manaSpent }
}
