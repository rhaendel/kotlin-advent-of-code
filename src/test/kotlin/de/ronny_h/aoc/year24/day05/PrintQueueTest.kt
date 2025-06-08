package de.ronny_h.aoc.year24.day05

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PrintQueueTest : StringSpec({
    val smallInput = listOf(
        "1|2", "2|3", "3|4", "4|5",
        "",
        "1,2,3,4,5", "1,3,2", "1,2,3"
    )
    val mediumInput = listOf(
        "47|53", "97|13", "97|61", "97|47", "75|29", "61|13", "75|53", "29|13", "97|29", "53|29", "61|53",
        "97|53", "61|29", "47|13", "75|47", "97|75", "47|61", "75|61", "47|29", "75|13", "53|13",
        "",
        "75,47,61,53,29", "97,61,53,29,13", "75,29,13",
        "75,97,47,61,53", "61,13,29", "97,13,75,29,47",
    )

    "part 1, small input: The sum of the middle page number from correctly-ordered updates" {
        PrintQueue().part1(smallInput) shouldBe 5
    }

    "part 1, medium input: The sum of the middle page number from correctly-ordered updates" {
        PrintQueue().part1(mediumInput) shouldBe 143
    }

    "part 2, small input: The sum of the middle page number from incorrectly-ordered, but now sorted updates" {
        PrintQueue().part2(smallInput) shouldBe 2
    }

    "part 2, medium input: The sum of the middle page number from incorrectly-ordered, but now sorted updates" {
        PrintQueue().part2(mediumInput) shouldBe 123
    }
})
