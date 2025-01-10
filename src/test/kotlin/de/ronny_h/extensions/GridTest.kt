package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GridTest {

    @Test
    fun `a grid can be constructed from List of String`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        assertThat(grid).isNotNull
    }

    @Test
    fun `width and height have the right values`() {
        val grid = object : Grid<Char>(listOf("00", "00", "00")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        assertThat(grid.height).isEqualTo(3)
        assertThat(grid.width).isEqualTo(2)
    }

    @Test
    fun `charAt returns the input values from the right indices`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        assertThat(grid.charAt(0, 0)).isEqualTo('1')
        assertThat(grid.charAt(0, 1)).isEqualTo('2')
        assertThat(grid.charAt(1, 0)).isEqualTo('3')
        assertThat(grid.charAt(1, 1)).isEqualTo('4')
    }

    @Test
    fun `toElementType converts the input values`() {
        val grid = object : Grid<Int>(listOf("12", "34")) {
            override val nullElement = Int.MIN_VALUE
            override fun Char.toElementType() = digitToInt()
        }
        assertThat(grid.charAt(0, 0)).isEqualTo(1)
        assertThat(grid.charAt(0, 1)).isEqualTo(2)
        assertThat(grid.charAt(1, 0)).isEqualTo(3)
        assertThat(grid.charAt(1, 1)).isEqualTo(4)
    }

    @Test
    fun `charAt with indices returns the same as charAt with Coordinates`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        assertThat(grid.charAt(0, 0)).isEqualTo(grid.charAt(Coordinates(0, 0)))
        assertThat(grid.charAt(0, 1)).isEqualTo(grid.charAt(Coordinates(0, 1)))
        assertThat(grid.charAt(1, 0)).isEqualTo(grid.charAt(Coordinates(1, 0)))
        assertThat(grid.charAt(1, 1)).isEqualTo(grid.charAt(Coordinates(1, 1)))
    }

    @Test
    fun `charAt with index out of the input values returns the nullElement of a Char Grid`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        assertThat(grid.charAt(-1, 0)).isEqualTo(' ')
        assertThat(grid.charAt(0, 2)).isEqualTo(' ')
        assertThat(grid.charAt(1, -1)).isEqualTo(' ')
        assertThat(grid.charAt(2, 2)).isEqualTo(' ')
    }

    @Test
    fun `charAt with index out of the input values returns the nullElement of an Int Grid`() {
        val grid = object : Grid<Int>(listOf("12", "34")) {
            override val nullElement = Int.MIN_VALUE
            override fun Char.toElementType() = digitToInt()
        }
        assertThat(grid.charAt(-1, 0)).isEqualTo(Int.MIN_VALUE)
        assertThat(grid.charAt(0, 2)).isEqualTo(Int.MIN_VALUE)
        assertThat(grid.charAt(1, -1)).isEqualTo(Int.MIN_VALUE)
        assertThat(grid.charAt(2, 2)).isEqualTo(Int.MIN_VALUE)
    }

    @Test
    fun `forEachIndex calls the provided function on each element in the expected order`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        val chars = grid.forEachIndex { row, column ->
            grid.charAt(row, column)
        }.toList()
        assertThat(chars).isEqualTo(listOf('1', '2', '3', '4'))
    }

    @Test
    fun `forEachElement calls the provided function on each element in the expected order`() {
        val grid = object : Grid<Char>(listOf("12", "34")) {
            override val nullElement = ' '
            override fun Char.toElementType() = this
        }
        val strings = grid.forEachElement { row, column, char ->
            "$row,$column:$char"
        }.toList()
        assertThat(strings).isEqualTo(
            listOf(
                "0,0:1",
                "0,1:2",
                "1,0:3",
                "1,1:4",
            )
        )
    }
}
