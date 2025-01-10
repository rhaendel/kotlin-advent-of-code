package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GridTest {

    @Test
    fun `a grid can be constructed from List of String`() {
        val grid = object : Grid(listOf("12", "34")) {
            override val nullElement = ' '
        }
        assertThat(grid).isNotNull
    }

    @Test
    fun `width and height have the right values`() {
        val grid = object : Grid(listOf("00", "00", "00")) {
            override val nullElement = ' '
        }
        assertThat(grid.height).isEqualTo(3)
        assertThat(grid.width).isEqualTo(2)
    }

    @Test
    fun `charAt returns the input values from the right indices`() {
        val grid = object : Grid(listOf("12", "34")) {
            override val nullElement = ' '
        }
        assertThat(grid.charAt(0, 0)).isEqualTo('1')
        assertThat(grid.charAt(0, 1)).isEqualTo('2')
        assertThat(grid.charAt(1, 0)).isEqualTo('3')
        assertThat(grid.charAt(1, 1)).isEqualTo('4')
    }

    @Test
    fun `charAt with index out of the input values returns the nullElement`() {
        val grid = object : Grid(listOf("12", "34")) {
            override val nullElement = ' '
        }
        assertThat(grid.charAt(-1, 0)).isEqualTo(' ')
        assertThat(grid.charAt(0, 2)).isEqualTo(' ')
        assertThat(grid.charAt(1, -1)).isEqualTo(' ')
        assertThat(grid.charAt(2, 2)).isEqualTo(' ')
    }

    @Test
    fun `forEach calls the provided function on each element in the expected order`() {
        val grid = object : Grid(listOf("12", "34")) {
            override val nullElement = ' '
        }
        val chars = grid.forEach { row, column ->
            grid.charAt(row, column)
        }.toList()
        assertThat(chars).isEqualTo(listOf('1', '2', '3', '4'))
    }
}
