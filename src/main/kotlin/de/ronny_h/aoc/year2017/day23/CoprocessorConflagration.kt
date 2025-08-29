package de.ronny_h.aoc.year2017.day23

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2017.day18.Program
import kotlinx.coroutines.runBlocking

fun main() = CoprocessorConflagration().run(4225, 0)

class CoprocessorConflagration : AdventOfCode<Long>(2017, 23) {
    override fun part1(input: List<String>): Long = runBlocking {
        val program = Program(input)
        program.run()
        return@runBlocking program.getNumberOfMultiplies()
    }

    override fun part2(input: List<String>): Long = runBlocking {
        val program = Program(input, presetRegisters = mapOf("a" to 1))
        program.run()
        return@runBlocking program.getRegisterValue("h")
    }
}

private fun partTwoAsKotlin(): Int {
    val a = 1
    var d = 0
    var e = 0
    var f = 0
    var g = 0
    var h = 0

    var b = 67
    var c = b
    if (a != 0) {
        b *= 100
        b += 100000
        c = b
        c += 17000
    }
    while (true) {
        f = 1
        d = 2
        do {
            e = 2
            do {
                g = d
                g *= e
                g -= b
                if (g == 0) {
                    f = 0
                }
                e++
                g = e
                g -= b
            } while (g != 0)
            d++
            g = d
            g -= b
        } while (g != 0)
        if (f == 0) {
            h++
        }
        g = b
        g -= c
        if (g == 0) {
            return h
        }
        b += 17
    }
}
