package de.ronny_h.aoc.extensions

import kotlin.math.floor

/**
 * Checks if a Double number is integral.
 */
fun Double.isIntegral() = floor(this) == this

/**
 * Checks if a String can be converted to an Int.
 */
fun String.isInt(): Boolean = try {
    this.toInt()
    true
} catch (_: NumberFormatException) {
    false
}
