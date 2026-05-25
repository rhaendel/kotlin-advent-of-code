package de.ronny_h.aoc.year2015.day12

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val JSAbacusFrameworkTest by testSuite {

    val day12 = JSAbacusFramework()

    test("part 1: a sum of 6") {
        day12.part1(listOf("[1,2,3]")) shouldBe 6
        day12.part1(listOf("{\"a\":2,\"b\":4}")) shouldBe 6
    }

    test("part 1: a sum of 3") {
        day12.part1(listOf("[[[3]]]")) shouldBe 3
        day12.part1(listOf("{\"a\":{\"b\":4},\"c\":-1}")) shouldBe 3
    }

    test("part 1: a sum of 0") {
        day12.part1(listOf("{\"a\":[-1,1]}")) shouldBe 0
        day12.part1(listOf("[-1,{\"a\":1}]")) shouldBe 0
        day12.part1(listOf("[]")) shouldBe 0
        day12.part1(listOf("{}")) shouldBe 0
    }

    test("readNonRedObject of simple array") {
        "[1,2,3]".filterOutRedObjects() shouldBe Pair("[1,2,3]", 7)
    }

    test("readNonRedObject of simple object") {
        """{"a":1}""".filterOutRedObjects() shouldBe Pair("""{"a":1}""", 7)
    }

    test("readNonRedObject of simple object containing value red") {
        """{"a":"red"}""".filterOutRedObjects() shouldBe Pair("{}", 11)
    }

    test("readNonRedObject of nested object") {
        """{"a":{"b":"c"}}""".filterOutRedObjects() shouldBe Pair("""{"a":{"b":"c"}}""", 15)
    }

    test("readNonRedObject of nested object containing value red") {
        """{"a":{"b":"red"}}""".filterOutRedObjects() shouldBe Pair("""{"a":{}}""", 17)
    }

    test("readNonRedObject with two red objects on nested level") {
        """{"a":{"b":"red"},"c":{"d":"red"}}""".filterOutRedObjects() shouldBe Pair("""{"a":{},"c":{}}""", 33)
    }

    test("readNonRedObject of double-nested object") {
        """{"a":{"b":{"c":"d"}},"e":1}""".filterOutRedObjects() shouldBe Pair(
            """{"a":{"b":{"c":"d"}},"e":1}""",
            27
        )
    }

    test("readNonRedObject of double-nested object containing red") {
        """{"a":{"b":{"c":"red"},"d":1}}""".filterOutRedObjects() shouldBe Pair("""{"a":{"b":{},"d":1}}""", 29)
    }

    test("readNonRedObject of double-nested object containing red on mid-level") {
        """{"a":{"b":"red","c":{"d":0},"d":1}}""".filterOutRedObjects() shouldBe Pair("""{"a":{}}""", 35)
    }

    test("part 2: a sum of 6") {
        day12.part2(listOf("[1,2,3]")) shouldBe 6
    }

    test("part 2: a sum of 4 when ignoring object with a value of red") {
        day12.part2(listOf("""[1,{"c":"red","b":2},3]""")) shouldBe 4
    }

    test("part 2: a sum of 0 when ignoring object with a value of red") {
        day12.part2(listOf("""{"d":"red","e":[1,2,3,4],"f":5}""")) shouldBe 0
    }

    test("part 2: a sum of 6 with red in an array") {
        day12.part2(listOf("""[1,"red",5]""")) shouldBe 6
    }
}
