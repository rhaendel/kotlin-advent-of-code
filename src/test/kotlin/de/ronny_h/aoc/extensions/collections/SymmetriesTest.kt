package de.ronny_h.aoc.extensions.collections

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SymmetriesTest : StringSpec({

    "an empty list is symmetrical" {
        listOf<Int>().isSymmetrical() shouldBe true
    }

    "a symmetrical list is symmetrical" {
        listOf(1, 2, 3, 2, 1).isSymmetrical() shouldBe true
    }

    "a symmetrical list of Strings is symmetrical" {
        listOf("a", "b", "c", "b", "a").isSymmetrical() shouldBe true
    }

    "an asymmetrical list is not symmetrical" {
        listOf(4, 2, 3, 2, 1).isSymmetrical() shouldBe false
    }
})
