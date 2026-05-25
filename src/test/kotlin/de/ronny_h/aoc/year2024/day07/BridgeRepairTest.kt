package de.ronny_h.aoc.year2024.day07

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val BridgeRepairTest by testSuite {
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

    testSuite(
        "part 1: Total calibration result of possibly true equations",
        mapOf(
            smallInput1 to 3749,
            mediumInput to 3749,
        ),
    ) { input, result ->
        BridgeRepair().part1(input) shouldBe result
    }

    testSuite(
        "part 2: Total calibration result of possibly true equations including elephant hiding spots",
        mapOf(
            smallInput2 to 7638,
            mediumInput to 11387,
        ),
    ) { input, result ->
        BridgeRepair().part2(input) shouldBe result
    }
}
