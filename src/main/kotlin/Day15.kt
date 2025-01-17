import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction
import de.ronny_h.extensions.Grid

fun main() {
    val day = "Day15"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val warehouse = Warehouse(input.takeWhile { it.isNotBlank() })
        warehouse.printGrid()

        val movements = input
            .takeLastWhile { it.isNotBlank() }
            .joinToString(separator = "")
            .map {
                when (it) {
                    '^' -> Direction.NORTH
                    '>' -> Direction.EAST
                    'v' -> Direction.SOUTH
                    '<' -> Direction.WEST
                    else -> error("Unexpected direction Char: $it")
                }
            }

        warehouse.moveRobot(movements)
        warehouse.printGrid()

        return warehouse.sumGPSCoordinates()
    }

    printAndCheck(
        """
            ########
            #..O.O.#
            ##@.O..#
            #...O..#
            #.#.O..#
            #...O..#
            #......#
            ########
            
            <^^>>>vv<v>>v<<
        """.trimIndent().lines(),
        ::part1, 2028
    )

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 10092)

    val input = readInput(day)
    printAndCheck(input, ::part1, 1463715)
}

private class Warehouse(input: List<String>) : Grid<Char>(input) {
    private val wall = '#'
    private val robot = '@'
    private val free = '.'
    private val goods = 'O'

    override val nullElement = wall
    override fun Char.toElementType(): Char = this

    fun moveRobot(movements: List<Direction>) {
        movements.fold(findTheRobot()) { position, direction ->
            if (tryToMove(position, direction, robot)) {
                position + direction
            } else {
                position
            }
        }
    }

    private fun tryToMove(from: Coordinates, direction: Direction, thing: Char): Boolean {
        val target = from + direction
        if (getAt(target) == free) {
            setAt(target, thing)
            setAt(from, free)
            return true
        }
        if (getAt(target) == wall) {
            return false
        }
        check(getAt(target) == goods)
        if (tryToMove(target, direction, goods)) {
            setAt(target, thing)
            setAt(from, free)
            return true
        }
        return false
    }

    private fun findTheRobot(): Coordinates = forEachElement { row, col, element ->
        if (element == robot) Coordinates(row, col) else null
    }.filterNotNull().first()

    fun sumGPSCoordinates(): Int = forEachElement { row, col, element ->
        if (element == goods) {
            100 * row + col
        } else {
            0
        }
    }.sum()

}