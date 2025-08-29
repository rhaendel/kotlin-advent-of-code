package de.ronny_h.aoc.year2017.day22

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SporificaVirusTest : StringSpec({

    val input = """
            ..#
            #..
            ...
        """.asList()

    "part 1: The example causes 5587 infections in 10000 bursts" {
        SporificaVirus().part1(input) shouldBe 5587
    }

    "part 2: The example causes 2511944 infections in 10000000 bursts" {
        SporificaVirus().part2(input) shouldBe 2511944
    }
})
