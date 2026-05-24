package de.ronny_h.aoc.year2024.day21

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

val KeypadConundrumTest by testSuite {
    val input = """
        029A
        980A
        179A
        456A
        379A
    """.asList()

    test("make the 1st robot type 029A") {
        val numericKeypad = Keypad(numericKeypadLayout)
        numericKeypad.moveTo('0') shouldBe listOf("<A")
        numericKeypad.moveTo('2') shouldBe listOf("^A")
        numericKeypad.moveTo('9') shouldContainExactlyInAnyOrder listOf("^^>A", "^>^A", ">^^A")
        numericKeypad.moveTo('A') shouldBe listOf("vvvA")
    }

    test("make the 2nd robot type ^^>A") {
        val directionalKeypad = Keypad(directionalKeypadLayout)
        directionalKeypad.moveTo('^') shouldBe listOf("<A")
        directionalKeypad.moveTo('^') shouldBe listOf("A")
        directionalKeypad.moveTo('>') shouldContainExactlyInAnyOrder listOf(">vA", "v>A")
        directionalKeypad.moveTo('A') shouldBe listOf("^A")
    }

    test("input v<<A>>^A, last robot") {
        KeypadConundrum().input("v<<A>>^A", Keypad(directionalKeypadLayout), 1) shouldBe 18L
    }

    test("input 0, all robots") {
        KeypadConundrum().input("0", Keypad(numericKeypadLayout), 3) shouldBe 18L
    }

    test("input with depth 1") {
        KeypadConundrum().input("v", Keypad(directionalKeypadLayout), 1) shouldBe 3
    }

    test("part 1: The sum of the complexities of the five codes") {
        KeypadConundrum().part1(input) shouldBe 126384
    }
}


/*
Example Nodes tree for input("<", Keypad(directionalKeypadLayout), depth = 2):
Node(code=<, children=[
    Node(code=<, children=[
        Node(code=v<<A, children=[
            Node(code=v, children=[
                Node(code=v<A, children=[]), Node(code=<vA, children=[])
            ]),
            Node(code=<, children=[
                Node(code=<A, children=[])
            ]),
            Node(code=<, children=[
                Node(code=A, children=[])
            ]),
            Node(code=A, children=[
                Node(code=>^>A, children=[]), Node(code=>>^A, children=[])
            ])
        ]),
        Node(code=<v<A, children=[
            Node(code=<, children=[
                Node(code=v<<A, children=[]), Node(code=<v<A, children=[])
            ]),
            Node(code=v, children=[
                Node(code=>A, children=[])
            ]),
            Node(code=<, children=[
                Node(code=<A, children=[])
            ]),
            Node(code=A, children=[
                Node(code=>^>A, children=[]), Node(code=>>^A, children=[])
            ])
        ])
    ])
])
*/
