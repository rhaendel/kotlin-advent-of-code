package de.ronny_h.aoc.year2017.day07

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RecursiveCircusTest : StringSpec({

    val input = """
        pbga (66)
        xhth (57)
        ebii (61)
        havc (66)
        ktlj (57)
        fwft (72) -> ktlj, cntj, xhth
        qoyq (66)
        padx (45) -> pbga, havc, qoyq
        tknk (41) -> ugml, padx, fwft
        jptl (61)
        ugml (68) -> gyxo, ebii, jptl
        gyxo (61)
        cntj (57)
    """.asList()

    "input can be parsed" {
        input.parsePrograms() shouldBe listOf(
            Program("pbga", 66),
            Program("xhth", 57),
            Program("ebii", 61),
            Program("havc", 66),
            Program("ktlj", 57),
            Program("fwft", 72, listOf("ktlj", "cntj", "xhth")),
            Program("qoyq", 66),
            Program("padx", 45, listOf("pbga", "havc", "qoyq")),
            Program("tknk", 41, listOf("ugml", "padx", "fwft")),
            Program("jptl", 61),
            Program("ugml", 68, listOf("gyxo", "ebii", "jptl")),
            Program("gyxo", 61),
            Program("cntj", 57),
        )
    }

    "part 1: the name of the bottom program" {
        RecursiveCircus().part1(input) shouldBe "tknk"
    }

    "part 2: the right weight of exactly one program with the wrong weight" {
        RecursiveCircus().part2(input) shouldBe "60"
    }
})
