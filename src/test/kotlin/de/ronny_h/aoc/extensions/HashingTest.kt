package de.ronny_h.aoc.extensions

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HashingTest : StringSpec({

    "md5() converts a String to an MD5 hash" {
        "This is just a test".md5() shouldBe "df0a9498a65ca6e20dc58022267f339a"
    }

})
