package de.ronny_h.aoc

import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class AdventOfCode<T>(val year: Int, val day: Int) {
    abstract fun part1(input: List<String>): T
    abstract fun part2(input: List<String>): T

    fun run(expectedPart1: T, expectedPart2: T) {
        val input = readInput()
        println("Day $day $year - ${this::class.simpleName} - part 1")
        printAndCheck(input, ::part1, expectedPart1)
        println()
        println("Day $day $year - ${this::class.simpleName} - part 2")
        printAndCheck(input, ::part2, expectedPart2)
    }

    fun readInput(): List<String> {
        val inputFile = "/$year/Day${paddedDay()}.txt"
        return AdventOfCode::class.java.getResource(inputFile)?.readText()?.trim()?.lines()
            ?: error("The input file for day $day, year $year could not be found. Expected at '$inputFile' in the resources folder.")
    }

    fun paddedDay(): String = day.toString().padStart(2, '0')

    private fun printAndCheck(input: List<String>, block: (List<String>) -> T, expected: T) {
        printAndCheck(measureTimedValue { block(input) }, expected)
    }

    private fun printAndCheck(result: TimedValue<T>, expected: T) {
        println("result: ${result.value}, expected: $expected, took: ${result.duration}")
        check(result.value == expected)
    }
}
