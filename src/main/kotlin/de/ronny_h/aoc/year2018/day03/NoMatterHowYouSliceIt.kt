package de.ronny_h.aoc.year2018.day03

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.grids.Coordinates

fun main() = NoMatterHowYouSliceIt().run(107820, 661)

class NoMatterHowYouSliceIt : AdventOfCode<Int>(2018, 3) {
    override fun part1(input: List<String>): Int = ClaimedAreas(input).numberOfOverlaps()
    override fun part2(input: List<String>): Int = ClaimedAreas(input).findTheOneThatDoesntOverlap().id
}

data class Claim(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int)

private val claimPattern = """#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""".toPattern()

fun List<String>.parseClaims() = map {
    val matcher = claimPattern.matcher(it)
    matcher.find()
    check(matcher.groupCount() == 5)
    Claim(
        id = matcher.group(1).toInt(),
        x = matcher.group(2).toInt(),
        y = matcher.group(3).toInt(),
        width = matcher.group(4).toInt(),
        height = matcher.group(5).toInt(),
    )
}

class ClaimedAreas(input: List<String>) {
    private val claims = input.parseClaims()
    private val claimedAreas = mutableMapOf<Coordinates, Int>().withDefault { 0 }

    init {
        claims.forEach {
            for (x in it.x..<it.x + it.width) {
                for (y in it.y..<it.y + it.height) {
                    claimedAreas[Coordinates(x, y)] = claimedAreas.getValue(Coordinates(x, y)) + 1
                }
            }
        }
    }

    fun numberOfOverlaps() = claimedAreas.values.count { it > 1 }

    fun findTheOneThatDoesntOverlap(): Claim = claims
        .filter {
            for (x in it.x..<it.x + it.width) {
                for (y in it.y..<it.y + it.height) {
                    if (claimedAreas.getValue(Coordinates(x, y)) > 1) return@filter false
                }
            }
            true
        }
        .single()
}
