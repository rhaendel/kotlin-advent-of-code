package de.ronny_h.aoc.year2017.day18

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2017.day18.Instruction.*
import de.ronny_h.aoc.year2017.day18.Value.Number
import de.ronny_h.aoc.year2017.day18.Value.Register
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED

class DuetTest : StringSpec({

    val input1 = """
        set a 1
        add a 2
        mul a a
        mod a 5
        snd a
        set a 0
        rcv a
        jgz a -1
        set a 1
        jgz a -2
    """.asList()

    "instructions with sound can be parsed" {
        input1.parseInstructions(null, null) shouldBe listOf(
            SetValue("a", Number(1)),
            Add("a", Number(2)),
            Multiply("a", Register("a")),
            Modulo("a", Number(5)),
            Sound("a"),
            SetValue("a", Number(0)),
            Recover("a"),
            JumpIfGreaterZero(Register("a"), Number(-1)),
            SetValue("a", Number(1)),
            JumpIfGreaterZero(Register("a"), Number(-2)),
        )
    }

    "part 1: the value of the recovered frequency the first time a rcv instruction is executed with a non-zero value" {
        Duet().part1(input1) shouldBe 4
    }

    val input2 = """
        snd 1
        snd 2
        snd p
        rcv a
        rcv b
        rcv c
        rcv d
    """.asList()

    "instructions with send and receive channels can be parsed" {
        val channel0 = Channel<Long>(UNLIMITED)
        val channel1 = Channel<Long>(UNLIMITED)

        input2.parseInstructions(channel0, channel1) shouldBe listOf(
            Send(Number(1), channel0),
            Send(Number(2), channel0),
            Send(Register("p"), channel0),
            Receive("a", channel1),
            Receive("b", channel1),
            Receive("c", channel1),
            Receive("d", channel1),
        )
    }

    "part 2: the number of times program 1 sent a value before a deadlock occurs" {
        Duet().part2(input2) shouldBe 3
    }
})
