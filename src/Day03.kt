fun main() {
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

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("blamul(2,3)*x_mul(2,2)+mul(32,64]t")) == 10)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)

    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)
    check(part2(testInput) == 161)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
