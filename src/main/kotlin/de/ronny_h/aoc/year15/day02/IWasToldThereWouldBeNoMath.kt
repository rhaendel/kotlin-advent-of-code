package de.ronny_h.aoc.year15.day02

import de.ronny_h.aoc.AdventOfCode

fun main() = IWasToldThereWouldBeNoMath().run(1586300, 3737498)

class IWasToldThereWouldBeNoMath : AdventOfCode<Int>(2015, 2) {
    data class Present(val length: Int, val width: Int, val height: Int) {
        fun surfaces() = listOf(2 * length * width, 2 * width * height, 2 * height * length)
        fun smallestPerimeter() = 2 * (width + length + height - listOf(width, height, length).max())
        fun volume() = length * width * height
    }

    override fun part1(input: List<String>): Int =
        input.toPresents()
            .map(Present::surfaces)
            .sumOf { it.sum() + it.min() / 2 }

    override fun part2(input: List<String>): Int =
        input.toPresents()
            .sumOf { it.smallestPerimeter() + it.volume() }

    private fun List<String>.toPresents() = map { it.split("x") }
        .map { (l, w, h) -> Present(l.toInt(), w.toInt(), h.toInt()) }
}
