package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MemoizeTest : StringSpec({

    "memoize does not change the result" {
        val someFun: (Int) -> Int = { it: Int ->
            it + it
        }
        val someFunMemoized: (Int) -> Int = someFun.memoize()

        for (i in -100..100 step 10) {
            someFunMemoized(i) shouldBe someFun(i)
        }
    }

    "recursive use of memoize does not change the result" {
        lateinit var fib: (Int) -> Long
        fib = { n: Int ->
            when (n) {
                1, 2 -> 1L
                else -> fib(n-1) + fib(n-2)
            }
        }
        val fibMemoized: (Int) -> Long = fib.memoize()

        for (i in listOf(1, 2, 10, 20, 30)) {
            fibMemoized(i) shouldBe fib(i)
        }
    }
})
