package de.ronny_h.aoc.extensions

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val HashingTest by testSuite {

    test("md5() converts a String to an MD5 hash") {
        "This is just a test".md5() shouldBe "df0a9498a65ca6e20dc58022267f339a"
    }

}
