package de.ronny_h.aoc.year2017.day09

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2017.day09.State.*

fun main() = StreamProcessing().run(16689, 7982)

class StreamProcessing : AdventOfCode<Int>(2017, 9) {
    override fun part1(input: List<String>): Int = input.single().parseGroups().score
    override fun part2(input: List<String>): Int = input.single().parseGroups().garbage
}

enum class State {
    Start, End, InGroup, InGarbage, Canceled
}

data class Groups(
    val count: Int,
    val score: Int,
    val garbage: Int,
)

/*
The state transitions are:
                  --<-->            --!-->
S --{--> in-group        in-garbage        canceled
E <--}--          <-->--  |      |  <--*--
                           -*^!>-

in-group: { -> level++
          } -> level--
 */
fun String.parseGroups(): Groups {
    var state = Start
    var nestingLevel = 0
    var groupCount = 0
    var score = 0
    var garbage = 0

    forEach { c ->
        when (state) {
            Start -> {
                state = when (c) {
                    '{' -> {
                        nestingLevel++
                        InGroup
                    }

                    else -> error("illegal character $c in state $state")
                }
            }

            End -> error("illegal character $c in state $state")

            InGroup -> {
                state = when (c) {
                    ',' -> InGroup
                    '{' -> {
                        nestingLevel++
                        InGroup
                    }

                    '}' -> {
                        score += nestingLevel
                        nestingLevel--
                        groupCount++
                        if (nestingLevel > 0) {
                            InGroup
                        } else {
                            End
                        }
                    }

                    '<' -> InGarbage
                    else -> error("illegal character $c in state $state")
                }
            }

            InGarbage -> {
                state = when (c) {
                    '>' -> InGroup
                    '!' -> Canceled
                    else -> {
                        garbage++
                        InGarbage
                    }
                }
            }

            Canceled -> {
                state = InGarbage
            }
        }
    }
    check(state == End) { "state should be End at the end, but was: $state" }
    check(nestingLevel == 0) { "nesting level should be 0 at the end, but was: $nestingLevel" }
    return Groups(groupCount, score, garbage)
}
