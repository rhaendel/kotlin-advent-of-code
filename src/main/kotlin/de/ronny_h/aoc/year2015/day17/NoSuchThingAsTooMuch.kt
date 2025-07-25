package de.ronny_h.aoc.year2015.day17

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.allSublistsOf

fun main() = NoSuchThingAsTooMuch().run(654, 0)

class NoSuchThingAsTooMuch : AdventOfCode<Int>(2015, 17) {
    override fun part1(input: List<String>): Int =
        differentCombinationsToStore(input.map(String::toInt), 150)

    override fun part2(input: List<String>): Int =
        differentWaysToStoreWithMinimalNumberOfContainers(input.map(String::toInt), 150)
}

fun differentCombinationsToStore(containers: List<Int>, capacity: Int): Int =
    allSublistsOf(containers).count { it.sum() == capacity }

fun differentWaysToStoreWithMinimalNumberOfContainers(containers: List<Int>, capacity: Int): Int {
    var minimalNumberOfContainers = containers.size
    var count = 0
    allSublistsOf(containers).forEach {
        if (it.sum() == capacity) {
            if (it.size < minimalNumberOfContainers) {
                minimalNumberOfContainers = it.size
                count = 1
            } else if (it.size == minimalNumberOfContainers) {
                count++
            }
        }
    }
    return count
}
