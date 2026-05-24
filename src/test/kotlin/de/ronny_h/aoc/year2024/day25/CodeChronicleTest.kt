package de.ronny_h.aoc.year2024.day25

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2024.day25.CodeChronicle.Companion.convertToLocksAndKeys
import de.ronny_h.aoc.year2024.day25.CodeChronicle.Companion.fitsIntoLockWithoutOverlapping
import io.kotest.matchers.shouldBe

val CodeChronicleTest by testSuite {

    val smallInput = """
        #####
        .####
        .####
        .####
        .#.#.
        .#...
        .....
        
        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####
    """.asList()

    val input = """
        #####
        .####
        .####
        .####
        .#.#.
        .#...
        .....
        
        #####
        ##.##
        .#.##
        ...##
        ...#.
        ...#.
        .....
        
        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####
        
        .....
        .....
        #.#..
        ###..
        ###.#
        ###.#
        #####
        
        .....
        .....
        .....
        #....
        #.#..
        #.#.#
        #####
    """.asList()

    test("convert smallInput to locks and keys") {
        smallInput.convertToLocksAndKeys() shouldBe listOf(Lock(listOf(0,5,3,4,3)), Key(listOf(5,0,2,1,3)))
    }

    test("a key fits into a lock without overlapping") {
        Key(listOf(3,3,3,3,3)).fitsIntoLockWithoutOverlapping(Lock(listOf(2,2,2,2,2))) shouldBe true
    }

    test("a key with a too high height does not fit into a lock without overlapping") {
        Key(listOf(3,3,4,3,3)).fitsIntoLockWithoutOverlapping(Lock(listOf(2,2,2,2,2))) shouldBe false
    }

    test("part1: In the input should be 3 unique lock/key pairs that fit together without overlapping in any column") {
        CodeChronicle().part1(input) shouldBe 3
    }
}
