package de.ronny_h.aoc.extensions


/**
 * @return All pairwise combinations of list elements without the reflexive ones.
 */
fun <E> List<E>.combinations() = sequence {
    forEachIndexed { i, a ->
        forEachIndexed { j, b ->
            if (i != j) {
                yield(a to b)
            }
        }
    }
}
