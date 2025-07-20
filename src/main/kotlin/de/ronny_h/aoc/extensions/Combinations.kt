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

/**
 * @return All pairwise combinations of list elements of the [first] and the [second] list.
 */
fun <A, B> combinationsOf(first: List<A>, second: List<B>) = sequence {
    first.forEach { a ->
        second.forEach { b ->
            yield(a to b)
        }
    }
}

/**
 * @return A sequence of lists containing all permutations of the original one.
 */
fun <E> permutationsOf(list: List<E>): Sequence<List<E>> = sequence {
    if (list.isEmpty()) {
        yield(emptyList())
    }

    for (i in list.indices) {
        permutationsOf(list - list[i]).forEach { smallerPermutation ->
            yield(smallerPermutation + list[i])
        }
    }
}
