package de.ronny_h.aoc.extensions.grids

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ListGridBackendTest : StringSpec({

    "get returns the existing element: the one that's set or the fallback element" {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        forAll(
            row(0, 0, '0'),
            row(0, 1, '#'),
            row(1, 0, '#'),
            row(1, 1, '1'),
        ) { x, y, expected ->
            grid[x, y] shouldBe expected
            grid[Coordinates(x, y)] shouldBe expected
        }
    }

    "get for non existing element throws an Exception" {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')

        shouldThrow<IndexOutOfBoundsException> {
            grid[3, 0]
        }
    }

    "getOrNull returns null for non existing elements" {
        val grid: GridBackend<Char> = ListGridBackend(2, 2, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        forAll(
            row(0, 0, '0'),
            row(0, 1, '#'),
            row(1, 0, '#'),
            row(1, 1, '1'),
            row(2, 0, null),
            row(0, 2, null),
        ) { x, y, expected ->
            grid.getOrNull(x, y) shouldBe expected
            grid.getOrNull(Coordinates(x, y)) shouldBe expected
        }
    }

    "subGridAt returns a list of lists for the specified section" {
        val grid: GridBackend<Char> = ListGridBackend(3, 3, '#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'
        grid[Coordinates(2, 2)] = '2'

        grid.subGridAt(1, 1, 2, 2) shouldBe listOf(
            listOf('1', '#'),
            listOf('#', '2'),
        )
    }

    "mapToSequence transforms each grid coordinates row by row" {
        val grid = ListGridBackend(2, 2, 0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.mapToSequence { x, y ->
            "${grid[x, y]}"
        }.toList() shouldBe listOf("0", "1", "2", "3")
    }

    "min and max indices are consistent with width and height" {
        val grid = ListGridBackend(2, 3, 0)
        grid.minX shouldBe 0
        grid.minY shouldBe 0
        grid.maxX shouldBe 1
        grid.maxY shouldBe 2
    }

    "entries returns a set containing all, dense grid entries" {
        val grid = ListGridBackend(2, 2, 0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.entries shouldBe setOf(
            Coordinates(0, 0) to 0, Coordinates(0, 1) to 2,
            Coordinates(1, 0) to 1, Coordinates(1, 1) to 3
        )
    }

    "hashCode and equals of equal grids" {
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

    "hashCode and equals of unequal grids" {
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

        forAll(
            row(grid1, grid2),
            row(grid1, grid3),
            row(grid1, grid4),
            row(grid2, grid1),
            row(grid2, grid3),
            row(grid2, grid4),
            row(grid3, grid1),
            row(grid3, grid2),
            row(grid3, grid4),
            row(grid4, grid1),
            row(grid4, grid2),
            row(grid4, grid3),
        ) { first, second ->
            first.hashCode() shouldNotBe second.hashCode()
            (first == second) shouldBe false
        }
    }
})
