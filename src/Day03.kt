fun main() {
    val regex = """mul\((\d+),(\d+)\)""".toRegex()

    fun findAndMultiply(line: String) = regex
        .findAll(line)
        .sumOf {
            val (left, right) = it.destructured
            left.toInt() * right.toInt()
        }

    fun part1(input: List<String>): Int {
        return input.map(::findAndMultiply).sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("blamul(2,3)*x_mul(2,2)+mul(32,64]t")) == 10)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
}
