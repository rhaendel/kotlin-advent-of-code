package de.ronny_h.aoc.year2018.day18

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = SettlersOfTheNorthPole().run(603098, 210000)

class SettlersOfTheNorthPole : AdventOfCode<Int>(2018, 18) {
    override fun part1(input: List<String>): Int = LumberAcre(input).afterTenMinutes().totalResourceValue()

    override fun part2(input: List<String>): Int = LumberAcre(input).afterABillionMinutes().totalResourceValue()
}

class LumberAcre(height: Int, width: Int) : Grid<Char>(height, width, OUTSIDE) {

    constructor(input: List<String>) : this(input.size, input.first().length) {
        initGrid(input)
    }

    companion object {
        private const val OUTSIDE = ' '
        private const val OPEN = '.'
        private const val TREE = '|'
        private const val LUMBER = '#'
    }

    fun changeAMinute(): LumberAcre {
        val result = LumberAcre(height, width)

        forEachCoordinates { position, element ->
            val neighbours = position.neighboursIncludingDiagonals().map { get(it) }
            result[position] = when (element) {
                OPEN -> if (neighbours.count { it == TREE } > 2) TREE else OPEN
                TREE -> if (neighbours.count { it == LUMBER } > 2) LUMBER else TREE
                LUMBER -> if (neighbours.count { it == LUMBER } > 0 && neighbours.count { it == TREE } > 0) LUMBER else OPEN
                else -> error("unexpected element: $element")
            }
        }.last()

        return result
    }

    fun afterTenMinutes(): LumberAcre = (1..10).fold(this) { acc, _ -> acc.changeAMinute() }

    fun afterABillionMinutes(): LumberAcre {
        val hashes = mutableMapOf<Int, Int>()
        var last = this
        val totalMinutes = 1_000_000_000
        for (minute in 0..totalMinutes) {
            val next = last.changeAMinute()
            val hashCode = next.hashCode()
            if (hashes.containsKey(hashCode)) {
                return afterABillionMinutesUtilizingCycle(hashes.getValue(hashCode), minute, totalMinutes)
            }
            hashes[hashCode] = minute
            last = next
        }

        return last
    }

    private fun afterABillionMinutesUtilizingCycle(
        cycleStart: Int,
        cycleEnd: Int,
        totalMinutes: Int
    ): LumberAcre {
        val cycleLength = cycleEnd - cycleStart
        val toBeFilledWithCycles = totalMinutes - cycleStart
        val remainder = toBeFilledWithCycles % cycleLength
        val neededIterations = cycleStart + cycleLength - remainder
        return (1..neededIterations).fold(this) { acc, _ ->
            acc.changeAMinute()
        }
    }

    fun totalResourceValue(): Int {
        var wooded = 0
        var lumberyards = 0
        forEachElement { _, _, element ->
            if (element == TREE) wooded++
            else if (element == LUMBER) lumberyards++
        }.last()
        return wooded * lumberyards
    }

    override fun Char.toElementType(): Char = this
}
