package de.ronny_h.aoc.year2015.day06

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.year2015.day06.Action.*

fun main() = FireHazard().run(400410, 15343601)

class FireHazard : AdventOfCode<Int>(2015, 6) {

    private val onOfGrid = List(1000) {
        MutableList(1000) { false }
    }

    private fun switch(switchLight: SwitchLight) = with(switchLight) {
        for (row in from.y..to.y) {
            for (col in from.x..to.x) {
                when (action) {
                    ON -> onOfGrid[row][col] = true
                    OFF -> onOfGrid[row][col] = false
                    TOGGLE -> onOfGrid[row][col] = !onOfGrid[row][col]
                }
            }
        }
    }

    override fun part1(input: List<String>): Int {
        input.map(::parse).forEach(::switch)
        return onOfGrid.sumOf { row -> row.count { it } }
    }

    fun parse(line: String): SwitchLight {
        val action = when {
            line.startsWith(ON.text) -> ON
            line.startsWith(OFF.text) -> OFF
            line.startsWith(TOGGLE.text) -> TOGGLE
            else -> error("invalid start of line: $line")
        }
        val (from, to) = line.substring(action.text.length).split(" through ")
        val (fromRow, fromCol) = from.split(',').map(String::toInt)
        val (toRow, toCol) = to.split(',').map(String::toInt)
        return SwitchLight(action, Coordinates(fromRow, fromCol), Coordinates(toRow, toCol))
    }

    private val dimmingGrid = List(1000) {
        MutableList(1000) { 0 }
    }

    private fun dim(switchLight: SwitchLight) = with(switchLight) {
        for (row in from.y..to.y) {
            for (col in from.x..to.x) {
                when (action) {
                    ON -> dimmingGrid[row][col]++
                    OFF -> if (dimmingGrid[row][col] > 0) dimmingGrid[row][col]--
                    TOGGLE -> dimmingGrid[row][col] = dimmingGrid[row][col] + 2
                }
            }
        }
    }

    override fun part2(input: List<String>): Int {
        input.map(::parse).forEach(::dim)
        return dimmingGrid.sumOf { row -> row.sumOf { it } }
    }
}

data class SwitchLight(val action: Action, val from: Coordinates, val to: Coordinates)

enum class Action(val text: String) {
    ON("turn on "), OFF("turn off "), TOGGLE("toggle ")
}
