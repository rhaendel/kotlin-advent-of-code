import de.ronny_h.extensions.Coordinates
import de.ronny_h.extensions.Direction
import de.ronny_h.extensions.Grid

fun main() {
    val day = "Day15"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val warehouse = NormalWarehouse(input.takeWhile { it.isNotBlank() })
        warehouse.printGrid()

        val movements = input.toDay15Movements()

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

fun List<String>.toDay15Movements(): List<Direction> = takeLastWhile { it.isNotBlank() }
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


abstract class Warehouse(input: List<String>) : Grid<Char>(input, '#') {
    protected val wall = nullElement
    protected val robot = '@'
    protected val free = '.'

    override fun Char.toElementType(): Char = this

    protected abstract val leftGoodsChar: Char
    protected abstract fun tryToMove(from: Coordinates, direction: Direction, thing: Char): Boolean

    fun moveRobot(movements: List<Direction>) {
        movements.fold(findTheRobot()) { position, direction ->
            if (tryToMove(position, direction, robot)) {
                position + direction
            } else {
                position
            }
        }
    }

    private fun findTheRobot(): Coordinates = forEachElement { row, col, element ->
        if (element == robot) Coordinates(row, col) else null
    }.filterNotNull().first()

    // GPS = Goods Positioning System
    fun sumGPSCoordinates(): Int = forEachElement { row, col, element ->
        if (element == leftGoodsChar) {
            100 * row + col
        } else {
            0
        }
    }.sum()
}

private class NormalWarehouse(input: List<String>) : Warehouse(input) {
    private val goods = 'O'
    override val leftGoodsChar = goods

    override fun tryToMove(from: Coordinates, direction: Direction, thing: Char): Boolean {
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
}
