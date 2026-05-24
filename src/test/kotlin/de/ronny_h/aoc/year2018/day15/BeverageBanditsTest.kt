package de.ronny_h.aoc.year2018.day15

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val BeverageBanditsTest by testSuite {

    test("input can be parsed") {
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

    test("movements of two rounds of a small example") {
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

    test("combat of a small example") {
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

    testSuite("part 1: the outcome of the battle of some given examples") {
        val input2 = """
            #######
            #G..#E#
            #E#E.E#
            #G.##.#
            #...#E#
            #...E.#
            #######
        """.asList()

        mapOf(
            input to 27730,
            input2 to 36334,
            input3 to 39514,
            input4 to 27755,
            input5 to 28944,
            input6 to 18740,
        ).forEach { (input, outcome) ->
            test("$input, $outcome") {
                BeverageBandits().part1(input) shouldBe outcome
            }
        }
    }

    testSuite("part 2: the outcome of the battle with the smallest elf attack power so that no elf dies") {
        mapOf(
//            input to 4988,
//            input3 to 31284,
//            input4 to 3478,
//            input5 to 6474,
            input6 to 1140,
        ).forEach { (input, outcome) ->
            test("$input, $outcome") {
                BeverageBandits().part2(input) shouldBe outcome
            }
        }
    }
}
