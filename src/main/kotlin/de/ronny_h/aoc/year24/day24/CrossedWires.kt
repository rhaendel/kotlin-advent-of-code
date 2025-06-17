package de.ronny_h.aoc.year24.day24

import de.ronny_h.aoc.AdventOfCode

fun main() = CrossedWires().run(66055249060558, 0)

class CrossedWires: AdventOfCode<Long>(2024, 24) {

    companion object {
        val and = { a: Boolean, b: Boolean -> a && b }
        val or = { a: Boolean, b: Boolean -> a || b }
        val xor = { a: Boolean, b: Boolean -> a xor b }
    }

    fun parseWires(input: List<String>) = input
        .takeWhile { it.isNotEmpty() }
        .map {
            val (name, value) = it.split(": ")
            Wire(name, value == "1")
        }

    fun parseGates(input: List<String>) = input
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .map {
            val (term, result) = it.split(" -> ")
            val (in1, op, in2) = term.split(" ")
            val operation = when(op) {
                "AND" -> and
                "OR" -> or
                "XOR" -> xor
                else -> error("Operation not supported: $op")
            }
            Gate(in1.trim(), in2.trim(), operation, result.trim())
        }

    override fun part1(input: List<String>): Long {
        val gates = parseGates(input)
        return parseWires(input)
            .associateBy(Wire::name)
            .simulateGates(gates)
            .withPrefixAsDecimal("z")
    }

    private fun Map<String, Wire>.withPrefixAsDecimal(prefix: String): Long = withPrefixAsBinary(prefix)
        .toLong(2)

    private fun Map<String, Wire>.withPrefixAsBinary(prefix: String): String = withPrefixSortedByLSBFirst(prefix)
            .joinToString("") {
                when (it.value) {
                    true -> "1"
                    false -> "0"
                }
            }

    private fun Map<String, Wire>.withPrefixSortedByLSBFirst(prefix: String): List<Wire> = values
        .filter { it.name.startsWith(prefix) }
        .sortedByDescending(Wire::name)

    private fun Map<String, Wire>.simulateGates(gates: List<Gate>): Map<String, Wire> {
        val wires = toMutableMap()
        var simulatedGates: Int
        do {
            val simulatableGates = gates.filter { it.in1 in wires && it.in2 in wires }
            simulatedGates = simulatableGates.size
            val outWires = simulatableGates
                .map { it.simulateWith(wires) }
                .associateBy(Wire::name)
            wires.putAll(outWires)
        } while (simulatedGates != gates.size)
        return wires
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

data class Wire(val name: String, val value: Boolean)
data class Gate(val in1: String, val in2: String, val operation: (Boolean, Boolean) -> (Boolean), val out: String) {
    fun simulateWith(inWires: Map<String, Wire>): Wire = Wire(
        out,
        operation(inWires.valueOf(in1), inWires.valueOf(in2))
    )
}

private fun Map<String, Wire>.valueOf(inWire: String): Boolean = getValue(inWire).value
