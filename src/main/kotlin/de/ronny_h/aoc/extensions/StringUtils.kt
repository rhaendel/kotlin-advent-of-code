package de.ronny_h.aoc.extensions

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets.UTF_8

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

/**
 * Runs the [block] while providing a [PrintStream]. Everything that is written to it is returned as a [String].
 */
fun withPrintStream(block: (PrintStream) -> Unit): String {
    val out = ByteArrayOutputStream()
    PrintStream(out, true, UTF_8).use {
        block(it)
    }
    return out.toString(UTF_8)
}
