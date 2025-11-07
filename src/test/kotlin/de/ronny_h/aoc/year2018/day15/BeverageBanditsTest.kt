package de.ronny_h.aoc.year2018.day15

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
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

    "part 1: the outcome of the battle of some given examples" {
        val input2 = """
            #######
            #G..#E#
            #E#E.E#
            #G.##.#
            #...#E#
            #...E.#
            #######
        """.asList()
        val input3 = """
            #######
            #E..EG#
            #.#G.E#
            #E.##E#
            #G..#.#
            #..E#.#
            #######
        """.asList()
        val input4 = """
            #######
            #E.G#.#
            #.#G..#
            #G.#.G#
            #G..#.#
            #...E.#
            #######
        """.asList()
        val input5 = """
            #######
            #.E...#
            #.#..G#
            #.###.#
            #E#G#G#
            #...#G#
            #######
        """.asList()
        val input6 = """
            #########
            #G......#
            #.E.#...#
            #..##..G#
            #...##..#
            #...#...#
            #.G...G.#
            #.....G.#
            #########
        """.asList()
        forAll(
            row(input, 27730),
            row(input2, 36334), // round right, but 3 hit points too high
            row(input3, 39514), // one round too low, but hit points right
            row(input4, 27755),
            row(input5, 28944),
            row(input6, 18740),
        ) { input, outcome ->
            BeverageBandits().part1(input) shouldBe outcome
        }
    }

    "part 2" {
        val input = listOf("")
        BeverageBandits().part2(input) shouldBe 0
    }
})
