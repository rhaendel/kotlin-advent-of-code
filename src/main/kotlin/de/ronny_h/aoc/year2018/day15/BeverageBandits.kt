package de.ronny_h.aoc.year2018.day15

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.filterMinBy
import de.ronny_h.aoc.extensions.graphs.ShortestPath
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid
import de.ronny_h.aoc.year2018.day15.CombatArea.PlayerType.Elf
import de.ronny_h.aoc.year2018.day15.CombatArea.PlayerType.Goblin
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() = BeverageBandits().run(0, 0)

class BeverageBandits : AdventOfCode<Long>(2018, 15) {
    override fun part1(input: List<String>): Long {
        val combatArea = CombatArea(input)
        while (combatArea.takeOneRound()) {
            // the action takes place in the condition
        }
        return combatArea.outcome()
    }

    override fun part2(input: List<String>): Long {
        return 0
    }
}

class CombatArea(input: List<String>) : SimpleCharGrid(input) {
    private val cavern = '.'

    private data class Player(
        val type: PlayerType,
        var position: Coordinates,
        val attackPower: Int = 3,
        var hitPoints: Int = 200
    )

    private enum class PlayerType(val char: Char) {
        Elf('E'), Goblin('G')
    }

    private val units = buildList {
        forEachCoordinates { position, square ->
            when (square) {
                Elf.char -> add(Player(Elf, position))
                Goblin.char -> add(Player(Goblin, position))
            }
        }.last()
    }.toMutableList()

    private var fullRounds = 0L

    /**
     * @return if combat continues
     */
    fun takeOneRound(): Boolean {
        if (fullRounds % 10 == 0L) logStats()
        units.sortedBy { it.position }.forEach { unit ->
            logger.debug { "unit $unit" }
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

    fun outcome(): Long = fullRounds * units.sumOf { it.hitPoints }

    private data class Target(val position: Coordinates, val path: ShortestPath<Coordinates>)

    private fun Player.move(
        targets: List<Player>
    ) {
        logger.debug { " moving $this" }
        if (targets.any { it.position in position.neighbours() }) {
            // already in range of a target
            return
        }

        val targetsWithPaths = targets.flatMap { target ->
            target.position.neighbours()
                .filter { getAt(it) == cavern }
                .flatMap { inRange ->
                    shortestPaths(position, inRange) { neighbour ->
                        getAt(neighbour) == cavern
                    }
                        .map { Target(inRange, it) }
                }
        }
        if (targetsWithPaths.isEmpty()) {
            // no path to any target found
            return
        }
        logger.debug { "  ${targetsWithPaths.size} paths found" }
        val nearestTargetPath = targetsWithPaths
            .filterMinBy { it.path.distance }
            .filterMinBy { it.position }
            .filterMinBy { it.path.path[1] }
            .first()
        moveTo(nearestTargetPath.path.path[1])
    }

    private fun Player.attack(targets: List<Player>) {
        logger.debug { " attacking with $this" }
        val opponent = position
            .neighbours()
            .mapNotNull { neighbour -> targets.firstOrNull { it.position == neighbour } }
            .filterMinBy { it.hitPoints }
            .minByOrNull { it.position }
        if (opponent == null) return

        logger.debug { "  hitting $opponent" }
        opponent.hitPoints -= attackPower

        if (opponent.hitPoints <= 0) {
            opponent.die()
        }
    }

    private fun Player.moveTo(newPosition: Coordinates) {
        logger.debug { "  moving to $newPosition" }
        setAt(position, cavern)
        setAt(newPosition, type.char)
        position = newPosition
    }

    private fun Player.die() {
        setAt(position, cavern)
        units.remove(this)
    }

    private fun logStats() {
        logger.info { "round $fullRounds: ${elfCount()} elfs, ${goblinCount()} goblins" }
        logger.debug { "\n${toString()}" }
    }

    private fun elfCount() = units.count { it.type == Elf }
    private fun goblinCount() = units.count { it.type == Goblin }
}
