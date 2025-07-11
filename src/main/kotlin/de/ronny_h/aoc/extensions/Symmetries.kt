package de.ronny_h.aoc.extensions

fun <T> List<T>.isSymmetrical(): Boolean {
    for (i in 0..<size / 2) {
        if (this[i] != this[lastIndex - i]) return false
    }
    return true
}
