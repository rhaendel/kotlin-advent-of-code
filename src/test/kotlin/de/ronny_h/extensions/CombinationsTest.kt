package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CombinationsTest {

    @Test
    fun `an empty list yields an empty sequence`() {
        assertThat(listOf<Int>().combinations().toList()).isEmpty()
    }

    @Test
    fun `a list with one element yields an empty sequence`() {
        assertThat(listOf(1).combinations().toList()).isEmpty()
    }

    @Test
    fun `a list with two elements yields both combinations`() {
        assertThat(listOf(1, 2).combinations().toList()).isEqualTo(listOf(1 to 2, 2 to 1))
    }

    @Test
    fun `a list with three elements yields all six combinations`() {
        assertThat(listOf(1, 2, 3).combinations().toList()).isEqualTo(
            listOf(
                1 to 2,
                1 to 3,
                2 to 1,
                2 to 3,
                3 to 1,
                3 to 2
            )
        )
    }

    @Test
    fun `a list with non-unique elements yields non-unique combinations but still skips the identical ones`() {
        assertThat(listOf(1, 1, 2).combinations().toList()).isEqualTo(
            listOf(
                1 to 1,
                1 to 2,
                1 to 1,
                1 to 2,
                2 to 1,
                2 to 1
            )
        )
    }

}
