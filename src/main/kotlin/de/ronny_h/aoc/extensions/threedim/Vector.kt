package de.ronny_h.aoc.extensions.threedim

import kotlin.math.abs
import kotlin.math.cbrt

data class Vector(val x: Long, val y: Long, val z: Long) {
    companion object {
        val ZERO = Vector(0, 0, 0)
    }

    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector) = Vector(x - other.x, y - other.y, z - other.z)
    infix fun taxiDistanceTo(other: Vector): Long = abs(other.x - x) + abs(other.y - y) + abs(other.z - z)
    fun abs(): Double = cbrt(x * x.toDouble() + y * y.toDouble() + z * z.toDouble())
}

operator fun Int.times(vector: Vector) = Vector(this * vector.x, this * vector.y, this * vector.z)
operator fun Long.times(vector: Vector) = Vector(this * vector.x, this * vector.y, this * vector.z)
