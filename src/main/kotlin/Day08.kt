import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.combinations

fun main() {
    val day = "Day08"

    println("$day part 1")

    fun part1(input: List<String>) = CityMap(input).collectAntinodes().size

    printAndCheck(
        """
            A...b
            ....b
            ..A..
            ..cc.
            .....
        """.trimIndent().lines(),
        ::part1, 4
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 14)

    val input = readInput(day)
    printAndCheck(input, ::part1, 214)


    println("$day part 2")

    fun part2(input: List<String>): Int {
        val map = CityMap(input)
        // map.printGrid()
        val antinodes = map.collectAntinodes(withResonantHarmonics = true)
        // map.printGrid(antinodes)
        return antinodes.size
    }

    printAndCheck(
        """
            A...b
            ....b
            ..A..
            ..cc.
            .....
        """.trimIndent().lines(),
        ::part2, 11
    )

    printAndCheck(testInput, ::part2, 34)
    printAndCheck(input, ::part2, 809)
}

private class CityMap(input: List<String>) {

    private val empty = '.'

    private val colIndices = input[0].indices
    private val rowIndices = input.indices

    // just for printing; the algorithm itself does not need the complete grid
    private val grid = Array(input.size) { CharArray(input[0].length) }

    private val antennas: Map<Char, List<Coordinates>> = buildMap<Char, MutableList<Coordinates>> {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                grid[row][col] = char
                if (char != empty) {
                    getOrPut(char, ::mutableListOf).add(Coordinates(row, col))
                }
            }
        }
    }

    fun collectAntinodes(withResonantHarmonics: Boolean = false): HashSet<Coordinates> {
        val antinodes = HashSet<Coordinates>()

        antennas.entries.forEach { entry ->
            val antennasOfSameFrequency = entry.value
            if (withResonantHarmonics) {
                antinodes.addAll(antennasOfSameFrequency)
            }
            antennasOfSameFrequency.combinations().forEach {
                val diff = it.second - it.first
                var antinode = it.second + diff
                if (withResonantHarmonics) {
                    while (antinode.isOnTheMap()) {
                        antinodes.add(antinode)
                        antinode += diff
                    }
                } else {
                    if (antinode.isOnTheMap()) {
                        antinodes.add(antinode)
                    }
                }
            }
        }

        return antinodes
    }

    private fun Coordinates.isOnTheMap() = col in colIndices && row in rowIndices

    fun printGrid(antinodes: Set<Coordinates> = setOf()) {
        grid.forEachIndexed { r, row ->
            row.forEachIndexed { c, char ->
                if (antinodes.contains(Coordinates(r, c))) {
                    print("#")
                } else {
                    print(char)
                }
            }
            println("")
        }
    }
}
