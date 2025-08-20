package de.ronny_h.aoc.year2017.day16

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.MutableRingList
import de.ronny_h.aoc.extensions.collections.MutableRingList.Companion.mutableRingListOf
import de.ronny_h.aoc.year2017.day16.DanceMove.*

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
