package de.ronny_h.aoc.year2015.day20

import de.ronny_h.aoc.AdventOfCode

fun main() = InfiniteElvesAndInfiniteHouses().run(776160, 786240)

class InfiniteElvesAndInfiniteHouses : AdventOfCode<Int>(2015, 20) {

    override fun part1(input: List<String>): Int {
        val s = input.single().toInt() / 10
        var houseNumber = s / 5
        var sum = 0
        println("Searching for the hous with at least $s presents, starting at $houseNumber")
        while (sum < s) {
            houseNumber++
            sum = numberOfPresentsForHouse(houseNumber)
            if (houseNumber % 10000 == 0) {
                println("at house number $houseNumber: $sum presents")
            }
        }
        return houseNumber
    }

    override fun part2(input: List<String>): Int {
        val s = input.single().toInt()
        var houseNumber = s / 50
        var sum = 0
        println("Searching for the hous with at least $s presents, starting at $houseNumber")
        while (sum < s) {
            houseNumber++
            sum = numberOfPresentsForHouseWithMax50Visits(houseNumber) * 11
            if (houseNumber % 10000 == 0) {
                println("at house number $houseNumber: $sum presents")
            }
        }
        return houseNumber
    }
}

// this is an implementation of the Divisor function:
// https://en.wikipedia.org/wiki/Divisor_function
fun numberOfPresentsForHouse(number: Int) = (1..number)
    .sumOf { if (number % it == 0) it else 0 }

fun numberOfPresentsForHouseWithMax50Visits(number: Int) = (1..number)
    .sumOf { if (number % it == 0 && number / it <= 50) it else 0 }
