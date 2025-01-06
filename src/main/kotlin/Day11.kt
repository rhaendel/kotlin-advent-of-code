fun main() {
    val day = "Day11"

    println("$day part 1")

    fun part1(input: List<String>): Int {
        val stones = input.first().split(" ").map(String::toLong).toMutableList()

        repeat(25) {
            val iterator = stones.listIterator()
            for (stone in iterator) {
                if (stone == 0L) {
                    iterator.set(1)
                } else {
                    val stoneString = "$stone"
                    if (stoneString.length % 2 == 0) {
                        iterator.set(stoneString.substring(0, stoneString.length / 2).toLong())
                        iterator.add(stoneString.substring(stoneString.length / 2, stoneString.length).toLong())
                    } else {
                        iterator.set(stone * 2024)
                    }
                }
            }
        }

        return stones.count()
    }

    printAndCheck(
        """
            125 17
        """.trimIndent().lines(),
        ::part1, 55312
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 193899)
}
