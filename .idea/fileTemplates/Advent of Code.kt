#set( $Integer = 0 )
#set( $DayNumber = $Integer.parseInt($Day) )
package de.ronny_h.aoc.year${Year}.day$Day

import de.ronny_h.aoc.AdventOfCode

fun main() = Day$Day().run(0, 0)

class Day$Day : AdventOfCode<Int>($Year, $DayNumber) {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}
