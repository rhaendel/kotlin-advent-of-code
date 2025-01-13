package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SymmetriesTest {

    @Test
    fun `an empty list is symmetrical`() {
        assertThat(listOf<Int>().isSymmetrical()).isTrue()
    }

    @Test
    fun `a symmetrical list is symmetrical`() {
        assertThat(listOf(1, 2, 3, 2, 1).isSymmetrical()).isTrue()
    }

    @Test
    fun `a asymmetrical list is not symmetrical`() {
        assertThat(listOf(4, 2, 3, 2, 1).isSymmetrical()).isFalse()
    }
}
