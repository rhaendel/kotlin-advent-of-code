package de.ronny_h.aoc.year24.day24

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.toBoolean
import de.ronny_h.aoc.extensions.toDigit

fun main() = CrossedWires().run(66055249060558, 0)

class CrossedWires : AdventOfCode<Long>(2024, 24) {

    class And : (Boolean, Boolean) -> Boolean {
        override fun invoke(a: Boolean, b: Boolean) = a && b
        override fun toString() = "AND"
        override fun equals(other: Any?) = other is And
        override fun hashCode(): Int = javaClass.hashCode()
    }

    class Or : (Boolean, Boolean) -> Boolean {
        override fun invoke(a: Boolean, b: Boolean) = a || b
        override fun toString() = "OR"
        override fun equals(other: Any?) = other is Or
        override fun hashCode(): Int = javaClass.hashCode()
    }

    class Xor : (Boolean, Boolean) -> Boolean {
        override fun invoke(a: Boolean, b: Boolean) = a xor b
        override fun toString() = "XOR"
        override fun equals(other: Any?) = other is Xor
        override fun hashCode(): Int = javaClass.hashCode()
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
            val operation = when (op) {
                "AND" -> And()
                "OR" -> Or()
                "XOR" -> Xor()
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
        .joinToString("") { it.value.toDigit() }

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
        // inputs: x00..x44, y00..y44
        // outputs: z00..z45
        val gates = parseGates(input)
        val wires = parseWires(input).associateBy(Wire::name)

        simulateGates(wires, gates)
        add(0, 0, wires, gates)
        add("1".repeat(45).toLong(2), 0, wires, gates)
        add(0, "1".repeat(45).toLong(2), wires, gates)
        add("1".repeat(45).toLong(2), "1".repeat(45).toLong(2), wires, gates)

        return 0
    }

    private fun add(
        x: Long,
        y: Long,
        wires: Map<String, Wire>,
        gates: List<Gate>
    ) {
        val modifiedWires = wires.toMutableMap()
        modifiedWires.putAll(x.toWires("x"))
        modifiedWires.putAll(y.toWires("y"))
        simulateGates(modifiedWires, gates)
    }

    private fun Long.toWires(prefix: String): Map<String, Wire> = toString(2)
        .reversed()
        .padEnd(45, '0')
        .mapIndexed { i, digit ->
            Wire(prefix + i.toString().padStart(2, '0'), digit.toBoolean())
        }
        .associateBy(Wire::name)

    private fun simulateGates(
        wires: Map<String, Wire>,
        gates: List<Gate>
    ) {
        println("--- simulating gates ---")
        val x0 = wires.withPrefixAsDecimal("x")
        val y0 = wires.withPrefixAsDecimal("y")

        val simulatedWires = wires.simulateGates(gates)

        val x1 = simulatedWires.withPrefixAsDecimal("x")
        val y1 = simulatedWires.withPrefixAsDecimal("y")

        val sumSimulatedBinary = simulatedWires.withPrefixAsBinary("z")
        val zWires = simulatedWires.withPrefixSortedByLSBFirst("z")
        val sumSimulated = sumSimulatedBinary.toLong(2)
        val sumExpectedBinary = (x1 + y1).toString(2).padStart(46, '0')

        // check that the input wires aren't modified
        check(x0 == x1)
        check(y0 == y1)

        println("inputs:")
        println("x=$x0")
        println("y=$y0")
        println()
        println("expected : x+y=${x1 + y1}")
        println("simulated: z  =$sumSimulated")
        println()
        println("as binary:")
        println("expected : x+y=$sumExpectedBinary")
        println("simulated: z  =$sumSimulatedBinary")

        val differentIndices = mutableListOf<Int>()
        for (i in sumSimulatedBinary.indices) {
            if (sumSimulatedBinary[i] != sumExpectedBinary[i]) {
                differentIndices.add(i)
            }
        }
        println("different indices: $differentIndices (${differentIndices.size})")

        val wrongWires = differentIndices.map { zWires[it].name }
        println("zWires at these indices: $wrongWires")

        val gatesToChange = gates.filter { it.out in wrongWires }
        println("gates to change: $gatesToChange")
    }
}

data class Wire(val name: String, val value: Boolean) {
    override fun toString() = "$name: $value"
}

data class Gate(val in1: String, val in2: String, val operation: (Boolean, Boolean) -> (Boolean), val out: String) {
    fun simulateWith(inWires: Map<String, Wire>): Wire = Wire(
        out,
        operation(inWires.valueOf(in1), inWires.valueOf(in2))
    )

    override fun toString() = "$in1 $operation $in2 = $out"
}

private fun Map<String, Wire>.valueOf(inWire: String): Boolean = getValue(inWire).value
