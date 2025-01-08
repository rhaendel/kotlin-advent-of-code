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

    val cacheDigitCounts = mutableMapOf<Long, Int>()
    val cachePowers = mutableMapOf<Int, Int>()

    fun List<Long>.blink(times: Int): List<Long> {
        val start = System.currentTimeMillis()
        val mutableList = toMutableList()
        repeat(times) { count ->
            println("$count, ${System.currentTimeMillis() - start} ms; size: ${mutableList.size}; cached digitCounts: ${cacheDigitCounts.size}, powers: ${cachePowers.size}")

            val iterator = mutableList.listIterator()
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
        return mutableList
    }

    data class State(val height: Int, val stones: List<Long>)

    fun blinkRec(state: State): List<Long> {
        if (state.height == 0) {
            return state.stones
        }
        return blinkRec(State(state.height - 1, state.stones.blink(1)))
    }

    fun List<Long>.blinkRec(times: Int): List<Long> {
        return blinkRec(State(times, this))
    }

    fun part1(input: List<String>) = parseStones(input)
        .blinkRec(25)
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
