package de.ronny_h.aoc.extensions

import kotlin.math.abs

/**
 * Counts the number of digits of the given Long number.
 */
fun Long.digitCount(): Int {
    if (this == 0L) return 1

    var count = 0
    var currentNumber = this
    while (currentNumber > 0) {
        currentNumber /= 10
        count++
    }
    return count
}

/**
 * Returns the ones digit (the rightmost digit) of the given Long number.
 */
fun Long.onesDigit(): Int = abs(this % 10).toInt()
