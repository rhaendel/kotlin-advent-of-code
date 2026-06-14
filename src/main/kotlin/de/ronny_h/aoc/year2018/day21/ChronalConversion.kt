package de.ronny_h.aoc.year2018.day21

import de.ronny_h.aoc.AdventOfCode

fun main() = ChronalConversion().run(9566170, 0)

class ChronalConversion : AdventOfCode<Int>(2018, 21) {

    override fun part1(input: List<String>): Int {
        return ActivationSystem().run()
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class ActivationSystem {
    private var reg2 = 0
    private var reg3 = 0
    private var reg4 = 0

    fun run(): Int {
        reg3 = 65536
        reg4 = 4332021

        while (true) {
            reg2 = reg3 and 255
            reg4 += reg2
            reg4 = reg4 and 16777215
            reg4 *= 65899
            reg4 = reg4 and 16777215

            if (reg3 < 256) {
                break
            }

            reg3 /= 256
        }
        return reg4
    }
}
