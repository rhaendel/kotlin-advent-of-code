package de.ronny_h.aoc.year2017.day12

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val DigitalPlumberTest by testSuite {

    val input = """
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5
    """.asList()

    test("part 1: the number of programs in the group that contains program ID 0") {
        DigitalPlumber().part1(input) shouldBe 6
    }

    test("part 2: the number of groups in total") {
        DigitalPlumber().part2(input) shouldBe 2
    }
}
