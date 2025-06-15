package de.ronny_h.aoc.year24.day23

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

    "in a simple network with three computers, a LAN party of these three is found" {
        val smallInput = """
            a-b
            b-c
            c-a
        """.asList()
        Network(smallInput).searchForLANPartiesWithThreeComputers() shouldBe setOf(setOf("a", "b", "c"))
    }

    "in a simple network with five computers, a LAN party of three is found" {
        val smallInput = """
            a-b
            b-d
            b-c
            c-e
            c-a
        """.asList()
        Network(smallInput).searchForLANPartiesWithThreeComputers() shouldBe setOf(setOf("a", "b", "c"))
    }

    "part1: For all sets of three inter-connected computers, the number containing a computer starting with t" {
        LANParty().part1(input) shouldBe 7
    }
})
