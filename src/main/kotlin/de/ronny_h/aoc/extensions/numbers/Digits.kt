package de.ronny_h.aoc.extensions.numbers

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

/**
 * Converts a digit character to a boolean value.
 * @return `true` if this is '1', `false` if this is '0'.
 * @throws IllegalStateException if this is neither '1' nor '0'.
 */
fun Char.toBoolean(): Boolean = when (this) {
    '1' -> true
    '0' -> false
    else -> error("must be either 0 or 1: '$this'")
}

/**
 * Converts a boolean value to a digit character as String.
 * @return "1" if this is `true`, "0" otherwise.
 */
fun Boolean.toDigit(): String = when (this) {
    true -> "1"
    false -> "0"
}
