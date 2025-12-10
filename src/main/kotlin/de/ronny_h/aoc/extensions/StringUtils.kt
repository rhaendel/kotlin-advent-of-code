package de.ronny_h.aoc.extensions

fun String.asList() = trimIndent().lines()

infix fun String.isAnagramOf(other: String): Boolean {
    if (this.length != other.length) {
        return false
    }
    val thisChars = this.groupBy { it }
    val otherChars = other.groupBy { it }
    return thisChars.size == otherChars.size && thisChars.all {
        it.value.size == otherChars[it.key]?.size
    }
}

fun String.substringBetween(startDelimiter: String, endDelimiter: String) =
    substringAfter(startDelimiter).substringBefore(endDelimiter)
