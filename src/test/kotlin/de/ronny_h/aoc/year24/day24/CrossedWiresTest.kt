package de.ronny_h.aoc.year24.day24

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year24.day24.CrossedWires.And
import de.ronny_h.aoc.year24.day24.CrossedWires.Or
import de.ronny_h.aoc.year24.day24.CrossedWires.Xor
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class CrossedWiresTest : StringSpec({

    val verySmallInput = """
        y01: 1
        y02: 0

        x00 AND y00 -> z00
        x01 XOR y01 -> z01
    """.asList()
    val smallInput = """
        x00: 1
        x01: 1
        x02: 1
        y00: 0
        y01: 1
        y02: 0

        x00 AND y00 -> z00
        x01 XOR y01 -> z01
        x02 OR y02 -> z02
    """.asList()
    val input = """
        x00: 1
        x01: 0
        x02: 1
        x03: 1
        x04: 0
        y00: 1
        y01: 1
        y02: 1
        y03: 1
        y04: 1
        
        ntg XOR fgs -> mjb
        y02 OR x01 -> tnw
        kwq OR kpj -> z05
        x00 OR x03 -> fst
        tgd XOR rvg -> z01
        vdt OR tnw -> bfw
        bfw AND frj -> z10
        ffh OR nrd -> bqk
        y00 AND y03 -> djm
        y03 OR y00 -> psh
        bqk OR frj -> z08
        tnw OR fst -> frj
        gnj AND tgd -> z11
        bfw XOR mjb -> z00
        x03 OR x00 -> vdt
        gnj AND wpb -> z02
        x04 AND y00 -> kjc
        djm OR pbm -> qhw
        nrd AND vdt -> hwm
        kjc AND fst -> rvg
        y04 OR y02 -> fgs
        y01 AND x02 -> pbm
        ntg OR kjc -> kwq
        psh XOR fgs -> tgd
        qhw XOR tgd -> z09
        pbm OR djm -> kpj
        x03 XOR y03 -> ffh
        x00 XOR y04 -> ntg
        bfw OR bqk -> z06
        nrd XOR fgs -> wpb
        frj XOR qhw -> z04
        bqk OR frj -> z07
        y03 OR x01 -> nrd
        hwm AND bqk -> z03
        tgd XOR rvg -> z12
        tnw OR pbm -> gnj
    """.asList()

    "wires can be parsed" {
        CrossedWires().parseWires(verySmallInput) shouldBe listOf(Wire("y01", true), Wire("y02", false))
    }

    "gates can be parsed" {
        CrossedWires().parseGates(verySmallInput) shouldBe listOf(
                Gate("x00", "y00", And(), "z00"),
                Gate("x01", "y01", Xor(), "z01"),
            )
    }

    "single gates can be simulated" {
        val inWires = mapOf("x00" to Wire("x00", true), "y00" to Wire("y00", false))

        forAll(
            row(And(), false),
            row(Or(), true),
            row(Xor(), true),
        ) { operation, result ->
            Gate("x00", "y00", operation, "z00").simulateWith(inWires) shouldBe Wire("z00", result)
        }
    }

    "part1 small: The decimal number output on the wires starting with z" {
        CrossedWires().part1(smallInput) shouldBe "4"
    }

    "part1: The decimal number output on the wires starting with z" {
        CrossedWires().part1(input) shouldBe "2024"
    }
})
