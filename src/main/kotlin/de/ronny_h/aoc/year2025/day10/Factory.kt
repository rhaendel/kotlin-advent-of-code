package de.ronny_h.aoc.year2025.day10

import com.microsoft.z3.*
import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.allSublistsOf
import de.ronny_h.aoc.extensions.graphs.shortestpath.aStarDouble
import de.ronny_h.aoc.extensions.numbers.squared
import de.ronny_h.aoc.extensions.substringBetween
import de.ronny_h.aoc.year2025.day10.LightState.OFF
import de.ronny_h.aoc.year2025.day10.LightState.ON
import kotlin.math.sqrt

fun main() = Factory().run(481, 20142)

class Factory : AdventOfCode<Int>(2025, 10) {
    override fun part1(input: List<String>): Int = input
        .parseMachineDescriptions().sumOf {
            Machine(it).configureIndicatorLights()
        }

    override fun part2(input: List<String>): Int = input
        .parseMachineDescriptions().sumOf {
            Machine(it).configureJoltages()
        }
}

// [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
fun List<String>.parseMachineDescriptions(): List<MachineDescription> =
    map { line ->
        MachineDescription(
            indicatorLights = line.substringBefore("] (").drop(1)
                .map { if (it == '#') ON else OFF },
            wiringSchematics = line.substringBetween("] ", " {")
                .split(" ").map { it.drop(1).dropLast(1).split(",").map(String::toInt) },
            joltage = line.substringAfter(") {").dropLast(1).split(",").map(String::toInt),
        )
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

class Machine(private val description: MachineDescription) {
    private val buttons = description.wiringSchematics

    fun configureIndicatorLights(): Int {
        val indicatorLightsOff = List(description.indicatorLights.size) { OFF }
        val target = description.indicatorLights

        return allSublistsOf(buttons).minOf { toPress ->
            val newState = indicatorLightsOff.toMutableList()
            toPress.forEach { button ->
                button.forEach { newState[it] = newState[it].toggle() }
            }
            if (newState.contentEquals(target)) {
                toPress.size
            } else {
                Int.MAX_VALUE
            }
        }
    }

    /*
        Create equations for joltages as sum of button presses and equal to the required target joltage of the machine.
        Then Optimize for min of total presses.
        Example:
           x0  x1    x2  x3    x4    x5
           (3) (1,3) (2) (2,3) (0,2) (0,1)
        0:                     x4    x5    = 3
        1:     x1                    x5    = 5
        2:           x2  x3    x4          = 4
        3: x0  x1        x3                = 7

        Use the Z3 Theorem Prover to solve the equations.
        - the C/C++ implementation: https://github.com/Z3Prover/z3/
        - packaged for Java by https://github.com/tudo-aqua/z3-turnkey
    */
    fun configureJoltages(): Int = with(Context()) {
        operator fun <R : ArithSort> ArithExpr<R>.plus(other: ArithExpr<R>) = mkAdd(this, other)
        fun Int.int() = mkInt(this)

        val optimization = mkOptimize()
        val buttonPresses = buttons.indices.map { mkIntConst("x$it") }

        // number of button presses may not be negative
        for (presses in buttonPresses) {
            optimization.Add(mkGe(presses, 0.int()))
        }

        // we want to minimize the total number of button presses
        val totalPresses = buttonPresses.fold<IntExpr, ArithExpr<IntSort>>(0.int()) { acc, n ->
            acc + n
        }
        optimization.MkMinimize(totalPresses)

        // create the actual system of linear equations
        val targetJoltages = description.joltage
        val joltageExpressions = MutableList<ArithExpr<IntSort>>(targetJoltages.size) { 0.int() }
        for ((button, presses) in buttons.zip(buttonPresses)) {
            for (i in button) {
                joltageExpressions[i] = joltageExpressions[i] + presses
            }
        }

        for ((joltage, targetJoltage) in joltageExpressions.zip(targetJoltages)) {
            optimization.Add(mkEq(joltage, targetJoltage.int()))
        }

        check(optimization.Check() == Status.SATISFIABLE)
        val minimalTotalPresses = optimization.model.evaluate(totalPresses, false)
        check(minimalTotalPresses is IntNum)
        return minimalTotalPresses.int
    }

    /*
        Alternative approach which is sufficient for the tests but does not terminate for the real input in reasonable
        time:
        Take 'all joltages zero' as the start and the target joltage levels as the goal in a graph with n-dimensional
        nodes. Each dimension corresponds to a joltage counter.

        Use the A-Star algorithm to search for the optimal path (the minimal sequence of button presses).
     */
    fun configureJoltagesUsingAStar(): Int {
        val goal = description.joltage

        // the maximum distance one button press can achieve is by increasing all values together:
        // sqrt(1^2 + 1^2 + ... + 1^2)
        val normVectorLength = sqrt(goal.size.toDouble())
        val shortestPath = aStarDouble(
            start = List(goal.size) { 0 },
            isGoal = { this == goal },

            // for given joltage levels, the possible button presses lead to the neighbours
            neighbors = { currentJoltages ->
                description.wiringSchematics.map { increasedByButton ->
                    currentJoltages.mapIndexed { i, joltage ->
                        if (i in increasedByButton) {
                            if (joltage == goal[i]) {
                                // this button press would overshoot -> filter it out
                                Int.MAX_VALUE
                            } else {
                                joltage + 1
                            }
                        } else {
                            joltage
                        }
                    }
                }.filterNot { it.contains(Int.MAX_VALUE) }
            },
            d = { from, to ->
                sqrt(
                    from.mapIndexed { i, element -> (element - to[i]).squared() }.sum().toDouble()
                ) / normVectorLength
            },

            // the euclidean distance to prefer buttons that move multiple joltage levels to the right direction,
            // normalized by normVectorLength since the heuristic must be admissible (see aStar docs for details)
            h = {
                sqrt(
                    it.mapIndexed { i, element -> (element - goal[i]).squared() }.sum().toDouble()
                ) / normVectorLength
            },
//            printIt = { visited, current, additionalInfo -> println(additionalInfo()) }
        )

        // the path includes the start -> subtract it
        return shortestPath.path.size - 1
    }
}

fun <T> MutableList<T>.contentEquals(other: List<T>): Boolean {
    if (size != other.size) return false
    forEachIndexed { i, element -> if (other[i] != element) return false }
    return true
}
