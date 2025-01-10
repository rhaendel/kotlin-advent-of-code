import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Grid
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

private class CityMap(input: List<String>) : Grid(input) {

    override val nullElement = '.'

    private val colIndices = input[0].indices
    private val rowIndices = input.indices

    private val antennas: Map<Char, List<Coordinates>> = buildMap<Char, MutableList<Coordinates>> {
        super.forEach { row, col ->
            val char = charAt(row, col)
            if (char != nullElement) {
                getOrPut(char, ::mutableListOf).add(Coordinates(row, col))
            }
        }.last()
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
        forEach { r, c ->
            val char = charAt(r, c)
            if (antinodes.contains(Coordinates(r, c))) {
                print("#")
            } else {
                print(char)
            }
            if (c == width - 1) println()
        }.last()
    }
}
