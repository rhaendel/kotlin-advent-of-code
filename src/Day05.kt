const val DAY = "Day05"

fun main() {

    println("part 1")

    fun part1(input: List<String>): Int {
        val rules = input
            .takeWhile { it.contains("|") }
            .map {
                val (a, b) = it.split("|")
                a.toInt() to b.toInt()
            }
        val updates = input
            .dropWhile { !it.contains(",") }
            .map {
                it.split(",")
                    .map(String::toInt)
            }
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

    val test1Input = readInput("${DAY}_test1")
    printAndCheck(part1(test1Input), 143)

    val input = readInput(DAY)
    printAndCheck(part1(input), 6384)
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
