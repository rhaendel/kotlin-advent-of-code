package de.ronny_h.aoc.extensions.collections

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.shouldBe

val ListsTest by testSuite {
    test("split an empty list") {
        listOf<String>().split("") shouldBe listOf()
    }

    test("split a list with only a single block") {
        listOf("a", "b").split("") shouldBe listOf(listOf("a", "b"))
    }

    test("split a list with multiple blocks") {
        listOf("a", "b", "", "c", "d").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    test("split a list with multiple blocks and consecutive delimiting lines") {
        listOf("a", "b", "", "", "", "c", "d").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    test("split a list with multiple blocks using the default delimiter") {
        listOf("a", "b", "", "c", "d").split() shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    test("split a list with multiple blocks using a non-empty delimiter") {
        listOf("a", "b", "---", "c", "d").split("---") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    test("split a list with multiple blocks ending with the delimiter line") {
        listOf("a", "b", "", "c", "d", "").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    test("filterMaxBy an empty list returns an empty list") {
        emptyList<String>().filterMaxBy(String::length) shouldBe emptyList()
    }

    test("filterMaxBy with a unique max returns exactly that") {
        listOf("1", "123", "12").filterMaxBy(String::length) shouldBe listOf("123")
    }

    test("filterMaxBy with more than one max element returns all max elements") {
        listOf("1", "123", "321").filterMaxBy(String::length) shouldBe listOf("123", "321")
    }

    testSuite(
        "filterMinBy",
        mapOf(
            emptyList<Int>() to emptyList(),
            listOf(1, 2, 3) to listOf(1),
            listOf(1, 2, 1) to listOf(1, 1),
        )
    ) { list, minimums ->
        list.filterMinBy { it } shouldBe minimums
    }

    testSuite(
        "minByUniqueOrNull",
        mapOf(
            listOf(1) to 1,
            listOf(1, 2, 3) to 1,
            listOf(2, 1, 3) to 1,
            listOf(2, 2, 1, 3) to 1,
            emptyList<Int>() to null,
            listOf(1, 1, 3) to null,
        )
    ) { list, expected ->
        list.minByUniqueOrNull { it } shouldBe expected
    }

    testSuite(
        "allUniqueBy",
        mapOf(
            emptyList<Int>() to true,
            listOf(1, 2, 3) to true,
            listOf(1, 1, 3) to false,
        )
    ) { list, unique ->
        list.allUniqueBy { it } shouldBe unique
    }

    testSuite(
        "firstDuplicate",
        mapOf(
            emptyList<Int>() to null,
            listOf(1) to null,
            listOf(1, 2) to null,
            listOf(1, 2, 2) to 2,
            listOf(1, 2, 2, 3, 3) to 2,
            listOf(1, 3, 3, 2, 2) to 3,
        )
    ) { list, element ->
        list.firstDuplicate { it } shouldBe element
    }
}
