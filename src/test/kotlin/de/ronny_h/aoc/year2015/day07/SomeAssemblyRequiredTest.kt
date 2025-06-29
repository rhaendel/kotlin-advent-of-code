package de.ronny_h.aoc.year2015.day07

import de.ronny_h.aoc.year2015.day07.Operator.BinaryOperator.*
import de.ronny_h.aoc.year2015.day07.Operator.UnaryOperator.NOT
import de.ronny_h.aoc.year2015.day07.OperatorOutput.NoSignal
import de.ronny_h.aoc.year2015.day07.OperatorOutput.ValueOutput
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SomeAssemblyRequiredTest : StringSpec({

    "parse scalar" {
        SomeAssemblyRequired().parse("0 -> c") shouldBe Scalar(0U, "c")
    }

    "parse re-wiring" {
        SomeAssemblyRequired().parse("lx -> a") shouldBe ReWire("lx", "a")
    }

    "parse unary operation" {
        SomeAssemblyRequired().parse("NOT ii -> ij") shouldBe UnaryOperation("ii", NOT, "ij")
    }

    "parse binary operation" {
        SomeAssemblyRequired().parse("af AND ah -> ai") shouldBe BinaryOperation("af", "ah", AND, "ai")
        SomeAssemblyRequired().parse("du OR dt -> dv") shouldBe BinaryOperation("du", "dt", OR, "dv")
        SomeAssemblyRequired().parse("eo LSHIFT 15 -> es") shouldBe BinaryOperation("eo", "15", LSHIFT, "es")
        SomeAssemblyRequired().parse("eo RSHIFT 5 -> es") shouldBe BinaryOperation("eo", "5", RSHIFT, "es")
    }

    "a scalar always represents a value" {
        Scalar(1U, "a").execute(mapOf("a" to 2U)) shouldBe Output("a", ValueOutput(1U))
    }

    "a re-wiring returns NoSignal if the argument is not provided" {
        ReWire("a", "b").execute(mapOf()) shouldBe Output("b", NoSignal)
    }

    "a re-wiring returns the result if the argument not provided" {
        ReWire("a", "b").execute(mapOf("a" to 7U)) shouldBe Output("b", ValueOutput(7U))
    }

    "an unary operation returns NoSignal if the argument is not provided" {
        UnaryOperation("b", NOT, "a").execute(mapOf()) shouldBe Output("a", NoSignal)
    }

    "an unary operation returns the result if the argument is provided" {
        UnaryOperation("b", NOT, "a").execute(mapOf("b" to "0000000000000000".toUShort(2))) shouldBe Output(
            "a",
            ValueOutput("1111111111111111".toUShort(2))
        )
    }

    "all binary operations return NoSignal if not both arguments are provided" {
        forAll(
            row(AND), row(OR), row(LSHIFT), row(RSHIFT)
        ) { op ->
            BinaryOperation("a", "b", op, "c").execute(emptyMap()) shouldBe Output("c", NoSignal)
            BinaryOperation("a", "b", op, "c").execute(mapOf("a" to 0U)) shouldBe Output("c", NoSignal)
            BinaryOperation("a", "b", op, "c").execute(mapOf("b" to 0U)) shouldBe Output("c", NoSignal)
        }
    }

    "all binary operations return the result if both arguments are provided" {
        val values = mapOf(
            "a" to "0000000011111111".toUShort(2),
            "b" to "0000000000000001".toUShort(2),
        )
        forAll(
            row(AND, "0000000000000001"),
            row(OR, "0000000011111111"),
            row(LSHIFT, "0000000111111110"),
            row(RSHIFT, "0000000001111111"),
        ) { op, result ->
            BinaryOperation("a", "b", op, "c").execute(values) shouldBe Output("c", ValueOutput(result.toUShort(2)))
        }
    }

    "all binary operations return the result if one argument is provided and the other is a scalar" {
        val values = mapOf(
            "a" to "0000000011111111".toUShort(2),
        )
        forAll(
            row(AND, "0000000000000001"),
            row(OR, "0000000011111111"),
            row(LSHIFT, "0000000111111110"),
            row(RSHIFT, "0000000001111111"),
        ) { op, result ->
            BinaryOperation("a", "1", op, "c").execute(values) shouldBe Output("c", ValueOutput(result.toUShort(2)))
        }
    }

    "part 1: the signal that is ultimately provided to wire a" {
        val input = listOf(
            "123 -> x",
            "456 -> y",
            "x AND y -> d",
            "x OR y -> e",
            "x LSHIFT 2 -> f",
            "y RSHIFT 2 -> g",
            "NOT x -> h",
            "NOT y -> i",
            "d -> a"
        )
        SomeAssemblyRequired().part1(input) shouldBe 72
    }

    "part 2 the signal that is ultimately provided to wire a when b is overridden with a" {
        val input = listOf("NOT b -> a", "1 -> b")
        SomeAssemblyRequired().part2(input) shouldBe 1
    }
})
