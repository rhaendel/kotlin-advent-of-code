fun main() {
    val day = "Day11"

    println("$day part 1")

    fun parseStones(input: List<String>) = input
        .first()
        .split(" ")
        .map(String::toLong)
        .toMutableList()

    fun Long.digitCount(): Int {
        if (this == 0L) return 1

        var count = 0
        var currentNumber = this
        while (currentNumber > 0) {
            currentNumber /= 10
            count++
        }
        return count
    }

    fun tenToPowerOf(digitCount: Int): Int {
        var tens = 10
        repeat((digitCount / 2) - 1) {
            tens *= 10
        }
        return tens
    }

    fun MutableList<Long>.blink(times: Int): MutableList<Long> {
        val cacheDigitCounts = mutableMapOf<Long, Int>()
        val cachePowers = mutableMapOf<Int, Int>()

        val start = System.currentTimeMillis()
        repeat(times) { count ->
            println("$count, ${System.currentTimeMillis() - start} ms; size: ${this.size}; cached digitCounts: ${cacheDigitCounts.size}, powers: ${cachePowers.size}")

            val iterator = listIterator()
            for (stone in iterator) {
                if (stone == 0L) {
                    iterator.set(1)
                } else {
                    val digitCount = cacheDigitCounts.getOrPut(stone) { stone.digitCount() }
                    if (digitCount % 2 == 0) {
                        val tens = cachePowers.getOrPut(digitCount) { tenToPowerOf(digitCount) }
                        iterator.set(stone.div(tens))
                        iterator.add(stone % tens)
                    } else {
                        iterator.set(stone * 2024)
                    }
                }
            }
        }
        return this
    }

    fun part1(input: List<String>) = parseStones(input)
        .blink(25)
        .count()

    printAndCheck(
        """
            125 17
        """.trimIndent().lines(),
        ::part1, 55312
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 193899)


    println("$day part 2")

    fun part2(input: List<String>) = parseStones(input)
        .blink(75)
        .count()

    printAndCheck(input, ::part2, 1960)
}
