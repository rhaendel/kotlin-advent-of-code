package de.ronny_h.aoc.year2017.day25

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split
import de.ronny_h.aoc.year2017.day25.TapeDirection.LEFT
import de.ronny_h.aoc.year2017.day25.TapeDirection.RIGHT

fun main() = TheHaltingProblem().run(2794, 0)

class TheHaltingProblem : AdventOfCode<Int>(2017, 25) {
    override fun part1(input: List<String>): Int = TuringMachine(input.parseTuringMachineConfiguration()).run()
    override fun part2(input: List<String>): Int = 0
}

fun List<String>.parseTuringMachineConfiguration(): TuringMachineConfiguration {
    val startState = first().substringAfter("Begin in state ").substringBefore(".")
    val checkAfter = this[1].substringAfter("Perform a diagnostic checksum after ").split(" ").first().toInt()

    val rules = subList(3, size).split().flatMap { rule ->
        val inState = rule[0].substringAfter("In state ").trim(':')
        listOf(rule.parseRule(inState, 1), rule.parseRule(inState, 5))
    }

    return TuringMachineConfiguration(startState, checkAfter, rules)
}

private fun List<String>.parseRule(
    inState: String,
    offset: Int,
): Rule {
    val value = this[offset].substringAfter("  If the current value is ").trim(':').toByte()
    val toWrite = this[offset + 1].substringAfter("    - Write the value ").trim('.').toByte()
    val toMove = if (this[offset + 2].endsWith("right.")) RIGHT else LEFT
    val nextState = this[offset + 3].substringAfter("    - Continue with state ").trim('.')
    return Rule(inState, value, toWrite, toMove, nextState)
}

data class TuringMachineConfiguration(val startState: String, val checkAfter: Int, val rules: List<Rule>)

data class Rule(
    val inState: String,
    val value: Byte,
    val toWrite: Byte,
    val toMove: TapeDirection,
    val nextState: String,
)

enum class TapeDirection(val offset: Int) {
    LEFT(-1), RIGHT(1)
}

class TuringMachine(private val configuration: TuringMachineConfiguration) {
    private val tape = mutableMapOf<Int, Byte>().withDefault { 0 }
    private var cursor = 0
    private var state = configuration.startState

    private val rules = configuration.rules.associateBy { Pair(it.inState, it.value) }

    fun run(): Int {
        repeat(configuration.checkAfter) {
            val rule = rules.getValue(Pair(state, tape.getValue(cursor)))
            tape[cursor] = rule.toWrite
            cursor += rule.toMove.offset
            state = rule.nextState
        }
        return tape.values.count { it == 1.toByte() }
    }
}
