package de.ronny_h.aoc.extensions.numbers

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

fun sumOfFirstNaturalNumbers(n: Int): Long = (n * (n + 1).toLong()) / 2

fun Long.toIntChecked(): Int {
    if (this > Int.MAX_VALUE || this < Int.MIN_VALUE) {
        throw IllegalArgumentException("$this exceeds Int range")
    }
    return this.toInt()
}
