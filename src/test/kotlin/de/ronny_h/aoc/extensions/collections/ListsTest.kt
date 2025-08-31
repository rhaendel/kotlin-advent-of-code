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

    "filterMaxBy an empty list returns an empty list" {
        emptyList<String>().filterMaxBy(String::length) shouldBe emptyList()
    }

    "filterMaxBy with a unique max returns exactly that" {
        listOf("1", "123", "12").filterMaxBy(String::length) shouldBe listOf("123")
    }

    "filterMaxBy with more than one max element returns all max elements" {
        listOf("1", "123", "321").filterMaxBy(String::length) shouldBe listOf("123", "321")
    }
})
