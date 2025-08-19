package de.ronny_h.aoc.year2017.day04

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class HighEntropyPassphrasesTest : StringSpec({

    "part 1: the example: passphrases without repeating words are valid" {
        forAll(
            row("aa bb cc dd ee", 1),
            row("aa bb cc dd aa", 0),
            row("aa bb cc dd aaa", 1),
        ) { passphrase, numberOfValidPhrases ->
            HighEntropyPassphrases().part1(listOf(passphrase)) shouldBe numberOfValidPhrases
        }
    }

    "part 2: the example: passphrases without anagrams are valid" {
        forAll(
            row("a ab abc abd abf abj", 1),
            row("abcde fghij", 1),
            row("abcde xyz ecdab", 0),
            row("iiii oiii ooii oooi oooo", 1),
            row("oiii ioii iioi iiio", 0),
        ) { passphrase, numberOfValidPhrases ->
            HighEntropyPassphrases().part2(listOf(passphrase)) shouldBe numberOfValidPhrases
        }
    }
})
