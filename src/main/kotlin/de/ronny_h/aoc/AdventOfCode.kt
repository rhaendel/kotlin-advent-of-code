package de.ronny_h.aoc

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class AdventOfCode<T>(val year: Int, val day: Int) {
    abstract fun part1(input: List<String>): T
    abstract fun part2(input: List<String>): T

    private val input = readInput()

    fun run(expectedPart1: T, expectedPart2: T) {
        println("Day $day $year - ${this::class.simpleName} - part 1")
        printAndCheck(::part1, expectedPart1)
        println("Day $day $year - ${this::class.simpleName} - part 2")
        printAndCheck(::part2, expectedPart2)
    }

    private fun readInput() = Path("src/input/$year/Day${paddedDay()}.txt").readText().trim().lines()

    private fun paddedDay(): String = day.toString().padStart(2, '0')

    private fun printAndCheck(block: (List<String>) -> T, expected: T) {
        printAndCheck(measureTimedValue { block(input) }, expected)
    }

    private fun printAndCheck(result: TimedValue<T>, expected: T) {
        println("result: ${result.value}, expected: $expected, took: ${result.duration}")
        check(result.value == expected)
    }
}
