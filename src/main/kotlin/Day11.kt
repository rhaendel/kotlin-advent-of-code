import de.ronny_h.aoc.extensions.memoize

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
    val cachePowers = IntArray(20) { tenToPowerOf(it) }

    fun blink(stone: Long): List<Long> {
        if (stone == 0L) {
            return listOf(1)
        }
        val digitCount = cacheDigitCounts.getOrPut(stone) { stone.digitCount() }
        if (digitCount % 2 == 0) {
            val tens = cachePowers[digitCount]
            return listOf(stone.div(tens), stone % tens)
        }
        return listOf(stone * 2024)
    }

    data class State(val height: Int, val stones: List<Long>)

    lateinit var blinkRec: (State) -> Long

    blinkRec = { state: State ->
        if (state.height == 0) {
            state.stones.size.toLong()
        } else if (state.stones.size > 1) {
            blinkRec(State(state.height, state.stones.subList(0, state.stones.size/2))) + blinkRec(State(state.height, state.stones.subList(state.stones.size/2, state.stones.size)))
        } else {
            blinkRec(State(state.height - 1, blink(state.stones.single())))
        }
    }.memoize()

    fun List<Long>.blinkRec(times: Int): Long {
        return blinkRec(State(times, this))
    }

    fun part1(input: List<String>) = parseStones(input)
        .blinkRec(25)

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
        .blinkRec(75)

    printAndCheck(input, ::part2, 229682160383225)
}
