package de.ronny_h.aoc.year2015.day13

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class KnightsOfTheDinnerTableTest : StringSpec({

    val input = listOf(
        "Alice would gain 54 happiness units by sitting next to Bob.",
        "Alice would lose 79 happiness units by sitting next to Carol.",
        "Alice would lose 2 happiness units by sitting next to David.",
        "Bob would gain 83 happiness units by sitting next to Alice.",
        "Bob would lose 7 happiness units by sitting next to Carol.",
        "Bob would lose 63 happiness units by sitting next to David.",
        "Carol would lose 62 happiness units by sitting next to Alice.",
        "Carol would gain 60 happiness units by sitting next to Bob.",
        "Carol would gain 55 happiness units by sitting next to David.",
        "David would gain 46 happiness units by sitting next to Alice.",
        "David would lose 7 happiness units by sitting next to Bob.",
        "David would gain 41 happiness units by sitting next to Carol.",
    )

    "input can be parsed" {
        forAll(
            row(
                "Alice would gain 54 happiness units by sitting next to Bob.",
                HappinessPotential("Alice", "Bob", 54)
            ),
            row(
                "Alice would lose 79 happiness units by sitting next to Carol.",
                HappinessPotential("Alice", "Carol", -79)
            ),
        ) { sentence, expected ->
            listOf(sentence).parse() shouldBe listOf(expected)
        }
    }

    "part 1: the optimal total change in happiness" {
        KnightsOfTheDinnerTable().part1(input) shouldBe 330
    }

    "part 2: the optimal total change in happiness, myself included" {
        KnightsOfTheDinnerTable().part2(input) shouldBe 286
    }
})
