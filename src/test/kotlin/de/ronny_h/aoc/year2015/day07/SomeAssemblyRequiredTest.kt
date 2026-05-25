package de.ronny_h.aoc.year2015.day07

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.year2015.day07.Operator.BinaryOperator.*
import de.ronny_h.aoc.year2015.day07.Operator.UnaryOperator.NOT
import de.ronny_h.aoc.year2015.day07.OperatorOutput.NoSignal
import de.ronny_h.aoc.year2015.day07.OperatorOutput.ValueOutput
import io.kotest.matchers.shouldBe

val SomeAssemblyRequiredTest by testSuite {

    test("parse scalar") {
        SomeAssemblyRequired().parse("0 -> c") shouldBe Scalar(0U, "c")
    }

    test("parse re-wiring") {
        SomeAssemblyRequired().parse("lx -> a") shouldBe ReWire("lx", "a")
    }

    test("parse unary operation") {
        SomeAssemblyRequired().parse("NOT ii -> ij") shouldBe UnaryOperation("ii", NOT, "ij")
    }

    test("parse binary operation") {
        SomeAssemblyRequired().parse("af AND ah -> ai") shouldBe BinaryOperation("af", "ah", AND, "ai")
        SomeAssemblyRequired().parse("du OR dt -> dv") shouldBe BinaryOperation("du", "dt", OR, "dv")
        SomeAssemblyRequired().parse("eo LSHIFT 15 -> es") shouldBe BinaryOperation("eo", "15", LSHIFT, "es")
        SomeAssemblyRequired().parse("eo RSHIFT 5 -> es") shouldBe BinaryOperation("eo", "5", RSHIFT, "es")
    }

    test("a scalar always represents a value") {
        Scalar(1U, "a").execute(mapOf("a" to 2U)) shouldBe Output("a", ValueOutput(1U))
    }

    test("a re-wiring returns NoSignal if the argument is not provided") {
        ReWire("a", "b").execute(mapOf()) shouldBe Output("b", NoSignal)
    }

    test("a re-wiring returns the result if the argument not provided") {
        ReWire("a", "b").execute(mapOf("a" to 7U)) shouldBe Output("b", ValueOutput(7U))
    }

    test("an unary operation returns NoSignal if the argument is not provided") {
        UnaryOperation("b", NOT, "a").execute(mapOf()) shouldBe Output("a", NoSignal)
    }

    test("an unary operation returns the result if the argument is provided") {
        UnaryOperation("b", NOT, "a").execute(mapOf("b" to "0000000000000000".toUShort(2))) shouldBe Output(
            "a",
            ValueOutput("1111111111111111".toUShort(2))
        )
    }

    testSuite("all binary operations return NoSignal if not both arguments are provided") {
        listOf(AND, OR, LSHIFT, RSHIFT).forEach { op ->
            test("$op") {
                BinaryOperation("a", "b", op, "c").execute(emptyMap()) shouldBe Output("c", NoSignal)
                BinaryOperation("a", "b", op, "c").execute(mapOf("a" to 0U)) shouldBe Output("c", NoSignal)
                BinaryOperation("a", "b", op, "c").execute(mapOf("b" to 0U)) shouldBe Output("c", NoSignal)
            }
        }
    }

    testSuite("all binary operations return the result if both arguments are provided") {
        val values = mapOf(
            "a" to "0000000011111111".toUShort(2),
            "b" to "0000000000000001".toUShort(2),
        )
        mapOf(
            AND to "0000000000000001",
            OR to "0000000011111111",
            LSHIFT to "0000000111111110",
            RSHIFT to "0000000001111111",
        ).forEach { (op, result) ->
            test("$op, $result") {
                BinaryOperation("a", "b", op, "c").execute(values) shouldBe Output("c", ValueOutput(result.toUShort(2)))
            }
        }
    }

    testSuite("all binary operations return the result if one argument is provided and the other is a scalar") {
        val values = mapOf(
            "a" to "0000000011111111".toUShort(2),
        )
        mapOf(
            AND to "0000000000000001",
            OR to "0000000011111111",
            LSHIFT to "0000000111111110",
            RSHIFT to "0000000001111111",
        ).forEach { (op, result) ->
            test("$op, $result") {
                BinaryOperation("a", "1", op, "c").execute(values) shouldBe Output("c", ValueOutput(result.toUShort(2)))
            }
        }
    }

    test("part 1: the signal that is ultimately provided to wire a") {
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

    test("part 2 the signal that is ultimately provided to wire a when b is overridden with a") {
        val input = listOf("NOT b -> a", "1 -> b")
        SomeAssemblyRequired().part2(input) shouldBe 1
    }
}
