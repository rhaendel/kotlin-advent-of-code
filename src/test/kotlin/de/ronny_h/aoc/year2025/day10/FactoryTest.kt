package de.ronny_h.aoc.year2025.day10

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2025.day10.LightState.OFF
import de.ronny_h.aoc.year2025.day10.LightState.ON
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class FactoryTest : StringSpec({

    val input = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.asList()

    "machine descriptions can be parsed" {
        """
            [.##.] (3) (1,3) {3,5,4}
            [.#] (0,2,3,4) {7,5}
        """.asList().parseMachineDescriptions() shouldBe listOf(
            MachineDescription(listOf(OFF, ON, ON, OFF), listOf(listOf(3), listOf(1, 3)), listOf(3, 5, 4)),
            MachineDescription(listOf(OFF, ON), listOf(listOf(0, 2, 3, 4)), listOf(7, 5)),
        )
    }

    "contentEquals checks equality of a MutableList and a List" {
        mutableListOf(1, 2).contentEquals(listOf(1, 2)) shouldBe true
        mutableListOf(1, 2).contentEquals(listOf(1, 2, 3)) shouldBe false
        mutableListOf(1, 2).contentEquals(listOf(1, 3)) shouldBe false
    }

    "configureIndicatorLights" {
        val descriptions = input.parseMachineDescriptions()
        forAll(
            row(descriptions[0], 2),
            row(descriptions[1], 3),
            row(descriptions[2], 2),
        ) { description, minPresses ->
            Machine(description).configureIndicatorLights() shouldBe minPresses
        }
    }

    "configureJoltages" {
        val descriptions = input.parseMachineDescriptions()
        forAll(
            row(descriptions[0], 10),
            row(descriptions[1], 12),
            row(descriptions[2], 11),
        ) { description, minPresses ->
            Machine(description).configureJoltages() shouldBe minPresses
        }
    }

    "configureJoltagesUsingAStar" {
        val descriptions = input.parseMachineDescriptions()
        forAll(
            row(descriptions[0], 10),
            row(descriptions[1], 12),
            row(descriptions[2], 11),
        ) { description, minPresses ->
            Machine(description).configureJoltagesUsingAStar() shouldBe minPresses
        }
    }

    "part 1: the fewest button presses required to correctly configure the indicator lights" {
        Factory().part1(input) shouldBe 7
    }

    "part 2: the fewest button presses required to correctly configure the joltage level counters" {
        Factory().part2(input) shouldBe 33
    }
})
