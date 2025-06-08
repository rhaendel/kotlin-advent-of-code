package de.ronny_h.aoc.extensions

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
