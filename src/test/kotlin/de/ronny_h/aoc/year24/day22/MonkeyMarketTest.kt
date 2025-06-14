package de.ronny_h.aoc.year24.day22

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MonkeyMarketTest : StringSpec({

    val input = """
        1
        10
        100
        2024
    """.asList()
    val market = MonkeyMarket()

    "next secret number of 123" {
        market.nextSecretNumber(123) shouldBe 15887950
    }

    "next ten secret numbers of 123" {
        var secret = 123L
        buildList {
            repeat(10) {
                secret = market.nextSecretNumber(secret)
                add(secret)
            }
        } shouldBe listOf(
            15887950,
            16495136,
            527345,
            704524,
            1553684,
            12683156,
            11100544,
            12249484,
            7753432,
            5908254,
        )
    }

    "part 1" {
        market.part1(input) shouldBe 37327623
    }
})
