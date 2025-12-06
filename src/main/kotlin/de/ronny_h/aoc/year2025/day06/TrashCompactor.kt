package de.ronny_h.aoc.year2025.day06

import de.ronny_h.aoc.AdventOfCode

fun main() = TrashCompactor().run(6100348226985, 12377473011151)

class TrashCompactor : AdventOfCode<Long>(2025, 6) {
    override fun part1(input: List<String>): Long =
        input.parseProblemsVertically().sumOf(Problem::solve)

    override fun part2(input: List<String>): Long =
        input.parseProblemsByColumnsRightToLeft().sumOf(Problem::solve)
}

private val whitespacePattern = " +".toPattern()

fun List<String>.parseProblemsVertically(): List<Problem> {
    val numbers = dropLast(1).map { row ->
        row.trim().split(whitespacePattern).map {
            it.toLong()
        }
    }
    val operations = parseOperations()
    return numbers.first().indices.mapIndexed { col, _ ->
        Problem(
            numbers.indices.map { row ->
                numbers[row][col]
            },
            operations[col],
        )
    }
}

private fun List<String>.parseOperations(): List<Operation> = last().trim().split(whitespacePattern).map {
    when (it) {
        "+" -> add
        "*" -> multiply
        else -> error("invalid operation: '$it'")
    }
}

fun List<String>.parseProblemsByColumnsRightToLeft(): List<Problem> {
    val rows = dropLast(1)
    val operations = parseOperations().reversed().iterator()

    val problems = mutableListOf<Problem>()
    val numbers = mutableListOf<Long>()
    for (col in first().indices.reversed()) {
        if (rows.all { it[col] == ' ' }) {
            problems.add(Problem(numbers.toList(), operations.next()))
            numbers.clear()
            continue
        }
        val number = rows.fold("") { acc, row ->
            acc + row[col]
        }.trim().toLong()
        numbers.add(number)
    }
    problems.add(Problem(numbers.toList(), operations.next()))

    return problems
}

data class Problem(val numbers: List<Long>, val operation: Operation) {
    fun solve(): Long = numbers.reduce(operation)
}

fun interface Operation : Function2<Long, Long, Long>

val add = Operation { a, b -> a + b }
val multiply = Operation { a, b -> a * b }
