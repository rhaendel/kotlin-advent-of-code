package de.ronny_h.extensions

fun List<Int>.isSymmetrical(): Boolean {
    for (i in 0..<size / 2) {
        if (this[i] != this[lastIndex - i]) return false
    }
    return true
}
