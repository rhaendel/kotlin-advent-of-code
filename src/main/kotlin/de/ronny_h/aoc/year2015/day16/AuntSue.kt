package de.ronny_h.aoc.year2015.day16

import de.ronny_h.aoc.AdventOfCode

fun main() = AuntSue().run(373, 260)

class AuntSue : AdventOfCode<Int>(2015, 16) {
    private val attributesFromTickerTape = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1,
    )

    override fun part1(input: List<String>): Int = input
        .parseSues()
        .filter { sue -> sue.attributes.all { attributesFromTickerTape.get(it.key) == it.value } }
        .single()
        .number

    override fun part2(input: List<String>): Int = input
        .parseSues()
        .filter { sue ->
            sue.attributes.all {
                when (it.key) {
                    "cats", "trees" -> attributesFromTickerTape.getValue(it.key) < it.value
                    "pomeranians", "goldfish" -> attributesFromTickerTape.getValue(it.key) > it.value
                    else -> attributesFromTickerTape.getValue(it.key) == it.value
                }
            }
        }
        .single()
        .number
}

fun List<String>.parseSues() = map {
    val (number, attributes) = it.substringAfter("Sue ").split(": ", limit = 2)
    val attributeMap = attributes.split(", ").map { it.split(": ") }.associate { it[0] to it[1].toInt() }
    Sue(number.toInt(), attributeMap)
}

data class Sue(
    val number: Int,
    val attributes: Map<String, Int>,
)
