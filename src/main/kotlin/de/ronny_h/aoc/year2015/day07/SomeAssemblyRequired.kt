package de.ronny_h.aoc.year2015.day07

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.isInt
import de.ronny_h.aoc.year2015.day07.Operator.BinaryOperator
import de.ronny_h.aoc.year2015.day07.Operator.UnaryOperator
import de.ronny_h.aoc.year2015.day07.OperatorOutput.NoSignal
import de.ronny_h.aoc.year2015.day07.OperatorOutput.ValueOutput

fun main() = SomeAssemblyRequired().run(956, 40149)

class SomeAssemblyRequired : AdventOfCode<Int>(2015, 7) {

    override fun part1(input: List<String>): Int = input
        .map(::parse)
        .calculateSignalForA()

    private fun List<Operation>.calculateSignalForA(): Int {
        var operations = this
        val values = mutableMapOf<String, UShort>()
        while (values["a"] == null) {
            val remainingOperations = buildList {
                operations.forEach {
                    val result = it.execute(values)
                    if (result.out is ValueOutput) {
                        values.put(result.wire, result.out.value)
                    } else {
                        this.add(it)
                    }
                }
            }
            operations = remainingOperations
        }
        return values.getValue("a").toInt()
    }

    fun parse(line: String): Operation {
        val (lhs, rhs) = line.split(" -> ")
        val operation = lhs.split(" ")
        return when (operation.size) {
            1 -> if (operation.first().isInt()) {
                Scalar(operation.first().toUShort(), rhs)
            } else {
                ReWire(operation.first(), rhs)
            }

            2 -> UnaryOperation(operation[1], UnaryOperator.valueOf(operation.first()), rhs)
            3 -> BinaryOperation(operation[0], operation[2], BinaryOperator.valueOf(operation[1]), rhs)
            else -> error("invalid operation: $operation")
        }
    }

    override fun part2(input: List<String>): Int {
        val signalOfA = part1(input)
        val operations = input.map(::parse)
            .filter { it !is Scalar || it.out != "b" } + Scalar(signalOfA.toUShort(), "b")
        return operations.calculateSignalForA()
    }
}


sealed interface Operation {
    fun execute(values: Map<String, UShort>): Output
}

data class Scalar(val value: UShort, val out: String) : Operation {
    override fun execute(values: Map<String, UShort>) = Output(out, ValueOutput(value))
}

data class ReWire(val from: String, val out: String) : Operation {
    override fun execute(values: Map<String, UShort>) = Output(out, values[from]?.let { ValueOutput(it) } ?: NoSignal)
}

data class UnaryOperation(val operand: String, val operator: UnaryOperator, val out: String) : Operation {
    override fun execute(values: Map<String, UShort>) = Output(out, operator.execute(values, operand))
}

data class BinaryOperation(val op1: String, val op2: String, val operator: BinaryOperator, val out: String) :
    Operation {
    override fun execute(values: Map<String, UShort>) = Output(out, operator.execute(values, op1, op2))
}


sealed interface Operator {
    fun execute(values: Map<String, UShort>, vararg operands: String): OperatorOutput

    enum class BinaryOperator : Operator {
        AND {
            override fun execute(values: Map<String, UShort>, vararg operands: String) =
                execute(operands, values) { a, b -> a and b }
        },
        OR {
            override fun execute(values: Map<String, UShort>, vararg operands: String) =
                execute(operands, values) { a, b -> a or b }
        },
        RSHIFT {
            override fun execute(values: Map<String, UShort>, vararg operands: String) =
                execute(operands, values) { a, b -> (a.toInt() shr b.toInt()).toUShort() }
        },
        LSHIFT {
            override fun execute(values: Map<String, UShort>, vararg operands: String) =
                execute(operands, values) { a, b -> (a.toInt() shl b.toInt()).toUShort() }
        };

        fun execute(
            operands: Array<out String>,
            values: Map<String, UShort>,
            operation: (UShort, UShort) -> UShort
        ): OperatorOutput {
            require(operands.size == 2) { "invalid number of operands for AND: $operands" }
            fun valueOfParameter(idx: Int): UShort? =
                if (operands[idx].isInt()) operands[idx].toUShort() else values[operands[idx]]

            val op1 = valueOfParameter(0)
            val op2 = valueOfParameter(1)
            if (op1 == null || op2 == null) {
                return NoSignal
            }
            return ValueOutput(operation(op1, op2))
        }
    }

    enum class UnaryOperator : Operator {
        NOT {
            override fun execute(values: Map<String, UShort>, vararg operands: String): OperatorOutput {
                require(operands.size == 1) { "invalid number of operands for unary operator NOT: $operands" }
                return values[operands.first()]?.let { ValueOutput(it.inv()) } ?: NoSignal
            }
        }
    }
}

data class Output(val wire: String, val out: OperatorOutput)

sealed interface OperatorOutput {
    data class ValueOutput(val value: UShort) : OperatorOutput
    object NoSignal : OperatorOutput
}