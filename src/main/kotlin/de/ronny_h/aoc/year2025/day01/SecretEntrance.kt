package de.ronny_h.aoc.year2025.day01

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2025.day01.RotationDirection.LEFT
import de.ronny_h.aoc.year2025.day01.RotationDirection.RIGHT
import kotlin.math.abs

fun main() = SecretEntrance().run(1177, 6768)

class SecretEntrance : AdventOfCode<Int>(2025, 1) {

    override fun part1(input: List<String>): Int {
        var count = 0
        var position = 50
        input.parse().forEach {
            val rotated = position.rotate(it)
            val newPosition = rotated.mod(100)
            if (newPosition == 0) {
                count++
            }
            position = newPosition
        }
        return count
    }

    override fun part2(input: List<String>): Int {
        var count = 0
        var position = 50
        input.parse().forEach {
            val rotated = position.rotate(it)
            val newPosition = rotated.mod(100)
            val fullRounds = abs(rotated).div(100)
            if (newPosition == 0 && fullRounds == 0) {
                count++
            }
            count += fullRounds
            if (position != 0 && rotated < 0) {
                count++
            }
            position = newPosition
        }
        return count
    }

    private fun Int.rotate(rotation: Rotation): Int = when (rotation.direction) {
        LEFT -> this - rotation.amount
        RIGHT -> this + rotation.amount
    }
}

fun List<String>.parse(): List<Rotation> = map {
    val direction = when (it.first()) {
        'L' -> LEFT
        'R' -> RIGHT
        else -> error("unknown direction: $it")
    }
    Rotation(direction, it.substring(1).toInt())
}

data class Rotation(val direction: RotationDirection, val amount: Int)

enum class RotationDirection { LEFT, RIGHT }
