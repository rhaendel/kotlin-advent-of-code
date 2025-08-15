package de.ronny_h.aoc.year2017.day08

import de.ronny_h.aoc.AdventOfCode

fun main() = IHeardYouLikeRegisters().run(8022, 9819)

class IHeardYouLikeRegisters : AdventOfCode<Int>(2017, 8) {
    override fun part1(input: List<String>): Int = executeInstructions(input).maxFinalValue
    override fun part2(input: List<String>): Int = executeInstructions(input).maxProcessValue

    data class Result(val maxFinalValue: Int, val maxProcessValue: Int)

    private fun executeInstructions(input: List<String>): Result {
        val registers = mutableMapOf<String, Int>()
        var maxProcessValue = 0
        input.parseInstructions().forEach {
            if (it.condition.appliesTo(registers.getOrDefault(it.conditionRegister, 0), it.conditionValue)) {
                val value = it.operation.applyTo(registers.getOrDefault(it.register, 0), it.amount)
                if (value > maxProcessValue) {
                    maxProcessValue = value
                }
                registers[it.register] = value
            }
        }
        return Result(registers.values.max(), maxProcessValue)
    }
}

enum class Operation(private val symbol: String) {
    Increase("inc") {
        override fun applyTo(first: Int, second: Int): Int = first + second
    },
    Decrease("dec") {
        override fun applyTo(first: Int, second: Int): Int = first - second
    };

    abstract fun applyTo(first: Int, second: Int): Int

    companion object {
        private val bySymbol = Operation.entries.associateBy { it.symbol }

        fun of(symbol: String) = bySymbol.getValue(symbol)
    }
}

enum class Condition(private val symbol: String) {
    Equal("==") {
        override fun appliesTo(first: Int, second: Int): Boolean = first == second
    },
    NotEqual("!=") {
        override fun appliesTo(first: Int, second: Int): Boolean = first != second
    },
    LessThan("<") {
        override fun appliesTo(first: Int, second: Int): Boolean = first < second
    },
    LessThanOrEqual("<=") {
        override fun appliesTo(first: Int, second: Int): Boolean = first <= second
    },
    GreaterThan(">") {
        override fun appliesTo(first: Int, second: Int): Boolean = first > second
    },
    GreaterThanOrEqual(">=") {
        override fun appliesTo(first: Int, second: Int): Boolean = first >= second
    };

    abstract fun appliesTo(first: Int, second: Int): Boolean

    companion object {
        private val bySymbol = Condition.entries.associateBy { it.symbol }

        fun of(symbol: String) = bySymbol.getValue(symbol)
    }
}

data class Instruction(
    val register: String,
    val operation: Operation,
    val amount: Int,
    val conditionRegister: String,
    val condition: Condition,
    val conditionValue: Int
)

fun List<String>.parseInstructions(): List<Instruction> = map {
    val parameters = it.split(" ")
    Instruction(
        register = parameters[0],
        operation = Operation.of(parameters[1]),
        amount = parameters[2].toInt(),
        conditionRegister = parameters[4],
        condition = Condition.of(parameters[5]),
        conditionValue = parameters[6].toInt(),
    )
}
