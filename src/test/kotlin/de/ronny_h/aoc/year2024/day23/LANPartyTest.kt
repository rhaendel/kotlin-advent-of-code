package de.ronny_h.aoc.year2024.day23

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LANPartyTest : StringSpec({

    val input = """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.asList()

    /*
    a: b,c   -> a,b,c
    b: a,c,d -> a,b,c,d
    c: a,b,e -> a,b,c,  e
    d: b,e   ->   b,  d,e
    e: c,d   ->     c,d,e
     */
    val smallInput = """
            a-b
            b-d
            b-c
            c-e
            c-a
            d-e
        """.asList()

    /*
    a: b,c -> a,b,c
    b: a,c -> a,b,c
    c: a,b -> a,b,c
     */
    "in a simple network with three computers, a LAN party of these three is found" {
        val minimalInput = """
            a-b
            b-c
            c-a
        """.asList()
        Network(minimalInput).searchForLANPartiesWithThreeComputers() shouldBe setOf(setOf("a", "b", "c"))
    }

    "in a simple network with five computers, a LAN party of three is found" {
        Network(smallInput).searchForLANPartiesWithThreeComputers() shouldBe setOf(setOf("a", "b", "c"))
    }

    "part1: For all sets of three inter-connected computers, the number containing a computer starting with t" {
        LANParty().part1(input) shouldBe "7"
    }

    "sortedConnectionSets() sorts the sets of computers directly connected to one of them descendant by length" {
        Network(smallInput).sortedConnectionSets() shouldBe listOf(
            setOf("a","b","c","d"),
            setOf("a","b","c","e"),
            setOf("a","b","c"),
            setOf("b","d","e"),
            setOf("c","d","e"),
        )
    }

    "findLargestIntersection() finds the largest intersection that exists at least its length times over all sets" {
        listOf(
            setOf("a","d","e","f"), // is largest intersection but occurs only twice, not four times
            setOf("a","d","e","f"),
            setOf("a","b","c","d"),
            setOf("a","b","c","e"),
            setOf("a","b","c"),
            setOf("b","d","e"),
            setOf("c","d","e"),
        ).findLargestIntersection() shouldBe setOf("a", "b", "c")
    }

    "part2: largest set of computers that are all connected to each other, sorted alphabetically" {
        LANParty().part2(input) shouldBe "co,de,ka,ta"
    }
})
