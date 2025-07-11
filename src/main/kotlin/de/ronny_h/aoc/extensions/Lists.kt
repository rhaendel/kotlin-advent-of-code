package de.ronny_h.aoc.extensions

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
