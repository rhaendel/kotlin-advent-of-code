package de.ronny_h.aoc.extensions

import kotlin.math.floor

/**
 * Checks if a Double number is integral.
 */
fun Double.isIntegral() = floor(this) == this
