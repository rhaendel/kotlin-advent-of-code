package de.ronny_h.aoc.extensions.collections

/**
 * Splits the list into multiple sub-lists on lines that match the specified delimiter.
 * @param delimiter The line to split the list at. Lines from the original list exactly matching the [delimiter] are not part
 * of any of the resulting sub-lists.
 * @return A list containing all sub-lists of the original list split at lines matching the [delimiter].
 *
 * * Consecutive lines matching the [delimiter] do not produce empty sub-lists in the result.
 * * If the original list ends with a [delimiter], the returned list does not contain the last empty sub-list.
 */
fun List<String>.split(delimiter: String = ""): List<List<String>> {
    if (isEmpty()) return emptyList()
    val result = fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, line ->
        if (line == delimiter) {
            if (acc.last().isNotEmpty()) {
                acc.add(mutableListOf())
            }
        } else {
            acc.last().add(line)
        }
        acc
    }
    if (this.last() == delimiter) {
        result.removeAt(result.lastIndex)
    }
    return result
}

/**
 * @return The [Iterable] of elements that maximize the given [selector].
 */
inline fun <T, R : Comparable<R>> Iterable<T>.filterMaxBy(selector: (T) -> R): Iterable<T> {
    if (none()) return this
    val max = maxOf(selector)
    return filter { selector(it) == max }
}

/**
 * Returns the unique element yielding the smallest value of the given [selector] function or `null` if there are no elements
 * or that element is not unique.
 *
 * If there are multiple equal minimal values returned by the [selector] function,
 * this function returns `null`.
 */
inline fun <T, R : Comparable<R>> Iterable<T>.minByUniqueOrNull(selector: (T) -> R): T? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var minElem = iterator.next()
    if (!iterator.hasNext()) return minElem
    var minValue = selector(minElem)
    var minCount = 1
    do {
        val e = iterator.next()
        val v = selector(e)
        if (minValue == v) {
            minCount++
        }
        if (minValue > v) {
            minElem = e
            minValue = v
            minCount = 1
        }
    } while (iterator.hasNext())
    return if (minCount == 1) minElem else null
}
