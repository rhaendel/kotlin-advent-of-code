package de.ronny_h.aoc.year24.day15

import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Direction.EAST
import de.ronny_h.aoc.extensions.Direction.WEST
import printAndCheck
import readInput
import kotlin.collections.iterator

fun main() {
    val day = "Day15"

    println("$day part 2")

    fun part2(input: List<String>): Int {
        val widenedInput = input.widenTheWarehouseInput()
        val warehouse = WideWarehouse(widenedInput)
        warehouse.printGrid()

        val movements = input.toDay15Movements()

        warehouse.moveRobot(movements)
        warehouse.printGrid()

        return warehouse.sumGPSCoordinates()
    }

    printAndCheck(
        """
            #######
            #...#.#
            #.....#
            #..OO@#
            #..O..#
            #.....#
            #######
            
            <vv<<^^<<^^
        """.trimIndent().lines(),
        ::part2, 618
    )

    val testInput = readInput("${day}_test")
    val input = readInput(day)
    printAndCheck(testInput, ::part2, 9021)
    printAndCheck(input, ::part2, 1481392)
}

private fun List<String>.widenTheWarehouseInput(): List<String> = takeWhile { it.isNotBlank() }
    .map {
        val chars = it.toMutableList()
        val listIterator = chars.listIterator()
        for (elem in listIterator) {
            when (elem) {
                '#' -> listIterator.add('#')
                'O' -> {
                    listIterator.set('[')
                    listIterator.add(']')
                }

                '.' -> listIterator.add('.')
                '@' -> listIterator.add('.')
            }
        }
        chars.joinToString("")
    }

// a thing can be the robot, a wall or goods
private data class ThingInWarehouse(val thing: Char, val from: Coordinates, val direction: Direction) {
    val to: Coordinates = from + direction
}

private class WideWarehouse(input: List<String>) : Warehouse(input) {
    private val goods = listOf('[', ']')
    override val leftGoodsChar = goods.first()

    override fun tryToMove(from: Coordinates, direction: Direction, thing: Char): Boolean {
        return tryToMove(setOf(ThingInWarehouse(thing, from, direction)))
    }

    private val rightGoodsChar = goods.last()

    private fun tryToMove(things: Set<ThingInWarehouse>): Boolean {
        val toMove = collectNeighbours(things)
//        println("--- try to move: $toMove")
        if (toMove.all { getAt(it.to) == free }) {
            toMove.forEach {
                setAt(it.to, it.thing)
                setAt(it.from, free)
            }
//            printGrid()
            return true
        }
        if (toMove.any { getAt(it.to) == wall }) {
            return false
        }
        check(toMove.all { getAt(it.to) in (goods + free) })
        if (tryToMove(toMove.targets())) {
            toMove.forEach {
                setAt(it.to, it.thing)
                setAt(it.from, free)
            }
//            printGrid()
            return true
        }
        return false
    }

    /*
   []  []
    [][]
     []
     @
     */
    private fun collectNeighbours(things: Set<ThingInWarehouse>): Set<ThingInWarehouse> {
        if ((things.size == 1 && things.single().thing == robot) || things.first().direction.isHorizontal()) {
            return things
        }
        val targets = buildSet {
            things.forEach { thing ->
                if (thing.thing == leftGoodsChar) {
                    add(thing)
                    add(ThingInWarehouse(rightGoodsChar, thing.from + EAST, thing.direction))
                } else if (thing.thing == rightGoodsChar) {
                    add(thing)
                    add(ThingInWarehouse(leftGoodsChar, thing.from + WEST, thing.direction))
                }
                // here, things pointing to 'free' are filtered out
            }
        }
        return targets
    }

    private fun Set<ThingInWarehouse>.targets() = map {
        it.copy(thing = getAt(it.to), from = it.to)
    }.toSet()

}
