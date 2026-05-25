package de.ronny_h.aoc.extensions.grids

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import de.infix.testBalloon.framework.core.TestConfig
import de.infix.testBalloon.framework.core.TestConfig.Invocation.Sequential
import de.infix.testBalloon.framework.core.invocation
import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

val GridTest by testSuite {

    val newLine = '\n'

    test("a grid can be constructed from List of String") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        grid shouldNotBe null
    }

    test("a grid can be constructed with overrides") {
        val grid = object : Grid<Char>(2, 2, ' ', 'x', listOf(Coordinates(1, 1))) {
            override fun Char.toElementType() = this
        }
        grid[0, 0] shouldBe ' '
        grid[0, 1] shouldBe ' '
        grid[1, 0] shouldBe ' '
        grid[1, 1] shouldBe 'x'
    }

    test("width and height have the right values") {
        val grid = SimpleCharGrid(listOf("00", "00", "00"))
        grid.height shouldBe 3
        grid.width shouldBe 2
    }

    test("get returns the input values from the right indices") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        grid[0, 0] shouldBe '1'
        grid[1, 0] shouldBe '2'
        grid[0, 1] shouldBe '3'
        grid[1, 1] shouldBe '4'
    }

    test("toElementType converts the input values") {
        val grid = object : Grid<Int>(listOf("12", "34"), Int.MIN_VALUE) {
            override fun Char.toElementType() = digitToInt()
        }
        grid[0, 0] shouldBe 1
        grid[1, 0] shouldBe 2
        grid[0, 1] shouldBe 3
        grid[1, 1] shouldBe 4
    }

    test("get with indices returns the same as get with Coordinates") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        grid[0, 0] shouldBe grid[Coordinates(0, 0)]
        grid[0, 1] shouldBe grid[Coordinates(0, 1)]
        grid[1, 0] shouldBe grid[Coordinates(1, 0)]
        grid[1, 1] shouldBe grid[Coordinates(1, 1)]
    }

    test("get with index out of the input values returns the nullElement of a Char Grid") {
        val grid = SimpleCharGrid(listOf("12", "34"), ' ')
        grid[0, -1] shouldBe ' '
        grid[2, 0] shouldBe ' '
        grid[-1, 1] shouldBe ' '
        grid[2, 2] shouldBe ' '
    }

    test("get with index out of the input values returns the nullElement of an Int Grid") {
        val grid = object : Grid<Int>(listOf("12", "34"), Int.MIN_VALUE) {
            override fun Char.toElementType() = digitToInt()
        }
        grid[0, -1] shouldBe Int.MIN_VALUE
        grid[2, 0] shouldBe Int.MIN_VALUE
        grid[-1, 1] shouldBe Int.MIN_VALUE
        grid[2, 2] shouldBe Int.MIN_VALUE
    }

    test("set with Coordinates sets the element at the given coordinates") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        grid[Coordinates(0, 1)] = '5'
        grid[0, 0] shouldBe '1'
        grid[1, 0] shouldBe '2'
        grid[0, 1] shouldBe '5'
        grid[1, 1] shouldBe '4'
    }

    test("set with array access sets the element at the given coordinates") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        grid[0, 1] = '5'
        grid[0, 0] shouldBe '1'
        grid[1, 0] shouldBe '2'
        grid[0, 1] shouldBe '5'
        grid[1, 1] shouldBe '4'
    }

    test("all setters call preSet") {
        val grid = object : Grid<Char>(listOf("12", "34"), '#') {
            override fun Char.toElementType() = this

            var setterWasCalledFor = mutableListOf<Pair<Coordinates, Char>>()

            override fun preSet(position: Coordinates, value: Char) {
                setterWasCalledFor.add(position to value)
            }
        }

        val position = Coordinates(0, 0)
        val position2 = Coordinates(0, 1)
        val position3 = Coordinates(1, 0)
        val value = 'a'
        grid.set(position, value)
        grid.setterWasCalledFor shouldBe listOf(position to value)

        grid.setterWasCalledFor.clear()
        grid[position] = value
        grid.setterWasCalledFor shouldBe listOf(position to value)

        grid.setterWasCalledFor.clear()
        grid.set(position.x, position.y, value)
        grid.setterWasCalledFor shouldBe listOf(position to value)

        grid.setterWasCalledFor.clear()
        grid[position.x, position.y] = value
        grid.setterWasCalledFor shouldBe listOf(position to value)

        grid.setterWasCalledFor.clear()
        grid[position.x, position.y] = value
        grid.setterWasCalledFor shouldBe listOf(position to value)

        grid.setterWasCalledFor.clear()
        grid[position.x, position.y..position2.y] = value
        grid.setterWasCalledFor shouldBe listOf(position to value, position2 to value)

        grid.setterWasCalledFor.clear()
        grid[position.x..position3.x, position.y] = value
        grid.setterWasCalledFor shouldBe listOf(position to value, position3 to value)
    }

    test("subGridAt returns a sub grid at the given coordinates") {
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

    test("forEachIndex calls the provided function on each element in the expected order") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        val chars = grid.forEachIndex { x, y ->
            grid[x, y]
        }.toList()
        chars shouldBe listOf('1', '2', '3', '4')
    }

    test("forEachElement calls the provided function on each element in the expected order") {
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

    testSuite("find() finds all Coordinates") {
        val grid = SimpleCharGrid(listOf("12", "34"))
        mapOf(
            '1' to Coordinates(0, 0),
            '2' to Coordinates(1, 0),
            '3' to Coordinates(0, 1),
            '4' to Coordinates(1, 1),
        ).forEach { (char, result) ->
            test("$char, $result") {
                grid.find(char) shouldBe result
            }
        }
    }

    test("find() throws a NoSuchElementException if the value cannot be found") {
        shouldThrow<NoSuchElementException> {
            SimpleCharGrid(listOf("12", "34")).find('5')
        }
    }

    testSuite(
        "printGrid",
        // tapSystemOut needs sequential execution
        testConfig = TestConfig.invocation(Sequential),
    ) {
        test("prints the grid") {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid()
            }
            output shouldBe "12${newLine}34$newLine"
        }

        test("overrides specified coordinates") {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(1, 0), Coordinates(0, 1))
                )
            }
            output shouldBe "1#${newLine}#4$newLine"
        }

        test("overrides specified coordinates with given overrideChar") {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    setOf(Coordinates(1, 0), Coordinates(0, 1)),
                    '?'
                )
            }
            output shouldBe "1?${newLine}?4$newLine"
        }

        test("highlights specified coordinates with given highlightDirection's Char") {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    highlightPosition = Coordinates(1, 0),
                    highlightDirection = Direction.SOUTH
                )
            }
            output shouldBe "1↓${newLine}34$newLine"
        }

        test("highlight has higher priority than overrides") {
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

        test("highlight without a direction falls back to overrides") {
            val output = tapSystemOut {
                val grid = SimpleCharGrid(listOf("12", "34"))
                grid.printGrid(
                    overrides = setOf(Coordinates(1, 0)),
                    highlightPosition = Coordinates(1, 0),
                )
            }
            output shouldBe "1#${newLine}34$newLine"
        }
    }

    testSuite("toString") {
        test("returns a string representation without any overrides") {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid.toString() shouldBe "12${newLine}34"
        }

        test("with overrides returns a string representation with the given overrides") {
            val grid = SimpleCharGrid(listOf("12", "34"))
            grid.toString(setOf(Coordinates(1, 1)), 'o') shouldBe "12${newLine}3o"
        }

        test("with padding adds padding") {
            val grid = SimpleCharGrid(listOf("12", "34"), nullElement = '.')
            grid.toString(padding = 2) shouldBe "..12..$newLine..34.."
        }
    }
}
