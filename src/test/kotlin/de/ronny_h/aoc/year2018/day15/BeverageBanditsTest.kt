package de.ronny_h.aoc.year2018.day15

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BeverageBanditsTest : StringSpec({

    "input can be parsed" {
        val input = """
            #######
            #E..G.#
            #...#.#
            #.G.#G#
            #######
        """.asList()
        CombatArea(input).toString().asList() shouldBe input
    }

    val input = """
        #######
        #.G...#
        #...EG#
        #.#.#G#
        #..G#E#
        #.....#
        #######
    """.asList()

    "movements of two rounds of a small example" {
        val expected1 = """
            #######
            #..G..#
            #...EG#
            #.#G#G#
            #...#E#
            #.....#
            #######
        """.trimIndent()
        val expected2 = """
            #######
            #...G.#
            #..GEG#
            #.#.#G#
            #...#E#
            #.....#
            #######
        """.trimIndent()

        val combatArea = CombatArea(input)

        combatArea.takeOneRound() shouldBe true
        combatArea.toString() shouldBe expected1

        combatArea.takeOneRound() shouldBe true
        combatArea.toString() shouldBe expected2
    }

    "combat of a small example" {
        val expected23 = """
            #######
            #...G.#
            #..G.G#
            #.#.#G#
            #...#E#
            #.....#
            #######
        """.trimIndent()
        val expected24 = """
            #######
            #..G..#
            #...G.#
            #.#G#G#
            #...#E#
            #.....#
            #######
        """.trimIndent()
        val expected25 = """
            #######
            #.G...#
            #..G..#
            #.#.#G#
            #..G#E#
            #.....#
            #######
        """.trimIndent()
        val expected28 = """
            #######
            #G....#
            #.G...#
            #.#.#G#
            #...#E#
            #....G#
            #######
        """.trimIndent()
        val expected47 = """
            #######
            #G....#
            #.G...#
            #.#.#G#
            #...#.#
            #....G#
            #######
        """.trimIndent()

        val combatArea = CombatArea(input)
        repeat(23) { combatArea.takeOneRound() shouldBe true }
        combatArea.toString() shouldBe expected23

        combatArea.takeOneRound() shouldBe true
        combatArea.toString() shouldBe expected24

        combatArea.takeOneRound() shouldBe true
        combatArea.toString() shouldBe expected25

        repeat(3) { combatArea.takeOneRound() shouldBe true }
        combatArea.toString() shouldBe expected28

        repeat(19) { combatArea.takeOneRound() shouldBe true }
        combatArea.toString() shouldBe expected47

        combatArea.takeOneRound() shouldBe false
    }

    "part 1: the outcome of the battle of the small example" {
        BeverageBandits().part1(input) shouldBe 27730
    }

    "part 2" {
        val input = listOf("")
        BeverageBandits().part2(input) shouldBe 0
    }
})
