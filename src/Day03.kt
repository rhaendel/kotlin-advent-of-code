fun main() {
    val day = "Day03"

    val mulRegex = """(?<mul>mul\((\d{1,3}),(\d{1,3})\))"""
    val doRegex = """(?<do>do\(\))"""
    val dontRegex = """(?<dont>don't\(\))"""

    fun multiply(match: MatchResult): Int {
        val (_, left, right) = match.destructured
        return left.toInt() * right.toInt()
    }

    fun findAndMultiply(line: String) = mulRegex
        .toRegex()
        .findAll(line)
        .sumOf(::multiply)

    fun part1(input: List<String>): Int {
        return input.map(::findAndMultiply).sum()
    }

    fun part2(input: List<String>): Int {
        var doIt = true

        return input
            .flatMap { line ->
                "$mulRegex|$doRegex|$dontRegex"
                    .toRegex()
                    .findAll(line)
            }.sumOf { match ->
                match.groups["do"]?.let { doIt = true }
                match.groups["dont"]?.let { doIt = false }
                match.groups["mul"]?.let { if (doIt) multiply(match) else 0 } ?: 0
            }
    }

    println("$day part 1")

    printAndCheck(listOf("blamul(2,3)*x_mul(2,2)+mul(32,64]t"), ::part1, 10)

    val testInput = readInput("${day}_test")
    printAndCheck(testInput, ::part1, 161)

    val input = readInput(day)
    printAndCheck(input, ::part1, 174960292)


    println("$day part 2")

    printAndCheck(
        listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"),
        ::part2, 48
    )
    printAndCheck(testInput, ::part2, 161)
    printAndCheck(input, ::part2, 56275602)
}
