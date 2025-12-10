package de.ronny_h.aoc.year2025.day10

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.memoize
import de.ronny_h.aoc.year2025.day10.LightState.OFF
import de.ronny_h.aoc.year2025.day10.LightState.ON

fun main() = Day10().run(481, 0)

class Day10 : AdventOfCode<Int>(2025, 10) {
    override fun part1(input: List<String>): Int {
        var i = 0
        return input.parseMachineDescriptions().sumOf {
            i++
            val result = Machine(it).configureIndicatorLights()
            println("machine $i: $result")
            result
        }
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

// [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
fun List<String>.parseMachineDescriptions(): List<MachineDescription> =
    map { line ->
        val indicatorLights = line.substringBefore("] (").drop(1)
            .map { if (it == '#') ON else OFF }
        val wiringSchematics = line.substringAfter("] ").substringBefore(" {")
            .split(" ").map { it.drop(1).dropLast(1).split(",").map(String::toInt) }
        val joltage = line.substringAfter(") {").dropLast(1).split(",").map(String::toInt)
        MachineDescription(indicatorLights, wiringSchematics, joltage)
    }


data class MachineDescription(
    val indicatorLights: List<LightState>,
    val wiringSchematics: List<List<Int>>,
    val joltage: List<Int>
)

enum class LightState {
    OFF, ON;

    fun toggle() = when (this) {
        OFF -> ON
        ON -> OFF
    }
}

class Machine(private val description: MachineDescription, private val maxPressCount: Int = 7) {
    private val indicatorLightsOff = List(description.indicatorLights.size) { OFF }
    private val buttons = description.wiringSchematics

    // determine the fewest total presses
    fun configureIndicatorLights(): Int {
        val target = description.indicatorLights

        data class RecursiveState(val state: List<LightState>, val buttonsToPress: List<Int>, val pressCount: Int)

        lateinit var pressButtons: (RecursiveState) -> Int
        pressButtons = { s ->
            if (s.pressCount == maxPressCount) {
                maxPressCount
            } else {
                val newState = s.state.toMutableList()
                s.buttonsToPress.forEach { newState[it] = newState[it].toggle() }
                if (newState.contentEquals(target)) {
                    s.pressCount
                } else {
                    buttons.minOf { pressButtons(RecursiveState(newState.toList(), it, s.pressCount + 1)) }
                }
            }
        }

        val pressButtonsMemoized = pressButtons.memoize()

        return buttons.minOf { pressButtonsMemoized(RecursiveState(indicatorLightsOff, it, 1)) }
    }
}

fun <T> MutableList<T>.contentEquals(other: List<T>): Boolean {
    if (size != other.size) return false
    forEachIndexed { i, element -> if (other[i] != element) return false }
    return true
}
