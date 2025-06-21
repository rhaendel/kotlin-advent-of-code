package de.ronny_h.aoc.year24.day25

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year24.day25.CodeChronicle.Companion.convertToLocksAndKeys
import de.ronny_h.aoc.year24.day25.CodeChronicle.Companion.fitsIntoLockWithoutOverlapping
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CodeChronicleTest : StringSpec({

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

    "convert smallInput to locks and keys" {
        smallInput.convertToLocksAndKeys() shouldBe listOf(Lock(listOf(0,5,3,4,3)), Key(listOf(5,0,2,1,3)))
    }

    "a key fits into a lock without overlapping" {
        Key(listOf(3,3,3,3,3)).fitsIntoLockWithoutOverlapping(Lock(listOf(2,2,2,2,2))) shouldBe true
    }

    "a key with a too high height does not fit into a lock without overlapping" {
        Key(listOf(3,3,4,3,3)).fitsIntoLockWithoutOverlapping(Lock(listOf(2,2,2,2,2))) shouldBe false
    }

    "part1: In the input should be 3 unique lock/key pairs that fit together without overlapping in any column" {
        CodeChronicle().part1(input) shouldBe 3
    }
})
