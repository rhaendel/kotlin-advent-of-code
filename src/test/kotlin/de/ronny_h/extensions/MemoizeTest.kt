package de.ronny_h.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MemoizeTest {

    @Test
    fun `memoize does not change the result`() {
        val someFun: (Int) -> Int = { it: Int ->
            it + it
        }
        val someFunMemoized: (Int) -> Int = someFun.memoize()

        for (i in -100..100 step 10) {
            assertThat(someFunMemoized(i)).isEqualTo(someFun(i))
        }
    }

    @Test
    fun `recursive use of memoize does not change the result`() {
        lateinit var fib: (Int) -> Long
        fib = { n: Int ->
            when (n) {
                1, 2 -> 1L
                else -> fib(n-1) + fib(n-2)
            }
        }
        val fibMemoized: (Int) -> Long = fib.memoize()

        for (i in listOf(1, 2, 10, 20, 30)) {
            assertThat(fibMemoized(i)).isEqualTo(fib(i))
        }
    }
}
