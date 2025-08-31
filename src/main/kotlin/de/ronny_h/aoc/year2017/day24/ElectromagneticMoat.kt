package de.ronny_h.aoc.year2017.day24

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.filterMaxBy

fun main() = ElectromagneticMoat().run(1906, 1824)

class ElectromagneticMoat : AdventOfCode<Int>(2017, 24) {
    override fun part1(input: List<String>): Int {
        return buildStrongestBridge(0, 0, input.parseComponents())
    }

    override fun part2(input: List<String>): Int {
        return buildLongestBridge(0, emptyList(), input.parseComponents()).strength()
    }
}

fun List<String>.parseComponents() = map {
    val (port1, port2) = it.split("/")
    Component(port1.toInt(), port2.toInt())
}

data class Component(private val port1: Int, private val port2: Int) {
    fun hasPort(port: Int): Boolean = port1 == port || port2 == port
    fun other(port: Int) = if (port1 != port) port1 else port2
    fun strength() = port1 + port2
}

private fun List<Component>.strength() = sumOf(Component::strength)

fun buildStrongestBridge(lastPort: Int, strength: Int, remaining: List<Component>): Int {
    if (remaining.isEmpty()) {
        return strength
    }

    val matching = remaining.filter { it.hasPort(lastPort) }
    if (matching.isEmpty()) {
        return strength
    }

    return matching.maxOf { buildStrongestBridge(it.other(lastPort), strength + it.strength(), remaining - it) }
}

fun buildLongestBridge(lastPort: Int, bridge: List<Component>, remaining: List<Component>): List<Component> {
    if (remaining.isEmpty()) {
        return bridge
    }

    val matching = remaining.filter { it.hasPort(lastPort) }
    if (matching.isEmpty()) {
        return bridge
    }

    return matching
        .map { buildLongestBridge(it.other(lastPort), bridge + it, remaining - it) }
        .filterMaxBy(List<Component>::size)
        .maxBy(List<Component>::strength)
}
