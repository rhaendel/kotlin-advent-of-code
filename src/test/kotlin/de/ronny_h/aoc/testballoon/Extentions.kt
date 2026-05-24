package de.ronny_h.aoc.testballoon

import de.infix.testBalloon.framework.core.Test
import de.infix.testBalloon.framework.core.TestSuiteScope

fun <K, V> TestSuiteScope.test(
    values: Map<K, V>,
    action: suspend Test.ExecutionScope.(K, V) -> Unit,
) = values.forEach { (k, v) ->
    test("$k, $v") {
        action(k, v)
    }
}

fun <K, V> TestSuiteScope.testSuite(
    name: String,
    values: Map<K, V>,
    action: suspend Test.ExecutionScope.(K, V) -> Unit,
) = testSuite(name) {
    values.forEach { (k, v) ->
        test("$k, $v") {
            action(k, v)
        }
    }
}
