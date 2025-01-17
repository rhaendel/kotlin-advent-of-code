package de.ronny_h.extensions

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class SymmetriesTest : StringSpec({

    "an empty list is symmetrical" {
        listOf<Int>().isSymmetrical() shouldBe true
    }

    "a symmetrical list is symmetrical" {
        listOf(1, 2, 3, 2, 1).isSymmetrical() shouldBe true
    }

    "a asymmetrical list is not symmetrical" {
        listOf(4, 2, 3, 2, 1).isSymmetrical() shouldBe false
    }
})
