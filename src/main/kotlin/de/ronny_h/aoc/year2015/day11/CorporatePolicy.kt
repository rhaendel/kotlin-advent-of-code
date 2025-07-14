package de.ronny_h.aoc.year2015.day11

import de.ronny_h.aoc.AdventOfCode

fun main() = CorporatePolicy().run("vzbxxyzz", "vzcaabcc")

class CorporatePolicy : AdventOfCode<String>(2015, 11) {
    override fun part1(input: List<String>) = input.first().rotate()

    override fun part2(input: List<String>) = input.first().rotate().rotate()

    fun rule0AppliesTo(password: String) = password.matches("[a-z]{8}".toRegex())

    // must include one increasing straight of at least three letters
    fun rule1Applies(password: String): Boolean {
        for (i in 0..password.length - 3) {
            if (password[i].inc() == password[i + 1] && password[i + 1].inc() == password[i + 2]) {
                return true
            }
        }
        return false
    }

    private val lettersToSkip = listOf('i', 'o', 'l')
    private val ambiguousLettersRegex = lettersToSkip.joinToString("|").toRegex()

    // may not contain the letters i, o, or l
    fun rule2Applies(password: String) = !password.contains(ambiguousLettersRegex)

    // must contain at least two different, non-overlapping pairs of letters
    fun rule3Applies(password: String): Boolean {
        var foundOnePair = false
        var lastWasAPair = false
        for (i in 0..password.length - 2) {
            if (lastWasAPair) {
                // no overlappings
                lastWasAPair = false
            } else {
                if (password[i] == password[i + 1]) {
                    if (foundOnePair) {
                        return true
                    }
                    foundOnePair = true
                    lastWasAPair = true
                }
            }
        }
        return false
    }

    private fun String.rotate(): String {
        require(rule0AppliesTo(this)) { "a password has to be exactly 8 lowercase letters: '${this}" }

        var newPassword = this
        do {
            newPassword = newPassword.inc(lettersToSkip)
        } while (newPassword.notAllRulesApply())

        check(rule0AppliesTo(newPassword)) { "a password has to be exactly 8 lowercase letters: '$newPassword" }
        return newPassword
    }

    private fun String.notAllRulesApply() = !rule1Applies(this) || !rule2Applies(this) || !rule3Applies(this)
}

fun String.inc(toSkip: List<Char> = emptyList()): String {
    val highestChar = 'z'
    require(highestChar !in toSkip) { "incrementation only works correct if the highest char is not to skip" }

    var suffix = ""
    var incIndex = lastIndex
    while (incIndex >= 0) {
        val prefix = substring(0..<incIndex)
        if (get(incIndex) == highestChar) {
            suffix = "a$suffix"
            incIndex--
        } else {
            var incChar = get(incIndex).inc()
            while (incChar in toSkip) {
                incChar = incChar.inc()
            }
            return prefix + incChar + suffix
        }
    }
    throw StringOverflowException("String '$this' cannot be incremented without making it longer.")
}

class StringOverflowException(message: String) : Exception(message)
