import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.combinations
import de.ronny_h.extensions.times

fun main() {
    val day = "Day08"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val map = CityMap(input)
        return map.collectAntinodes()
    }

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
}

private class CityMap(input: List<String>) {

    private val empty = '.'

    private val colIndices = input[0].indices
    private val rowIndices = input.indices

    private val antennas: Map<Char, List<Coordinates>> = HashMap<Char, MutableList<Coordinates>>().apply {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != empty) {
                    getOrPut(char, ::mutableListOf).add(Coordinates(row, col))
                }
            }
        }
    }

    fun collectAntinodes(): Int {
        val antinodes = HashSet<Coordinates>()

        antennas.entries.forEach { entry ->
            val antennasOfSameFrequency = entry.value
            antennasOfSameFrequency.combinations().forEach {
                // add the difference vector to the second: second + (second - first)
                val antinode = 2 * it.second - it.first
                if (antinode.isOnTheMap()) {
                    antinodes.add(antinode)
                }
            }
        }

        return antinodes.size
    }

    private fun Coordinates.isOnTheMap() = col in colIndices && row in rowIndices
}
