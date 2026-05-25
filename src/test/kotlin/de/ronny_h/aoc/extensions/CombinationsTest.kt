package de.ronny_h.aoc.extensions

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.testballoon.testSuite
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

val CombinationsTest by testSuite {

    test("an empty list yields an empty sequence") {
        listOf<Int>().combinations().toList() shouldBe emptyList()
    }

    test("a list with one element yields an empty sequence") {
        listOf(1).combinations().toList() shouldBe emptyList()
    }

    test("a list with two elements yields both combinations") {
        listOf(1, 2).combinations().toList() shouldBe listOf(1 to 2, 2 to 1)
    }

    test("a list with three elements yields all six combinations") {
        listOf(1, 2, 3).combinations().toList() shouldBe
                listOf(
                    1 to 2,
                    1 to 3,
                    2 to 1,
                    2 to 3,
                    3 to 1,
                    3 to 2
                )
    }

    test("a range with three elements yields all six combinations") {
        (1..3).combinations().toList() shouldBe
                listOf(
                    1 to 2,
                    1 to 3,
                    2 to 1,
                    2 to 3,
                    3 to 1,
                    3 to 2
                )
    }

    test("a list with non-unique elements yields non-unique combinations but still skips the identical ones") {
        listOf(1, 1, 2).combinations().toList() shouldBe
                listOf(
                    1 to 1,
                    1 to 2,
                    1 to 1,
                    1 to 2,
                    2 to 1,
                    2 to 1
                )
    }

    test("combinationsOf() with the first list empty yields an empty sequence") {
        combinationsOf(listOf<Int>(), listOf("a", "b")).toList() shouldBe emptyList()
    }

    test("combinationsOf() with the second list empty yields an empty sequence") {
        combinationsOf(listOf(1, 2), listOf<String>()).toList() shouldBe emptyList()
    }

    test("combinationsOf() two non-empty lists yields all combinations of their elements") {
        combinationsOf(listOf(1, 2), listOf("a", "b")).toList() shouldBe
                listOf(
                    1 to "a",
                    1 to "b",
                    2 to "a",
                    2 to "b",
                )
    }

    testSuite(
        "permutationsOf() yields all possible permutations of the given list elements",
        mapOf(
            emptyList<Int>() to listOf(emptyList()),
            listOf(1) to listOf(listOf(1)),
            listOf(1, 2) to listOf(listOf(1, 2), listOf(2, 1)),
            listOf(1, 2, 3) to listOf(
                listOf(1, 2, 3),
                listOf(1, 3, 2),
                listOf(2, 1, 3),
                listOf(2, 3, 1),
                listOf(3, 1, 2),
                listOf(3, 2, 1),
            ),
        ),
    ) { input, permutations ->
        permutationsOf(input).toList() shouldContainExactlyInAnyOrder permutations
    }


    testSuite(
        "allSublistsOf for lists with up to three elements",
        mapOf(
            emptyList<Int>() to listOf(emptyList()),
            listOf(1) to listOf(emptyList(), listOf(1)),
            listOf(1, 2) to listOf(emptyList(), listOf(1), listOf(2), listOf(1, 2)),
            listOf(1, 2, 3) to listOf(
                emptyList(), listOf(1), listOf(2), listOf(1, 2),
                listOf(3), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)
            ),
        ),
    ) { list, expected ->
        allSublistsOf(list).toList() shouldBe expected
    }

    data class row(val n: Int, val sum: Int, val expected: List<List<Int>>)

    testSuite(
        "sequenceNumbersOfEqualSum(n, sum) yields all lists of 'n' elements with a sum of 'sum'",
        listOf(
            row(1, 100, listOf(listOf(100))),
            row(2, 1, listOf(listOf(1, 0), listOf(0, 1))),
            row(2, 2, listOf(listOf(2, 0), listOf(1, 1), listOf(0, 2))),
            row(2, 3, listOf(listOf(3, 0), listOf(2, 1), listOf(1, 2), listOf(0, 3))),
            row(
                3, 3, listOf(
                    listOf(3, 0, 0), listOf(2, 1, 0), listOf(1, 2, 0), listOf(0, 3, 0),
                    listOf(2, 0, 1), listOf(1, 1, 1), listOf(0, 2, 1),
                    listOf(1, 0, 2), listOf(0, 1, 2),
                    listOf(0, 0, 3),
                )
            ),
        ),
    ) { (n, sum, expected) ->
        sequenceNumbersOfEqualSum(n, sum).toList() shouldBe expected
    }
}
