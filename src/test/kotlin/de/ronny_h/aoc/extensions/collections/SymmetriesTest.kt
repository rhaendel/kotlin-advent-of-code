package de.ronny_h.aoc.extensions.collections

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val SymmetriesTest by testSuite {

    test("an empty list is symmetrical") {
        listOf<Int>().isSymmetrical() shouldBe true
    }

    test("a symmetrical list is symmetrical") {
        listOf(1, 2, 3, 2, 1).isSymmetrical() shouldBe true
    }

    test("a symmetrical list of Strings is symmetrical") {
        listOf("a", "b", "c", "b", "a").isSymmetrical() shouldBe true
    }

    test("an asymmetrical list is not symmetrical") {
        listOf(4, 2, 3, 2, 1).isSymmetrical() shouldBe false
    }
}
