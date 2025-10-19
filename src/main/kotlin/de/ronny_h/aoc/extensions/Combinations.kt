package de.ronny_h.aoc.extensions

import kotlin.math.pow


/**
 * @return All pairwise combinations of list elements without the reflexive ones.
 */
fun <E> Iterable<E>.combinations() = sequence {
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
        return@sequence
    }

    for (i in list.indices) {
        permutationsOf(list - list[i]).forEach { smallerPermutation ->
            yield(smallerPermutation + list[i])
        }
    }
}

fun <E> allSublistsOf(list: List<E>): Sequence<List<E>> = sequence {
    // count a binary number with list.size digits from 0...0 to 1...1
    var number = 0
    while (number < 2.0.pow(list.size.toDouble())) {
        val sublist = ArrayList<E>(list.size)
        val binary = number.toString(2).reversed()
        // take the list elements at indices of the 1 bits
        binary.forEachIndexed { i, bit ->
            if (bit == '1') {
                sublist.add(list[i])
            }
        }
        yield(sublist)
        number++
    }
}

/**
 * @return A sequence of `List<Int>` where each list is of length [n] and its elements count from `0` to [sum] in such a way
 * that `sum(list[0], list[1], ... list[n-1]) == [sum]`.
 */
fun sequenceNumbersOfEqualSum(n: Int, sum: Int): Sequence<List<Int>> = sequence {
    if (n == 1) {
        yield(listOf(sum))
        return@sequence
    }

    for (i in 0..sum) {
        sequenceNumbersOfEqualSum(n - 1, sum - i).forEach { shorterList ->
            yield(shorterList + i)
        }
    }
}
