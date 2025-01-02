import Direction.*

fun main() {
    val DAY = "Day06"

    println("$DAY part 1")

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Position>()
        Lab(input).doTheGuardWalk { position, _ ->
            visited.add(position)
            true
        }
        return visited.size
    }

    printAndCheck(
        listOf(
            ".#..",
            "...#",
            ".^..",
            "..#."
        ),
        ::part1, 5
    )

    val testInput = readInput("${DAY}_test")
    printAndCheck(testInput, ::part1, 41)

    val input = readInput(DAY)
    printAndCheck(input, ::part1, 4580)


    println("$DAY part 2")

    fun part2(input: List<String>): Int {
        val lab = Lab(input)
        var loopCount = 0

        lab.freePositions().forEach { additionalObstruction ->
            val visited = mutableSetOf<Pair<Position, Direction>>()
            lab.doTheGuardWalk(additionalObstruction) { position, direction ->
                if (visited.add(position to direction)) {
                    true
                } else {
                    loopCount++
                    false
                }
            }
        }
        return loopCount
    }

    printAndCheck(
        listOf(
            ".#..",
            "...#",
            ".^..",
            "..#."
        ),
        ::part2, 1
    )

    printAndCheck(testInput, ::part2, 6)
    printAndCheck(input, ::part2, 1480)
}

private data class Position(val row: Int, val col: Int) {
    operator fun plus(direction: Direction) = Position(row + direction.row, col + direction.col)
}

private enum class Direction(val row: Int, val col: Int) {
    NORTH(-1, 0),
    EAST(0, +1),
    SOUTH(+1, 0),
    WEST(0, -1),
    ;

    fun turnRight() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

private class Lab(input: List<String>) {

    private val guard = '^'
    private val obstruction = '#'
    private val free = '.'
    private val offMap = ' '

    private val grid = Array(input.size) { CharArray(input[0].length) }

    init {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char -> grid[row][col] = char }
        }
    }

    private fun charAt(position: Position): Char {
        return grid.getOrNull(position.row)?.getOrNull(position.col) ?: offMap
    }

    private fun findTheGuard(): Position {
        for ((rowNo, row) in grid.withIndex()) {
            row.indexOf(guard).let { if (it >= 0) return Position(rowNo, it) }
        }
        error("There was no initial guard position found in the lab.")
    }

    fun freePositions() = sequence {
        grid.forEachIndexed { rowNo, row ->
            row.forEachIndexed { colNo, char ->
                if (char == free) {
                    yield(Position(rowNo, colNo))
                }
            }
        }
    }

    fun doTheGuardWalk(
        additionalObstruction: Position? = null,
        visit: (Position, Direction) -> Boolean,
    ) {
        var direction = NORTH
        var position = findTheGuard()
        var doWalkFurther = true

        while (charAt(position) != offMap && doWalkFurther) {
            doWalkFurther = visit(position, direction)
            val stepAhead = position + direction
            if (charAt(stepAhead) == obstruction || stepAhead == additionalObstruction) {
                direction = direction.turnRight()
            } else {
                position = stepAhead
            }
        }
    }
}
