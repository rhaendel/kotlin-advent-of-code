package de.ronny_h.aoc.extensions.grids

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

val ListGridBackendTest by testSuite {

    testSuite("get returns the existing element: the one that's set or the fallback element") {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        listOf(
            Triple(0, 0, '0'),
            Triple(0, 1, '#'),
            Triple(1, 0, '#'),
            Triple(1, 1, '1'),
        ).forEach { (x, y, expected) ->
            test("$x, $y, $expected") {
                grid[x, y] shouldBe expected
                grid[Coordinates(x, y)] shouldBe expected
            }
        }
    }

    test("get for non existing element throws an Exception") {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')

        shouldThrow<IndexOutOfBoundsException> {
            grid[3, 0]
        }
    }

    testSuite("getOrNull returns null for non existing elements") {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        listOf(
            Triple(0, 0, '0'),
            Triple(0, 1, '#'),
            Triple(1, 0, '#'),
            Triple(1, 1, '1'),
            Triple(2, 0, null),
            Triple(0, 2, null),
        ).forEach { (x, y, expected) ->
            test("$x, $y, $expected") {
                grid.getOrNull(x, y) shouldBe expected
                grid.getOrNull(Coordinates(x, y)) shouldBe expected
            }
        }
    }

    test("subGridAt returns a list of lists for the specified section") {
        val grid: GridBackend<Char> = ListGridBackend(3, 3, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'
        grid[Coordinates(2, 2)] = '2'

        grid.subGridAt(1, 1, 2, 2) shouldBe listOf(
            listOf('1', '#'),
            listOf('#', '2'),
        )
    }

    test("mapToSequence transforms each grid coordinates row by row") {
        val grid = ListGridBackend(2, 2, 0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.mapToSequence { x, y ->
            "${grid[x, y]}"
        }.toList() shouldBe listOf("0", "1", "2", "3")
    }

    test("min and max indices are consistent with width and height") {
        val grid = ListGridBackend(2, 3, 0)
        grid.minX shouldBe 0
        grid.minY shouldBe 0
        grid.maxX shouldBe 1
        grid.maxY shouldBe 2
    }

    test("entries returns a set containing all, dense grid entries") {
        val grid = ListGridBackend(2, 2, 0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.entries shouldBe setOf(
            Coordinates(0, 0) to 0, Coordinates(0, 1) to 2,
            Coordinates(1, 0) to 1, Coordinates(1, 1) to 3
        )
    }

    test("hashCode and equals of equal grids") {
        val grid1: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid1[0, 0] = '0'
        grid1[1, 1] = '1'

        val grid2: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid2[0, 0] = '0'
        grid2[1, 1] = '1'

        grid1.hashCode() shouldBe grid2.hashCode()
        (grid1 == grid2) shouldBe true
        (grid2 == grid1) shouldBe true
    }

    testSuite("hashCode and equals of unequal grids") {
        val grid1: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid1[0, 0] = '0'
        grid1[1, 1] = '1'

        val grid2: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid2[0, 0] = '0'
        grid2[1, 1] = '2'

        val grid3: GridBackend<Char> = ListGridBackend(3, 2, '#')
        grid3[0, 0] = '0'
        grid3[1, 1] = '1'

        val grid4: GridBackend<Char> = ListGridBackend(2, 2, '.')
        grid4[0, 0] = '0'
        grid4[1, 1] = '1'

        val grids = listOf(grid1, grid2, grid3, grid4).withIndex()

        for ((i, first) in grids) {
            for ((j, second) in grids) {
                if (i == j) continue
                test("$i, $j") {
                    first.hashCode() shouldNotBe second.hashCode()
                    (first == second) shouldBe false
                }
            }
        }
    }
}
