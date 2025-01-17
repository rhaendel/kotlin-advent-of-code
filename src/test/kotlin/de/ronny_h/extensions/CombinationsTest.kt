package de.ronny_h.extensions

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

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

})
