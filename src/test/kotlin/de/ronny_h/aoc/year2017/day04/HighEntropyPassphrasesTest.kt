package de.ronny_h.aoc.year2017.day04

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val HighEntropyPassphrasesTest by testSuite {

    testSuite("part 1: the example: passphrases without repeating words are valid") {
        mapOf(
            "aa bb cc dd ee" to 1,
            "aa bb cc dd aa" to 0,
            "aa bb cc dd aaa" to 1,
        ).forEach { (passphrase, numberOfValidPhrases) ->
            test("$passphrase, $numberOfValidPhrases") {
                HighEntropyPassphrases().part1(listOf(passphrase)) shouldBe numberOfValidPhrases
            }
        }
    }

    testSuite("part 2: the example: passphrases without anagrams are valid") {
        mapOf(
            "a ab abc abd abf abj" to 1,
            "abcde fghij" to 1,
            "abcde xyz ecdab" to 0,
            "iiii oiii ooii oooi oooo" to 1,
            "oiii ioii iioi iiio" to 0,
        ).forEach { (passphrase, numberOfValidPhrases) ->
            test("$passphrase, $numberOfValidPhrases") {
                HighEntropyPassphrases().part2(listOf(passphrase)) shouldBe numberOfValidPhrases
            }
        }
    }
}
