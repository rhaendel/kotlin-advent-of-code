package de.ronny_h.aoc.extensions.collections

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ListsTest : StringSpec({
    "split an empty list" {
        listOf<String>().split("") shouldBe listOf()
    }

    "split a list with only a single block" {
        listOf("a", "b").split("") shouldBe listOf(listOf("a", "b"))
    }

    "split a list with multiple blocks" {
        listOf("a", "b", "", "c", "d").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    "split a list with multiple blocks and consecutive delimiting lines" {
        listOf("a", "b", "", "", "", "c", "d").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    "split a list with multiple blocks using the default delimiter" {
        listOf("a", "b", "", "c", "d").split() shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    "split a list with multiple blocks using a non-empty delimiter" {
        listOf("a", "b", "---", "c", "d").split("---") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    "split a list with multiple blocks ending with the delimiter line" {
        listOf("a", "b", "", "c", "d", "").split("") shouldBe listOf(listOf("a", "b"), listOf("c", "d"))
    }

    "filterMaxBy an empty list returns an empty list" {
        emptyList<String>().filterMaxBy(String::length) shouldBe emptyList()
    }

    "filterMaxBy with a unique max returns exactly that" {
        listOf("1", "123", "12").filterMaxBy(String::length) shouldBe listOf("123")
    }

    "filterMaxBy with more than one max element returns all max elements" {
        listOf("1", "123", "321").filterMaxBy(String::length) shouldBe listOf("123", "321")
    }

    "minByUniqueOrNull" {
        forAll(
            row(listOf(1), 1),
            row(listOf(1, 2, 3), 1),
            row(listOf(2, 1, 3), 1),
            row(listOf(2, 2, 1, 3), 1),
            row(emptyList(), null),
            row(listOf(1, 1, 3), null),
        ) { list, expected ->
            list.minByUniqueOrNull { it } shouldBe expected
        }
    }

    "allUniqueBy" {
        forAll(
            row(emptyList(), true),
            row(listOf(1, 2, 3), true),
            row(listOf(1, 1, 3), false),
        ) { list, unique ->
            list.allUniqueBy { it } shouldBe unique
        }
    }

    "firstDuplicate" {
        forAll(
            row(emptyList(), null),
            row(listOf(1), null),
            row(listOf(1, 2), null),
            row(listOf(1, 2, 2), 2),
            row(listOf(1, 2, 2, 3, 3), 2),
            row(listOf(1, 3, 3, 2, 2), 3),
        ) { list, element ->
            list.firstDuplicate { it } shouldBe element
        }
    }
})
