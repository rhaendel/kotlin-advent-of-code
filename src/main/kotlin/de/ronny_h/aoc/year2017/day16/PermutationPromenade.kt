package de.ronny_h.aoc.year2017.day16

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2017.day16.DanceMove.*
import de.ronny_h.aoc.year2017.day16.MutableRingList.Companion.mutableRingListOf

fun main() = PermutationPromenade().run("kgdchlfniambejop", "fjpmholcibdgeakn")

class PermutationPromenade : AdventOfCode<String>(2017, 16) {
    override fun part1(input: List<String>): String = doTheDance("abcdefghijklmnop", input)
    override fun part2(input: List<String>): String = doTheDance("abcdefghijklmnop", input, 1_000_000_000)

    fun doTheDance(programsLine: String, input: List<String>, times: Int = 1): String {
        val programs = mutableRingListOf(programsLine)
        val danceMoves = input.parseDanceMoves()

        var cycleTime = 0
        while (cycleTime < times) {
            danceMoves.forEach { it.doTheMove(programs) }
            cycleTime++
            if (programs.toJoinedString() == programsLine) {
                break
            }
        }

        val remaining = times % cycleTime
        repeat(remaining) {
            danceMoves.forEach { it.doTheMove(programs) }
        }
        return programs.toJoinedString()
    }
}

sealed interface DanceMove {
    fun doTheMove(programs: MutableRingList<Char>)

    data class Spin(val x: Int) : DanceMove {
        override fun doTheMove(programs: MutableRingList<Char>) {
            programs.shiftRight(x)
        }
    }

    data class Exchange(val posA: Int, val posB: Int) : DanceMove {
        override fun doTheMove(programs: MutableRingList<Char>) {
            programs.swap(posA, posB)
        }
    }

    data class Partner(val programA: Char, val programB: Char) : DanceMove {
        override fun doTheMove(programs: MutableRingList<Char>) {
            programs.swap(programA, programB)
        }
    }
}

fun List<String>.parseDanceMoves() = single().split(",").map {
    when (it.first()) {
        's' -> Spin(it.substringAfter("s").toInt())
        'x' -> {
            val (a, b) = it.substringAfter("x").split("/")
            Exchange(a.toInt(), b.toInt())
        }

        'p' -> {
            val (a, b) = it.substringAfter("p").split("/")
            Partner(a.first(), b.first())
        }

        else -> error("unknown move: $it")
    }
}

class MutableRingList<T>(initialList: List<T>) {
    private val list: MutableList<T> = initialList.toMutableList()
    private var offset = 0

    companion object {
        fun <T> mutableRingListOf(vararg elements: T): MutableRingList<T> = MutableRingList(elements.toList())
        fun mutableRingListOf(elements: String): MutableRingList<Char> = MutableRingList(elements.toList())
    }

    operator fun get(index: Int) = list[(index + offset) % list.size]

    operator fun set(index: Int, value: T) {
        list[(index + offset) % list.size] = value
    }

    /**
     * Makes [n] elements move from the end to the front, but maintain their order otherwise.
     */
    fun shiftRight(n: Int): MutableRingList<T> {
        check(n <= list.size) { "the number of items to spin must be smaller than or equal to the list's length" }
        offset = (offset + list.size - n) % list.size
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

    /**
     * @return A snapshot of the current state of this [MutableRingList]
     */
    fun toList(): List<T> = list.subList(offset, list.size) + list.subList(0, offset)

    override fun toString(): String = toList().toString()

    fun toJoinedString(): String = toList().joinToString("")
}
