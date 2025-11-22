package de.ronny_h.aoc.extensions.grids

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import de.ronny_h.aoc.extensions.asList
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class GridTest : StringSpec() {

    private val newLine = '\n'

    init {
        "a grid can be constructed from List of String" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid shouldNotBe null
        }

        "a grid can be constructed with overrides" {
            val grid = object : Grid<Char>(2, 2, ' ', 'x', listOf(Coordinates(1, 1))) {
                override fun Char.toElementType() = this
            }
            grid[0, 0] shouldBe ' '
            grid[0, 1] shouldBe ' '
            grid[1, 0] shouldBe ' '
            grid[1, 1] shouldBe 'x'
        }

        "width and height have the right values" {
            val grid = SimpleCharGrid(listOf("00", "00", "00"))
            grid.height shouldBe 3
            grid.width shouldBe 2
        }

        "charAt returns the input values from the right indices" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid[0, 0] shouldBe '1'
            grid[1, 0] shouldBe '2'
            grid[0, 1] shouldBe '3'
            grid[1, 1] shouldBe '4'
        }

        "toElementType converts the input values" {
            val grid = object : Grid<Int>(listOf("12", "34"), Int.MIN_VALUE) {
                override fun Char.toElementType() = digitToInt()
            }
            grid[0, 0] shouldBe 1
            grid[1, 0] shouldBe 2
            grid[0, 1] shouldBe 3
            grid[1, 1] shouldBe 4
        }

        "charAt with indices returns the same as charAt with Coordinates" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid[0, 0] shouldBe grid.getAt(Coordinates(0, 0))
            grid[0, 1] shouldBe grid.getAt(Coordinates(0, 1))
            grid[1, 0] shouldBe grid.getAt(Coordinates(1, 0))
            grid[1, 1] shouldBe grid.getAt(Coordinates(1, 1))
        }

        "charAt with index out of the input values returns the nullElement of a Char Grid" {
            val grid = SimpleCharGrid(listOf("12", "34"), ' ')
            grid[0, -1] shouldBe ' '
            grid[2, 0] shouldBe ' '
            grid[-1, 1] shouldBe ' '
            grid[2, 2] shouldBe ' '
        }

        "charAt with index out of the input values returns the nullElement of an Int Grid" {
            val grid = object : Grid<Int>(listOf("12", "34"), Int.MIN_VALUE) {
                override fun Char.toElementType() = digitToInt()
            }
            grid[0, -1] shouldBe Int.MIN_VALUE
            grid[2, 0] shouldBe Int.MIN_VALUE
            grid[-1, 1] shouldBe Int.MIN_VALUE
            grid[2, 2] shouldBe Int.MIN_VALUE
        }

        "setAt sets the element at the given coordinates" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid.setAt(Coordinates(0, 1), '5')
            grid[0, 0] shouldBe '1'
            grid[1, 0] shouldBe '2'
            grid[0, 1] shouldBe '5'
            grid[1, 1] shouldBe '4'
        }

        "set with array access sets the element at the given coordinates" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid[0, 1] = '5'
            grid[0, 0] shouldBe '1'
            grid[1, 0] shouldBe '2'
            grid[0, 1] shouldBe '5'
            grid[1, 1] shouldBe '4'
        }

        "subGridAt returns a sub grid at the given coordinates" {
            val input = """
                1234
                5678
                90AB
                CDEF
            """.asList()
            SimpleCharGrid(input).subGridAt(1, 1, width = 2) shouldBe listOf(
                listOf('6', '7'),
                listOf('0', 'A'),
            )
        }

        "forEachIndex calls the provided function on each element in the expected order" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            val chars = grid.forEachIndex { x, y ->
                grid[x, y]
            }.toList()
            chars shouldBe listOf('1', '2', '3', '4')
        }

        "forEachElement calls the provided function on each element in the expected order" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            val strings = grid.forEachElement { x, y, char ->
                "$y,$x:$char"
            }.toList()
            strings shouldBe
                    listOf(
                        "0,0:1",
                        "0,1:2",
                        "1,0:3",
                        "1,1:4",
                    )
        }

        "find() finds all Coordinates" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            forAll(
                row('1', Coordinates(0, 0)),
                row('2', Coordinates(1, 0)),
                row('3', Coordinates(0, 1)),
                row('4', Coordinates(1, 1)),
            ) { char, result ->
                grid.find(char) shouldBe result
            }
        }

        "find() throws a NoSuchElementException if the value cannot be found" {
            shouldThrow<NoSuchElementException> {
                SimpleCharGrid(listOf("12", "34")).find('5')
            }
        }

        "printGrid prints the grid" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid()
            }
            output shouldBe "12${newLine}34$newLine"
        }

        "printGrid overrides specified coordinates" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(1, 0), Coordinates(0, 1))
                )
            }
            output shouldBe "1#${newLine}#4$newLine"
        }

        "printGrid overrides specified coordinates with given overrideChar" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(1, 0), Coordinates(0, 1)),
                    '?'
                )
            }
            output shouldBe "1?${newLine}?4$newLine"
        }

        "printGrid highlights specified coordinates with given highlightDirection's Char" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    highlightPosition = Coordinates(1, 0),
                    highlightDirection = Direction.SOUTH
                )
            }
            output shouldBe "1↓${newLine}34$newLine"
        }

        "printGrid - highlight has higher priority than overrides" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    overrides = setOf(Coordinates(1, 0)),
                    highlightPosition = Coordinates(1, 0),
                    highlightDirection = Direction.SOUTH
                )
            }
            output shouldBe "1↓${newLine}34$newLine"
        }

        "printGrid - highlight without a direction falls back to overrides" {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    overrides = setOf(Coordinates(1, 0)),
                    highlightPosition = Coordinates(1, 0),
                )
            }
            output shouldBe "1#${newLine}34$newLine"
        }

        "toString returns a string representation without any overrides" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid.toString() shouldBe "12${newLine}34"
        }

        "toString with overrides returns a string representation with the given overrides" {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid.toString(setOf(Coordinates(1, 1)), 'o') shouldBe "12${newLine}3o"
        }
    }
}
