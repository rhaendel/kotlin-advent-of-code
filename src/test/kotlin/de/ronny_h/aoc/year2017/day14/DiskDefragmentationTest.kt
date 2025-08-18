package de.ronny_h.aoc.year2017.day14

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DiskDefragmentationTest : StringSpec({

    "hex to binary String" {
        hexToBinary("a0c2017") shouldBe "1010000011000010000000010111"
    }

    "part 1: In the example, 8108 squares are used across the entire 128x128 grid" {
        DiskDefragmentation().part1(listOf("flqrgnkx")) shouldBe 8108
    }

    "part 2: The number of regions present in the given key String" {
        DiskDefragmentation().part2(listOf("flqrgnkx")) shouldBe 1242
    }
})
