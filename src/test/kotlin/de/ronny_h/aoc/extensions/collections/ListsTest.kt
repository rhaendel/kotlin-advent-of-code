package de.ronny_h.aoc.extensions.collections

import io.kotest.core.spec.style.StringSpec
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
})
