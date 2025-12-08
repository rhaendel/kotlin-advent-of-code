package de.ronny_h.aoc.extensions.threedim

import de.ronny_h.aoc.extensions.numbers.squared
import kotlin.math.abs
import kotlin.math.cbrt
import kotlin.math.sqrt

data class Vector(val x: Long, val y: Long, val z: Long) {
    companion object {
        val ZERO = Vector(0, 0, 0)
    }

    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector) = Vector(x - other.x, y - other.y, z - other.z)

    infix fun taxiDistanceTo(other: Vector): Long = abs(other.x - x) + abs(other.y - y) + abs(other.z - z)
    infix fun straightLineDistanceTo(other: Vector): Double =
        sqrt((other.x - x).squared().toDouble() + (other.y - y).squared() + (other.z - z).squared())

    fun abs(): Double = cbrt(x * x.toDouble() + y * y.toDouble() + z * z.toDouble())
}

operator fun Int.times(vector: Vector) = Vector(this * vector.x, this * vector.y, this * vector.z)
operator fun Long.times(vector: Vector) = Vector(this * vector.x, this * vector.y, this * vector.z)
