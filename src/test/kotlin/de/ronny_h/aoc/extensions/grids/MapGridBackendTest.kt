package de.ronny_h.aoc.extensions.grids

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class MapGridBackendTest : StringSpec({

    "get returns the existing element: the one that's set or the fallback element" {
        val grid: GridBackend<Char> = MapGridBackend('#')
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

    "get for an element out of the bound of the elements set throws an Exception" {
        val grid: GridBackend<Char> = MapGridBackend('#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        shouldThrow<IndexOutOfBoundsException> {
            grid[3, 0]
        }
    }

    "getOrNull returns null for non existing elements" {
        val grid: GridBackend<Char> = MapGridBackend('#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'

        forAll(
            row(0, 0, '0'),
            row(0, 1, null),
            row(1, 0, null),
            row(1, 1, '1'),
            row(2, 0, null),
            row(0, 2, null),
        ) { x, y, expected ->
            grid.getOrNull(x, y) shouldBe expected
            grid.getOrNull(Coordinates(x, y)) shouldBe expected
        }
    }

    "subGridAt returns a list of lists for the specified section" {
        val grid: GridBackend<Char> = MapGridBackend('#')
        grid[0, 0] = '0'
        grid[Coordinates(1, 1)] = '1'
        grid[Coordinates(2, 2)] = '2'

        grid.subGridAt(1, 1, 2, 2) shouldBe listOf(
            listOf('1', '#'),
            listOf('#', '2'),
        )
    }

    "mapToSequence transforms each grid coordinates row by row" {
        val grid = MapGridBackend(0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.mapToSequence { x, y ->
            "${grid[x, y]}"
        }.toList() shouldBe listOf("0", "1", "2", "3")
    }

    "min and max indices are consistent with what was set" {
        forAll(
            row(listOf(Coordinates(7, 7)), 7, 7, 7, 7, 1, 1),
            row(listOf(Coordinates(7, 7), Coordinates(8, 9)), 7, 7, 8, 9, 2, 3),
        ) { set, minX, minY, maxX, maxY, width, height ->
            val grid = MapGridBackend(0)
            set.forEach { grid[it] = 1 }

            grid.minX shouldBe minX
            grid.minY shouldBe minY
            grid.maxX shouldBe maxX
            grid.maxY shouldBe maxY
            grid.width shouldBe width
            grid.height shouldBe height
        }

    }

    "entries returns a set containing only grid entries that were explicitly set" {
        val grid = MapGridBackend(0)
        grid[1, 0] = 1
        grid[0, 1] = 2
        grid[1, 1] = 3

        grid.entries shouldBe setOf(
            Coordinates(0, 1) to 2,
            Coordinates(1, 0) to 1, Coordinates(1, 1) to 3
        )
    }
})
