package de.ronny_h.extensions

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class GridTest : StringSpec() {

    private fun simpleCharGridOf(input: List<String>) = object : Grid<Char>(input) {
        override val nullElement = ' '
        override fun Char.toElementType() = this
    }

    private val newLine: String = System.lineSeparator()

    init {

        "a grid can be constructed from List of String" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            grid shouldNotBe null
        }

        "width and height have the right values" {
            val grid = simpleCharGridOf(listOf("00", "00", "00"))
            grid.height shouldBe 3
            grid.width shouldBe 2
        }

        "charAt returns the input values from the right indices" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            grid[0, 0] shouldBe '1'
            grid[0, 1] shouldBe '2'
            grid[1, 0] shouldBe '3'
            grid[1, 1] shouldBe '4'
        }

        "toElementType converts the input values" {
            val grid = object : Grid<Int>(listOf("12", "34")) {
                override val nullElement = Int.MIN_VALUE
                override fun Char.toElementType() = digitToInt()
            }
            grid[0, 0] shouldBe 1
            grid[0, 1] shouldBe 2
            grid[1, 0] shouldBe 3
            grid[1, 1] shouldBe 4
        }

        "charAt with indices returns the same as charAt with Coordinates" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            grid[0, 0] shouldBe grid.getAt(Coordinates(0, 0))
            grid[0, 1] shouldBe grid.getAt(Coordinates(0, 1))
            grid[1, 0] shouldBe grid.getAt(Coordinates(1, 0))
            grid[1, 1] shouldBe grid.getAt(Coordinates(1, 1))
        }

        "charAt with index out of the input values returns the nullElement of a Char Grid" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            grid[-1, 0] shouldBe ' '
            grid[0, 2] shouldBe ' '
            grid[1, -1] shouldBe ' '
            grid[2, 2] shouldBe ' '
        }

        "charAt with index out of the input values returns the nullElement of an Int Grid" {
            val grid = object : Grid<Int>(listOf("12", "34")) {
                override val nullElement = Int.MIN_VALUE
                override fun Char.toElementType() = digitToInt()
            }
            grid[-1, 0] shouldBe Int.MIN_VALUE
            grid[0, 2] shouldBe Int.MIN_VALUE
            grid[1, -1] shouldBe Int.MIN_VALUE
            grid[2, 2] shouldBe Int.MIN_VALUE
        }

        "forEachIndex calls the provided function on each element in the expected order" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            val chars = grid.forEachIndex { row, column ->
                grid[row, column]
            }.toList()
            chars shouldBe listOf('1', '2', '3', '4')
        }

        "forEachElement calls the provided function on each element in the expected order" {
            val grid = simpleCharGridOf(listOf("12", "34"))
            val strings = grid.forEachElement { row, column, char ->
                "$row,$column:$char"
            }.toList()
            strings shouldBe
                    listOf(
                        "0,0:1",
                        "0,1:2",
                        "1,0:3",
                        "1,1:4",
                    )
        }

        "printGrid prints the grid" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid()
            }
            output shouldBe "12${newLine}34$newLine"
        }

        "printGrid overrides specified coordinates" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(0, 1), Coordinates(1, 0))
                )
            }
            output shouldBe "1#${newLine}#4$newLine"
        }

        "printGrid overrides specified coordinates with given overrideChar" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(0, 1), Coordinates(1, 0)),
                    '?'
                )
            }
            output shouldBe "1?${newLine}?4$newLine"
        }

        "printGrid highlights specified coordinates with given highlightDirection's Char" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid(
                    highlightPosition = Coordinates(0, 1),
                    highlightDirection = Direction.SOUTH
                )
            }
            output shouldBe "1↓${newLine}34$newLine"
        }

        "printGrid - highlight has higher priority than overrides" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid(
                    overrides = setOf(Coordinates(0, 1)),
                    highlightPosition = Coordinates(0, 1),
                    highlightDirection = Direction.SOUTH
                )
            }
            output shouldBe "1↓${newLine}34$newLine"
        }

        "printGrid - highlight without a direction falls back to overrides" {
            val output = tapSystemOut {
                val grid = simpleCharGridOf(listOf("12", "34"))
                grid.printGrid(
                    overrides = setOf(Coordinates(0, 1)),
                    highlightPosition = Coordinates(0, 1),
                )
            }
            output shouldBe "1#${newLine}34$newLine"
        }

    }
}
