package de.ronny_h.aoc.year2015.day13

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.permutationsOf

fun main() = KnightsOfTheDinnerTable().run(664, 640)

class KnightsOfTheDinnerTable : AdventOfCode<Int>(2015, 13) {

    override fun part1(input: List<String>): Int {
        return input.parse().maxHappiness()
    }

    private fun List<HappinessPotential>.maxHappiness(): Int {
        val happinessLookup = associate { Pair(it.person, it.nextTo) to it.happiness }
        fun happinessChange(a: String, b: String) = happinessLookup[a to b]!! + happinessLookup[b to a]!!

        val allGuests = map { it.person }.toSet().toList()
        return permutationsOf(allGuests).maxOf { guests ->
            var happiness = 0
            guests.windowed(2) { (person, nextTo) ->
                happiness += happinessChange(person, nextTo)
            }
            happiness += happinessChange(guests.first(), guests.last())
            happiness
        }
    }

    override fun part2(input: List<String>): Int {
        val happinessPotentials = input.parse()
        val allGuests = happinessPotentials.map { it.person }.toSet().toList()

        val myHappinessPotential = allGuests.flatMap {
            listOf(
                HappinessPotential("me", it, 0),
                HappinessPotential(it, "me", 0)
            )
        }
        return (happinessPotentials + myHappinessPotential).maxHappiness()
    }
}

data class HappinessPotential(val person: String, val nextTo: String, val happiness: Int)

fun List<String>.parse() = this.map {
    val (person, remainder) = it.split(" would ")
    val sign = when {
        remainder.startsWith("gain") -> 1
        else -> -1
    }
    val (happiness, nextTo) = remainder.substring(5, remainder.lastIndex)
        .split(" happiness units by sitting next to ")
    HappinessPotential(person, nextTo, sign * happiness.toInt())
}
