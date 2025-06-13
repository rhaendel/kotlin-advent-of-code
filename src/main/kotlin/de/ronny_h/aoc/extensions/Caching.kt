package de.ronny_h.aoc.extensions

fun <T, R> ((T) -> R).memoize(): ((T) -> R) {
    val original = this
    val cache = mutableMapOf<T, R>()
    return { n: T ->
        cache.getOrPut(n) { original(n) }
    }
}
