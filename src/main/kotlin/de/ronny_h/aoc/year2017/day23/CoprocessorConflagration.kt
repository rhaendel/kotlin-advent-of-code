package de.ronny_h.aoc.year2017.day23

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2017.day18.Program
import kotlinx.coroutines.runBlocking

fun main() = CoprocessorConflagration().run(4225, 905)

class CoprocessorConflagration : AdventOfCode<Long>(2017, 23) {
    override fun part1(input: List<String>): Long = runBlocking {
        val program = Program(input)
        program.run()
        return@runBlocking program.getNumberOfMultiplies()
    }

    override fun part2(input: List<String>): Long = partTwoAsKotlinOptimized().toLong()
}

// This is an intermediate step of simplifying the program given in my puzzle input.
// It keeps the original variable names but uses a higher level of language features
// that make it easier to see what the program actually does.
// It does not terminate in reasonable time and is not tested. The optimized function
// below is my final solution.
private fun partTwoAsKotlin(): Int {
    var f: Int
    var h = 0

    for (b in 106700..106700 + 17000 step 17) { // 1000 iterations
        f = 1
        for (d in 2..b) {
            for (e in 2..b) {
                if (b == d * e) {
                    f = 0
                }
            }
        }
        if (f == 0) {
            // f is 0 if b can be written as d * e (with d,e in {2..b})
            // -> h counts non-prime numbers between 106700 and 123700 in steps of 17
            h++
        }
    }
    return h
}

private fun partTwoAsKotlinOptimized(): Int {
    var nonPrimesCount = 0
    for (number in 106700..106700 + 17000 step 17) {
        for (divisor in 2..number / 2) {
            if (number % divisor == 0) {
                nonPrimesCount++
                break
            }
        }
    }
    return nonPrimesCount
}
