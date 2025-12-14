package de.ronny_h.aoc.year2025.day12

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.collections.split
import de.ronny_h.aoc.extensions.grids.SimpleCharGrid

fun main() = ChristmasTreeFarm().run(454, 0)

class ChristmasTreeFarm : AdventOfCode<Int>(2025, 12) {
    override fun part1(input: List<String>): Int {
        val presents = input.parsePresents()
        return presents.regions.count { PresentsSpace(it, presents.shapes).allShapesFit() }
    }

    override fun part2(input: List<String>): Int = 0
}

fun List<String>.parsePresents(): Presents {
    val chunks = split()
    val shapes = chunks.dropLast(1).map(List<String>::parseShape)
    val regions = chunks.last().map(String::parseRegion)
    return Presents(shapes, regions)
}

// 0:
// ###
// ##.
// ##.
private fun List<String>.parseShape() = PresentShape(
    index = first().dropLast(1).toInt(),
    input = drop(1),
)

// 4x4: 0 0 0 0 2 0
private fun String.parseRegion(): Region {
    val (dimensions, presents) = split(": ")
    val (width, length) = dimensions.split("x").map(String::toInt)
    return Region(width, length, presents.split(" ").map(String::toInt))
}

data class Presents(val shapes: List<PresentShape>, val regions: List<Region>)

data class Region(val width: Int, val length: Int, val presents: List<Int>) {
    val area: Int
        get() = width * length
}

class PresentShape(private val index: Int, input: List<String>) : SimpleCharGrid(input, nullElement = BACKGROUND) {

    companion object {
        const val BACKGROUND = '.'
    }

    val numberOfTiles
        get() = forEachElement { _, _, element ->
            if (element != BACKGROUND) 1 else 0
        }.sum()

    override fun equals(other: Any?): Boolean {
        if (other !is PresentShape) return false
        return index == other.index && toString() == other.toString()
    }

    override fun hashCode(): Int = index
}

class PresentsSpace(private val region: Region, private val shapes: List<PresentShape>) {

    // Note that this is just an approximation producing an upper bound.
    // Luckily this is enough to solve this year's last puzzle.
    fun allShapesFit(): Boolean = region
        .presents
        .mapIndexed { i, times -> shapes[i] to times }
        .sumOf { (shape, times) -> shape.numberOfTiles * times } <= region.area

}
