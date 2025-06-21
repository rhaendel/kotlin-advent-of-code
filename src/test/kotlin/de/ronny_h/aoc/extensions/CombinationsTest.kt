package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CombinationsTest : StringSpec({

    "an empty list yields an empty sequence" {
        listOf<Int>().combinations().toList() shouldBe emptyList()
    }

    "a list with one element yields an empty sequence" {
        listOf(1).combinations().toList() shouldBe emptyList()
    }

    "a list with two elements yields both combinations" {
        listOf(1, 2).combinations().toList() shouldBe listOf(1 to 2, 2 to 1)
    }

    "a list with three elements yields all six combinations" {
        listOf(1, 2, 3).combinations().toList() shouldBe
                listOf(
                    1 to 2,
                    1 to 3,
                    2 to 1,
                    2 to 3,
                    3 to 1,
                    3 to 2
                )
    }

    "a list with non-unique elements yields non-unique combinations but still skips the identical ones" {
        listOf(1, 1, 2).combinations().toList() shouldBe
                listOf(
                    1 to 1,
                    1 to 2,
                    1 to 1,
                    1 to 2,
                    2 to 1,
                    2 to 1
                )
    }

    "combinationsOf() with the first list empty yields an empty sequence" {
        combinationsOf(listOf<Int>(), listOf("a", "b")).toList() shouldBe emptyList()
    }

    "combinationsOf() with the second list empty yields an empty sequence" {
        combinationsOf(listOf(1, 2), listOf<String>()).toList() shouldBe emptyList()
    }

    "combinationsOf() two non-empty lists yields all combinations of their elements" {
        combinationsOf(listOf(1, 2), listOf("a", "b")).toList() shouldBe
                listOf(
                    1 to "a",
                    1 to "b",
                    2 to "a",
                    2 to "b",
                )
    }
})
