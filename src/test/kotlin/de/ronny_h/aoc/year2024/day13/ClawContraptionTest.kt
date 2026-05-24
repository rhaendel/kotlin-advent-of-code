package de.ronny_h.aoc.year2024.day13

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val ClawContraptionTest by testSuite {
    val input = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400
        
        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176
        
        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450
        
        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.asList()

    test("part 1: The fewest tokens to spend to win all possible prizes") {
        ClawContraption().part1(input) shouldBe 480
    }

    test("part 2: The fewest tokens to spend to win all possible prizes with corrected prize") {
        ClawContraption().part2(input) shouldBe 875318608908
    }
}
