package de.ronny_h.aoc.year2018.day08

import de.ronny_h.aoc.AdventOfCode

fun main() = MemoryManeuver().run(45618, 22306)

class MemoryManeuver : AdventOfCode<Int>(2018, 8) {
    override fun part1(input: List<String>): Int = input.parseTreeNodes().metadataSum()
    override fun part2(input: List<String>): Int = input.parseTreeNodes().value()
}

data class Node(private val meta: List<Int>, private val children: List<Node>) {
    fun metadataSum(): Int = meta.sum() + children.sumOf(Node::metadataSum)
    fun value(): Int = if (children.isEmpty()) {
        meta.sum()
    } else {
        meta.filter { it in 1..children.size }
            .sumOf { children[it - 1].value() }
    }
}

fun List<String>.parseTreeNodes(): Node {
    val numbers = single().split(" ").map(String::toInt)

    fun parseSubtree(numbers: List<Int>): Pair<Node, Int> {
        val childCount = numbers[0]
        val metaCount = numbers[1]

        var offset = 2
        val children = mutableListOf<Node>()
        repeat(childCount) {
            val (child, length) = parseSubtree(numbers.subList(offset, numbers.lastIndex))
            offset += length
            children += child
        }
        return Node(numbers.subList(offset, offset + metaCount), children) to metaCount + offset
    }

    return parseSubtree(numbers).first
}
