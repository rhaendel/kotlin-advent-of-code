package de.ronny_h.aoc.year2024.day05

import de.ronny_h.aoc.AdventOfCode

fun main() = PrintQueue().run(6384, 5353)

class PrintQueue : AdventOfCode<Int>(2024, 5) {
    private fun parseRules(input: List<String>): List<Pair<Int, Int>> = input
        .takeWhile { it.contains("|") }
        .map {
            val (a, b) = it.split("|")
            a.toInt() to b.toInt()
        }

    private fun parseUpdates(input: List<String>): List<List<Int>> = input
        .dropWhile { !it.contains(",") }
        .map {
            it.split(",")
                .map(String::toInt)
        }

    override fun part1(input: List<String>): Int {
        val rules = parseRules(input)
        val updates = parseUpdates(input)
        val pageComparator = PageComparator(rules)
        val orderedUpdates = updates.filter { it == it.sortedWith(pageComparator) }
        return orderedUpdates.sumOf { it[it.size / 2] }
    }

    override fun part2(input: List<String>): Int {
        val rules = parseRules(input)
        val updates = parseUpdates(input)
        val pageComparator = PageComparator(rules)
        val notOrderedUpdates = updates.filter { it != it.sortedWith(pageComparator) }
        return notOrderedUpdates
            .map { it.sortedWith(pageComparator) }
            .sumOf { it[it.size / 2] }
    }
}

class PageComparator(rules: List<Pair<Int, Int>>) : Comparator<Int> {

    private val rules: Map<Int, Set<Int>> = HashMap<Int, MutableSet<Int>>().apply {
        rules.forEach {
            getOrPut(it.first) { HashSet() }.add(it.second)
        }
    }
    private val rulesInverted: Map<Int, Set<Int>> = HashMap<Int, MutableSet<Int>>().apply {
        rules.forEach {
            getOrPut(it.second) { HashSet() }.add(it.first)
        }
    }

    override fun compare(o1: Int?, o2: Int?): Int = when {
        // pages are in the right order: o1 < o2
        rules[o1]?.contains(o2) == true -> -1

        // pages are in the wrong order: o1 > o2
        rulesInverted[o1]?.contains(o2) == true -> 1

        // the rules don't say anything about these two pages relatively to another
        // -> consider them equal
        else -> 0
    }
}
