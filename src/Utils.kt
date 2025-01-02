import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.reflect.KSuspendFunction1
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun printAndCheck(input: List<String>, block: (List<String>) -> Int, expected: Int) {
    printAndCheck(measureTimedValue { block(input).toLong() }, expected.toLong())
}

fun printAndCheck(input: List<String>, block: (List<String>) -> Long, expected: Long) {
    printAndCheck(measureTimedValue { block(input) }, expected)
}

suspend fun printAndCheck(input: List<String>, block: KSuspendFunction1<List<String>, Int>, expected: Int) {
    printAndCheck(measureTimedValue { block(input).toLong() }, expected.toLong())
}

suspend fun printAndCheck(input: List<String>, block: KSuspendFunction1<List<String>, Long>, expected: Long) {
    printAndCheck(measureTimedValue { block(input) }, expected)
}

private fun printAndCheck(result: TimedValue<Long>, expected: Long) {
    println("result: ${result.value}, expected: $expected, took: ${result.duration}")
    check(result.value == expected)
}
