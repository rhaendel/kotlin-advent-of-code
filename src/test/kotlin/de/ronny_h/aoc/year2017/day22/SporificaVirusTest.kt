package de.ronny_h.aoc.year2017.day22

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val SporificaVirusTest by testSuite {

    val input = """
            ..#
            #..
            ...
        """.asList()

    test("part 1: The example causes 5587 infections in 10000 bursts") {
        SporificaVirus().part1(input) shouldBe 5587
    }

    test("part 2: The example causes 2511944 infections in 10000000 bursts") {
        SporificaVirus().part2(input) shouldBe 2511944
    }
}
