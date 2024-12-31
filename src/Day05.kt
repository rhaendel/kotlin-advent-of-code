const val DAY = "Day05"

fun main() {

    println("part 1")

    fun parseRules(input: List<String>): List<Pair<Int, Int>> = input
        .takeWhile { it.contains("|") }
        .map {
            val (a, b) = it.split("|")
            a.toInt() to b.toInt()
        }

    fun parseUpdates(input: List<String>): List<List<Int>> = input
        .dropWhile { !it.contains(",") }
        .map {
            it.split(",")
                .map(String::toInt)
        }

    fun part1(input: List<String>): Int {
        val rules = parseRules(input)
        val updates = parseUpdates(input)
        val pageComparator = PageComparator(rules)
        val orderedUpdates = updates.filter { it == it.sortedWith(pageComparator) }
        return orderedUpdates.sumOf { it[it.size / 2] }
    }

    printAndCheck(
        part1(
            listOf(
                "1|2", "2|3", "3|4", "4|5", "",
                "1,2,3,4,5", "1,3,2", "1,2,3"
            )
        ), 5
    )

    val test1Input = readInput("${DAY}_test")
    printAndCheck(part1(test1Input), 143)

    val input = readInput(DAY)
    printAndCheck(part1(input), 6384)


    println("part 2")

    fun part2(input: List<String>): Int {
        val rules = parseRules(input)
        val updates = parseUpdates(input)
        val pageComparator = PageComparator(rules)
        val notOrderedUpdates = updates.filter { it != it.sortedWith(pageComparator) }
        return notOrderedUpdates
            .map { it.sortedWith(pageComparator) }
            .sumOf { it[it.size / 2] }
    }

    printAndCheck(
        part2(
            listOf(
                "1|2", "2|3", "3|4", "4|5", "",
                "1,2,3,4,5", "1,3,2", "1,2,3"
            )
        ), 2
    )

    val test2Input = readInput("${DAY}_test")
    printAndCheck(part2(test2Input), 123)
    printAndCheck(part2(input), 5353)
}

class PageComparator(rules: List<Pair<Int, Int>>) : Comparator<Int> {

    private val rules: Map<Int, Set<Int>> = HashMap<Int, MutableSet<Int>>().apply {
        rules.forEach {
            putIfAbsent(it.first, mutableSetOf(it.second))?.add(it.second)
        }
    }
    private val rulesInverted: Map<Int, Set<Int>> = HashMap<Int, MutableSet<Int>>().apply {
        rules.forEach {
            putIfAbsent(it.second, mutableSetOf(it.first))?.add(it.first)
        }
    }

    override fun compare(o1: Int?, o2: Int?): Int {
        if (rules[o1]?.contains(o2) == true) {
            // pages are in the right order: o1 < o2
            return -1
        }
        if (rulesInverted[o1]?.contains(o2) == true) {
            // pages are in the wrong order: o1 > o2
            return 1
        }
        // the rules don't say anything about these two pages relatively to another
        // -> consider them equal
        return 0
    }

}
