package de.ronny_h.aoc.extensions.numbers

import kotlin.math.abs

/**
 * Counts the number of digits of the given Long number.
 */
fun Long.digitCount(): Int {
    if (this == 0L) return 1
    require(this >= 0)

    var count = 0
    var currentNumber = this
    while (currentNumber > 0) {
        currentNumber /= 10
        count++
    }
    return count
}

/**
 * Counts the number of digits of the given Int number.
 */
fun Int.digitCount(): Int {
    if (this == 0) return 1
    require(this >= 0)

    var count = 0
    var currentNumber = this
    while (currentNumber > 0) {
        currentNumber /= 10
        count++
    }
    return count
}

/**
 * @return The ones digit (the rightmost digit) of the given Long number.
 */
fun Long.onesDigit(): Int = abs(this % 10).toInt()

/**
 * @return The [n]th digit of the given Int number.
 */
fun Int.digit(n: Int): Int {
    require(n > 0) { "n must be greater than zero, but was: $n" }
    return abs(this / 10.pow(n - 1) % 10)
}

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

/**
 * @return this positive [Int]'s digits from the lowest to the highest.
 */
fun Int.digits(): Sequence<Int> = sequence {
    require(this@digits >= 0)
    var remainder = this@digits
    if (remainder == 0) yield(0)
    while (remainder != 0) {
        yield(remainder % 10)
        remainder /= 10
    }
}

/**
 * @return this positive [Int]'s digits from the highest to the lowest.
 */
fun Int.digitsReversed(): Sequence<Int> = sequence {
    require(this@digitsReversed >= 0)
    var factor = 10.pow(this@digitsReversed.digitCount() - 1)
    while (factor > 0) {
        yield((this@digitsReversed % (10 * factor) - this@digitsReversed % factor) / factor)
        factor /= 10
    }
}
