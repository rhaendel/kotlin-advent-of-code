package de.ronny_h.aoc.year2018.day15

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.filterMinBy
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2018.day15.CombatArea.PlayerType.Elf
import de.ronny_h.aoc.year2018.day15.CombatArea.PlayerType.Goblin
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() = BeverageBandits().run(218272, 40861)

class BeverageBandits : AdventOfCode<Int>(2018, 15) {
    override fun part1(input: List<String>): Int {
        val combatArea = CombatArea(input)
        while (combatArea.takeOneRound()) {
            // the action takes place in the condition
        }
        return combatArea.outcome()
    }

    override fun part2(input: List<String>): Int {
        var combatArea = CombatArea(input, elfAttackPower = 4)
        while (true) {
            while (combatArea.takeOneRound() && !combatArea.anElfDied) {
                // the action takes place in the condition
            }
            if (combatArea.anElfDied) {
                combatArea = CombatArea(input, elfAttackPower = combatArea.elfAttackPower + 1)
            } else {
                return combatArea.outcome()
            }
        }
    }
}

class CombatArea(input: List<String>, val elfAttackPower: Int = 3) : SimpleCharGrid(input) {
    private val cavern = '.'
    private val goblinAttackPower = 3

    var anElfDied = false
        private set

    private var fullRounds = 0
    private val units = forEachCoordinates { position, square ->
        when (square) {
            Elf.char -> Player(Elf, position)
            Goblin.char -> Player(Goblin, position)
            else -> null
        }
    }.filterNotNull().toMutableList()


    init {
        logger.info { "starting new combat with attack powers: elfs: $elfAttackPower, goblins: $goblinAttackPower" }
    }

    /**
     * @return if combat continues
     */
    fun takeOneRound(): Boolean {
        if (fullRounds % 50 == 0) logStats()
        units.sortedBy { it.position }.forEach { unit ->
            logger.trace { "unit $unit" }
            val enemyType = if (unit.type == Elf) Goblin else Elf
            val targets = units.filter { it.type == enemyType }
            if (targets.isEmpty()) {
                return false
            }

            unit.move(targets)
            unit.attack(targets)
        }
        fullRounds++
        return true
    }

    fun outcome(): Int {
        logStats()
        val hitPointsLeft = units.sumOf { it.hitPoints }
        logger.info { "rounds: $fullRounds, hit points left: $hitPointsLeft" }
        return fullRounds * hitPointsLeft
    }

    private data class Player(
        val type: PlayerType,
        var position: Coordinates,
        var hitPoints: Int = 200
    ) {
        fun isDead() = hitPoints <= 0
        override fun toString(): String = "${type.char}${position}_$hitPoints"
    }

    private enum class PlayerType(val char: Char) {
        Elf('E'), Goblin('G')
    }

    private fun Player.move(
        targets: List<Player>
    ) {
        logger.trace { " moving $this" }
        if (targets.any { it.position in position.neighbours() }) {
            // already in range of a target
            return
        }

        val targetsInRange = targets.flatMap { target ->
            target.position.neighbours().filter { get(it) == cavern }
        }
        val shortestPaths = shortestPaths(
            start = position,
            goals = targetsInRange,
            stopAfterMinimalPathsAreFound = true,
            isObstacle = { it != cavern })

        if (shortestPaths.isEmpty()) {
            // no path to any target found
            return
        }
        logger.trace { "  ${shortestPaths.size} paths found" }
        val nearestTargetPath = shortestPaths
            .filterMinBy { it.distance }
            .filterMinBy { it.path.last() }
            .filterMinBy { it.path[1] }
            .first()
        moveTo(nearestTargetPath.path[1])
    }

    private fun Player.attack(targets: List<Player>) {
        if (this.isDead()) return
        logger.trace { " attacking with $this" }
        val opponent = position
            .neighbours()
            .mapNotNull { neighbour -> targets.firstOrNull { it.position == neighbour } }
            .filterMinBy { it.hitPoints }
            .minByOrNull { it.position }
        if (opponent == null) return

        opponent.hitPoints -= attackPower()
        logger.debug { " $this hit $opponent" }

        if (opponent.hitPoints <= 0) {
            opponent.die()
        }
    }

    private fun Player.attackPower() = when (type) {
        Elf -> elfAttackPower
        Goblin -> goblinAttackPower
    }

    private fun Player.moveTo(newPosition: Coordinates) {
        logger.debug { " $this moves to $newPosition" }
        set(position, cavern)
        set(newPosition, type.char)
        position = newPosition
    }

    private fun Player.die() {
        logger.debug { " $this died" }
        set(position, cavern)
        units.remove(this)
        if (type == Elf) {
            anElfDied = true
        }
    }

    private fun logStats() {
        logger.info { "round $fullRounds: ${elfCount()} elfs, ${goblinCount()} goblins" }
        logger.debug { "\n${toString()}" }
    }

    private fun elfCount() = units.count { it.type == Elf }
    private fun goblinCount() = units.count { it.type == Goblin }
}
