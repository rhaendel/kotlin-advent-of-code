package de.ronny_h.aoc.year24.day14

import de.ronny_h.aoc.AdventOfCode

fun main() = RestroomRedoubt().run(218433348, 6512)

class RestroomRedoubt : AdventOfCode<Int>(2024, 14) {
    private val robotPattern = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()

    private fun List<String>.parseRobots() = map {
        val (px, py, vx, vy) = robotPattern.find(it)!!.destructured
        Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
    }

    private fun List<Robot>.move(width: Int, height: Int, seconds: Int) = map {
        it.copy(
            px = (it.px + seconds * it.vx).mod(width),
            py = (it.py + seconds * it.vy).mod(height),
        )
    }

    private fun List<Robot>.countQuadrants(width: Int, height: Int) = fold(QuadrantCounts(0, 0, 0, 0)) { counts, it ->
        if (it.px in 0..<width / 2 && it.py in 0..<height / 2) {
            counts.q1++
        }
        if (it.px in width / 2 + 1..width && it.py in 0..<height / 2) {
            counts.q2++
        }
        if (it.px in 0..<width / 2 && it.py in height / 2 + 1..height) {
            counts.q3++
        }
        if (it.px in width / 2 + 1..width && it.py in height / 2 + 1..height) {
            counts.q4++
        }
        counts
    }

    private fun List<Robot>.toGrid(
        width: Int,
        height: Int
    ): List<List<Int>> {
        val grid = MutableList(height) { MutableList(width) { 0 } }
        forEach {
            grid[it.py][it.px]++
        }
        return grid
    }

    private fun List<List<Int>>.print() {
        forEach { row ->
            row.forEach {
                when (it) {
                    0 -> print('.')
                    else -> print(it.digitToChar())
                }
            }
            println()
        }
    }

    fun part1Small(input: List<String>): Int {
        val width = 11
        val height = 7
        val robots = input.parseRobots()
        robots.toGrid(width, height).print()
        println("---------------------")
        val robotsMoved = robots.move(width, height, 100)
        robotsMoved.toGrid(width, height).print()
        val (q1, q2, q3, q4) = robotsMoved.countQuadrants(width, height)
        return q1 * q2 * q3 * q4
    }

    override fun part1(input: List<String>): Int {
        val width = 101
        val height = 103
        val (q1, q2, q3, q4) = input.parseRobots()
            .move(width, height, 100)
            .countQuadrants(width, height)
        return q1 * q2 * q3 * q4
    }

    private fun List<Robot>.isNotUnique(width: Int, height: Int): Boolean {
        val grid = toGrid(width, height)
        val allUnique = grid.all { row ->
            row.all { it <= 1 }
        }
        if (allUnique) {
            grid.print()
        }
        return !allUnique
    }

    private fun iterateUntilUnique(robots: List<Robot>, width: Int, height: Int): Int {
        var seconds = 0
        do {
            seconds++
            val result = robots.move(width, height, seconds)
        } while (result.isNotUnique(width, height))
        return seconds
    }

    override fun part2(input: List<String>) = iterateUntilUnique(input.parseRobots(), 101, 103)
}

data class Robot(val px: Int, val py: Int, val vx: Int, val vy: Int)

data class QuadrantCounts(var q1: Int, var q2: Int, var q3: Int, var q4: Int)
