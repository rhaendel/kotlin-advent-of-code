package de.ronny_h.aoc.year24.day15

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.Coordinates
import de.ronny_h.aoc.extensions.Direction
import de.ronny_h.aoc.extensions.Grid

fun main() = WarehouseWoes().run(1463715, 1481392)

class WarehouseWoes : AdventOfCode<Int>(2024, 15) {
    override fun part1(input: List<String>): Int {
        val warehouse = NormalWarehouse(input.warehouseInput())
        return moveRobotAndCalculateResult(warehouse, input)
    }

    override fun part2(input: List<String>): Int {
        val warehouse = WideWarehouse(input.widenedWarehouseInput())
        return moveRobotAndCalculateResult(warehouse, input)
    }

    private fun moveRobotAndCalculateResult(
        warehouse: Warehouse,
        input: List<String>
    ): Int {
        warehouse.printGrid()

        val movements = input.toDay15Movements()

        warehouse.moveRobot(movements)
        warehouse.printGrid()

        return warehouse.sumGPSCoordinates()
    }
}

private fun List<String>.toDay15Movements(): List<Direction> = takeLastWhile { it.isNotBlank() }
    .joinToString(separator = "")
    .map {
        when (it) {
            '^' -> Direction.NORTH
            '>' -> Direction.EAST
            'v' -> Direction.SOUTH
            '<' -> Direction.WEST
            else -> error("Unexpected direction Char: $it")
        }
    }

abstract class Warehouse(input: List<String>) : Grid<Char>(input, '#') {
    protected val wall = nullElement
    protected val robot = '@'
    protected val free = '.'

    override fun Char.toElementType(): Char = this

    protected abstract val leftGoodsChar: Char
    protected abstract fun tryToMove(from: Coordinates, direction: Direction, thing: Char): Boolean

    fun moveRobot(movements: List<Direction>) {
        movements.fold(findTheRobot()) { position, direction ->
            if (tryToMove(position, direction, robot)) {
                position + direction
            } else {
                position
            }
        }
    }

    private fun findTheRobot(): Coordinates = forEachElement { row, col, element ->
        if (element == robot) Coordinates(row, col) else null
    }.filterNotNull().first()

    // GPS = Goods Positioning System
    fun sumGPSCoordinates(): Int = forEachElement { row, col, element ->
        if (element == leftGoodsChar) {
            100 * row + col
        } else {
            0
        }
    }.sum()
}
