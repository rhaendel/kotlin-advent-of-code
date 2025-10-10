package de.ronny_h.aoc.extensions.collections

import java.util.*

class MutableRingList<T>() {
    private val list: MutableList<T> = LinkedList<T>()
    private var offset = 0

    constructor(initialList: List<T>) : this() {
        list.addAll(initialList)
    }

    constructor(size: Int, init: (index: Int) -> T) : this() {
        repeat(size) { index -> list.add(init(index)) }
    }

    companion object {
        fun <T> mutableRingListOf(vararg elements: T): MutableRingList<T> = MutableRingList(elements.toList())
        fun mutableRingListOf(elements: String): MutableRingList<Char> = MutableRingList(elements.toList())
    }

    operator fun get(index: Int) = list[(index + offset) % list.size]

    operator fun set(index: Int, value: T) {
        list[(index + offset) % list.size] = value
    }

    /**
     * Inserts the given [value] at the current position (i.e. the current start of the ring list).
     */
    fun insert(value: T) {
        list.add(offset, value)
    }

    /**
     * Removes the value at the current position (i.e. the current start of the ring list) and returns it.
     * All elements after the current position are shifted 1 position left.
     *
     * @return The element that has been removed.
     */
    fun removeFirst(): T = list.removeAt(offset)

    /**
     * Makes [n] elements move from the end to the front, but maintain their order otherwise.
     */
    fun shiftRight(n: Int): MutableRingList<T> {
        offset = (offset + list.size - n).mod(list.size)
        return this
    }

    /**
     * Makes [n] elements move from the front to the end, but maintain their order otherwise.
     */
    fun shiftLeft(n: Int): MutableRingList<T> {
        offset = (offset + list.size + n).mod(list.size)
        return this
    }

    /**
     * Swaps the elements at [indexA] and [indexB].
     */
    fun swap(indexA: Int, indexB: Int): MutableRingList<T> {
        val tmp = get(indexA)
        set(indexA, get(indexB))
        set(indexB, tmp)
        return this
    }

    /**
     * Swaps the positions of the specified elements [elemA] and [elemB].
     * If not unique, only their first occurrences are swapped.
     */
    fun swap(elemA: T, elemB: T): MutableRingList<T> {
        val indexA = list.indexOf(elemA)
        val indexB = list.indexOf(elemB)
        require(indexA >= 0) { "$elemA is not in the list" }
        require(indexB >= 0) { "$elemB is not in the list" }
        list[indexA] = elemB
        list[indexB] = elemA
        return this
    }

    fun reverseSubList(s: Int, length: Int): MutableRingList<T> {
        val start = (offset + s) % list.size
        if (start + length < list.size) {
            list.subList(start, start + length).toList()
        } else {
            list.subList(start, list.size) + list.subList(0, length - list.size + start)
        }
            .reversed()
            .forEachIndexed { i, item ->
                list[(start + i) % list.size] = item
            }
        return this
    }

    /**
     * @return A snapshot of the current state of this [MutableRingList]
     */
    fun toList(): List<T> = list.subList(offset, list.size) + list.subList(0, offset)

    override fun toString(): String = toList().toString()

    fun toJoinedString(): String = toList().joinToString("")
}
