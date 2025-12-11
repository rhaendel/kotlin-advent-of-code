package de.ronny_h.aoc.year2025.day11

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ReactorTest : StringSpec({

    "parse devices" {
        """
            aaa: you hhh
            you: bbb ccc
        """.asList().parseDevices() shouldBe listOf(
            Device("aaa", listOf("you", "hhh")),
            Device("you", listOf("bbb", "ccc")),
        )
    }

    "part 1: the number of paths to out" {
        val input = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
        """.asList()
        Reactor().part1(input) shouldBe 5
    }

    "part 2: exactly one straight path" {
        val input = """
            svr: aaa
            aaa: dac
            dac: ccc
            ccc: fft
            fft: out
        """.asList()
        Reactor().part2(input) shouldBe 1
    }

    "part 2: exactly one path with a bypass right" {
        val input = """
            svr: aaa
            aaa: dac bya
            dac: ccc
            bya: ccc
            ccc: fft
            fft: out
        """.asList()
        Reactor().part2(input) shouldBe 1
    }

    "part 2: exactly one path with a bypass left" {
        val input = """
            svr: aaa
            aaa: bya dac
            bya: ccc
            dac: ccc
            ccc: fft
            fft: out
        """.asList()
        Reactor().part2(input) shouldBe 1
    }

    "part 2: 2 paths above fft and dac" {
        val input = """
            svr: aaa bbb
            aaa: dac
            bbb: dac
            dac: ccc
            ccc: fft
            fft: out
        """.asList()
        Reactor().part2(input) shouldBe 2
    }

    "part 2: 2 paths below fft and dac" {
        val input = """
            svr: aaa
            aaa: dac
            dac: bbb
            bbb: fft
            fft: ccc ddd
            ccc: out
            ddd: out
        """.asList()
        Reactor().part2(input) shouldBe 2
    }

    "part 2: 2 paths between fft and dac" {
        val input = """
            svr: aaa
            aaa: dac
            dac: bbb
            bbb: ccc ddd
            ccc: fft
            ddd: fft
            fft: out
        """.asList()
        Reactor().part2(input) shouldBe 2
    }

    "part 2: the number of paths from svr to out visiting ffd and dac" {
        val input = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """.asList()
        Reactor().part2(input) shouldBe 2
    }
})
