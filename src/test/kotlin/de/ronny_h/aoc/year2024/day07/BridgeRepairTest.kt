package de.ronny_h.aoc.year2024.day07

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class BridgeRepairTest : StringSpec({
    val smallInput1 = """
        190: 10 19
        3267: 81 40 27
        292: 11 6 16 20
    """.asList()
    val mediumInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.asList()
    val smallInput2 = """
        156: 15 6
        7290: 6 8 6 15
        192: 17 8 14
    """.asList()

    "part 1: Total calibration result of possibly true equations" {
        forAll(
            row(smallInput1, 3749),
            row(mediumInput, 3749),
        ) { input, result ->
            BridgeRepair().part1(input) shouldBe result
        }
    }

    "part 2: Total calibration result of possibly true equations including elephant hiding spots" {
        forAll(
            row(smallInput2, 7638),
            row(mediumInput, 11387),
        ) { input, result ->
            BridgeRepair().part2(input) shouldBe result
        }
    }
})
