package de.ronny_h.aoc.year2018.day21

import de.ronny_h.aoc.AdventOfCode

fun main() = ChronalConversion().run(9566170, 13192622)

class ChronalConversion : AdventOfCode<Int>(2018, 21) {

    override fun part1(input: List<String>): Int {
        return ActivationSystem().run()
    }

    override fun part2(input: List<String>): Int {
        return ActivationSystem().run(false)
    }
}

class ActivationSystem {
    private var reg2 = 0
    private var reg3 = 0
    private var reg4 = 0

    fun run(returnFirst: Boolean = true): Int {
        reg4 = 0

        val set = mutableSetOf<Int>()
        var last = 0
        do {
            reg3 = reg4 or 65536
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
            if (returnFirst) {
                return reg4
            }
            if (!set.add(reg4)) {
                // the first time we see a value again, the cycle starts from the beginning
                // -> the last value was the lowest non-negative integer value for register 0 that causes the program to
                //    halt after executing the most instructions
                return last
            }
            last = reg4
        } while (true)
    }
}
