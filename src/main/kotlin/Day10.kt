import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction.*

fun main() {
    val day = "Day10"

    println("$day part 1")

    fun part1(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.score }

    printAndCheck(
        """
            0123
            1234
            8765
            9876
        """.trimIndent().lines(),
        ::part1, 1
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 36)

    val input = readInput(day)
    printAndCheck(input, ::part1, 816)


    println("$day part 2")

    fun part2(input: List<String>) = TopographicMap(input)
        .searchTrailheads()
        .sumOf { it.rating }

    printAndCheck(
        """
            .....0.
            ..4321.
            ..5..2.
            ..6543.
            ..7..4.
            ..8765.
            ..9....
        """.trimIndent().lines(),
        ::part2, 3
    )

    printAndCheck(testInput, ::part2, 81)
    printAndCheck(input, ::part2, 1960)
}

private class TopographicMap(input: List<String>) {

    private val offMap = Integer.MIN_VALUE

    private val grid = Array(input.size) { IntArray(input[0].length) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = if (char.isDigit()) char.digitToInt() else offMap }
        }
    }

    private fun heights(): Sequence<Pair<Coordinates, Int>> = sequence {
        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, height ->
                yield(Coordinates(rowIndex, colIndex) to height)
            }
        }
    }

    private fun heightAt(coordinates: Coordinates): Int {
        return grid.getOrNull(coordinates.row)?.getOrNull(coordinates.col) ?: offMap
    }

    fun searchTrailheads(): List<Trailhead> {
        val startPositions = heights().filter { it.second == 0 }
        return startPositions
            .map {
                val trailhead = Trailhead(it.first)
                if (trailhead.findPathsToNines(::heightAt)) {
                    trailhead
                } else {
                    null
                }
            }
            .mapNotNull { it }
            .toList()
    }

}

class Trailhead(private val startPosition: Coordinates) {
    val score: Int
        get() = reachableNines.size

    var rating: Int = 0
        private set

    private val reachableNines = mutableSetOf<Coordinates>()

    private fun addReachable(coordinates: Coordinates) {
        reachableNines.add(coordinates)
        rating++
    }

    fun findPathsToNines(heightAt: (Coordinates) -> Int): Boolean {
        findPathsToNines(startPosition, -1, heightAt)
        return score > 0
    }

    private fun findPathsToNines(coordinates: Coordinates, lastHeight: Int, heightAt: (Coordinates) -> Int) {
        val height = heightAt(coordinates)
        // println("$coordinates height: $height lastHeight: $lastHeight")
        if (height != lastHeight + 1) {
            return
        }
        if (height == 9) {
            addReachable(coordinates)
            return
        }
        findPathsToNines(coordinates + NORTH, height, heightAt)
        findPathsToNines(coordinates + EAST, height, heightAt)
        findPathsToNines(coordinates + SOUTH, height, heightAt)
        findPathsToNines(coordinates + WEST, height, heightAt)
    }
}
