package de.ronny_h.aoc.extensions.collections

import kotlin.math.abs

class MutableRingList<T>() {
    private interface Node<T> {
        var value: T
        var prev: Node<T>
        var next: Node<T>
    }

    private var head: Node<T> = EmptyNode()
    var size = 0
        private set

    constructor(initialList: List<T>) : this() {
        initialList.forEach { add(it) }
    }

    constructor(size: Int, init: (index: Int) -> T) : this() {
        repeat(size) { index -> add(init(index)) }
    }

    companion object {
        fun <T> mutableRingListOf(vararg elements: T): MutableRingList<T> = MutableRingList(elements.toList())
        fun mutableRingListOf(elements: String): MutableRingList<Char> = MutableRingList(elements.toList())
    }

    operator fun get(index: Int): T = head.goSteps(index).value

    operator fun set(index: Int, value: T) {
        head.goSteps(index).value = value
    }

    /**
     * Inserts the given [value] at the current position (i.e. the current start of the ring list).
     */
    fun insert(value: T) {
        head = addAsLast(value)
    }

    /**
     * Adds the given [value] to the end of this ring list (i.e. inserts it before the current position).
     */
    fun add(value: T) {
        addAsLast(value)
    }

    /**
     * Removes the value at the current position (i.e. the current start of the ring list) and returns it.
     * All elements after the current position are shifted 1 position left.
     *
     * @return The element that has been removed.
     * @throws IndexOutOfBoundsException If this ring list is empty.
     */
    fun removeFirst(): T {
        if (size == 0) {
            throw IndexOutOfBoundsException("Unable to remove an element from an empty list.")
        }
        size--
        val removed = head.value
        if (size == 0) {
            head = EmptyNode()
            return removed
        }
        val node = head.next
        node.prev = head.prev
        node.prev.next = node
        head = node
        return removed
    }

    /**
     * Makes [n] elements move from the end to the front, but maintain their order otherwise.
     */
    fun shiftRight(n: Int): MutableRingList<T> {
        head = head.goSteps(-n)
        return this
    }

    /**
     * Makes [n] elements move from the front to the end, but maintain their order otherwise.
     */
    fun shiftLeft(n: Int): MutableRingList<T> {
        head = head.goSteps(n)
        return this
    }

    /**
     * Swaps the elements at [indexA] and [indexB].
     */
    fun swap(indexA: Int, indexB: Int): MutableRingList<T> {
        swapValues(head.goSteps(indexA), head.goSteps(indexB))
        return this
    }

    /**
     * Swaps the positions of the specified elements [elemA] and [elemB].
     * If not unique, only their first occurrences are swapped.
     */
    fun swap(elemA: T, elemB: T): MutableRingList<T> {
        swapValues(findNode(elemA), findNode(elemB))
        return this
    }

    /**
     * Reverses the elements of the sublist starting at [startIndex] (inclusive) with length [length].
     */
    fun reverseSubList(startIndex: Int, length: Int): MutableRingList<T> {
        var first = head.goSteps(startIndex)
        var last = first.goSteps(length - 1)
        var swaps = length / 2
        while (swaps > 0) {
            swapValues(first, last)
            first = first.next
            last = last.prev
            swaps--
        }
        return this
    }

    /**
     * @return A snapshot of the current state of this [MutableRingList]
     */
    fun toList(): List<T> {
        val list = ArrayList<T>(size)
        var node = head
        repeat(size) {
            list.add(node.value)
            node = node.next
        }
        return list
    }

    override fun toString(): String = toList().toString()

    fun toJoinedString(): String = toList().joinToString("")

    private fun Node<T>.goSteps(n: Int): Node<T> {
        var node = this
        repeat(abs(n) % size) {
            node = (if (n > 0) node.next else node.prev)
        }
        return node
    }

    private fun addAsLast(value: T): Node<T> {
        val newNode = NodeWithValue(value)
        size++
        if (head is EmptyNode) {
            initHead(newNode)
        } else {
            insertBefore(head, newNode)
        }
        return newNode
    }

    private fun insertBefore(
        current: Node<T>,
        newNode: Node<T>
    ) {
        val prev = current.prev
        current.prev = newNode
        newNode.next = current
        newNode.prev = prev
        prev.next = newNode
    }

    private fun initHead(newNode: Node<T>) {
        newNode.prev = newNode
        newNode.next = newNode
        head = newNode
    }

    private fun swapValues(nodeA: Node<T>, nodeB: Node<T>) {
        val tmp = nodeA.value
        nodeA.value = nodeB.value
        nodeB.value = tmp
    }

    private fun findNode(value: T): Node<T> {
        var node = head
        while (node.value != value) {
            node = node.next
            require(node !== head) {
                "$value is not in the list"
            }
        }
        return node
    }

    private data class NodeWithValue<T>(override var value: T) : Node<T> {
        override lateinit var prev: Node<T>
        override lateinit var next: Node<T>
    }

    private class EmptyNode<T>() : Node<T> {
        override var value: T
            get() = throw IllegalStateException("An EmptyNode has no value.")
            set(_) = throw IllegalStateException("An EmptyNode has no value.")
        override var prev: Node<T>
            get() = this
            set(_) = throw IllegalStateException("Setting prev of an EmptyNode is not allowed.")
        override var next: Node<T>
            get() = this
            set(_) = throw IllegalStateException("Setting next of an EmptyNode is not allowed.")
    }
}
