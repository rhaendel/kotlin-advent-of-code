package de.ronny_h.aoc.year2015.day17

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NoSuchThingAsTooMuchTest : StringSpec({

    val input = listOf(20, 15, 10, 5, 5)

    "part 1: number of combinations to store 25 litres of eggnog" {
        differentCombinationsToStore(input, 25) shouldBe 4
    }

    "part 2: number of combinations to store 25 litres of eggnog with minimal number of containers" {
        differentWaysToStoreWithMinimalNumberOfContainers(input, 25) shouldBe 3
    }
})
