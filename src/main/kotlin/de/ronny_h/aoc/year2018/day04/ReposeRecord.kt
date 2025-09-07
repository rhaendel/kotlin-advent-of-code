package de.ronny_h.aoc.year2018.day04

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2018.day04.GuardEvent.*

fun main() = ReposeRecord().run(140932, 51232)

class ReposeRecord : AdventOfCode<Int>(2018, 4) {
    override fun part1(input: List<String>): Int {
        val guardDutyRecords = input.parseRecords()

        val guardWithMaxSleep = guardDutyRecords.findGuardWithMostSleep()
        println("the guard with max sleep minutes: $guardWithMaxSleep")

        val maxSleepMinute = guardDutyRecords
            .filter { it.guardId == guardWithMaxSleep.guardId }
            .findMinuteAsleepMostFrequently()
        println("the guard was asleep most during minute: $maxSleepMinute}")
        return guardWithMaxSleep.guardId * maxSleepMinute.minute
    }

    override fun part2(input: List<String>): Int {
        input.parseRecords()
            .groupBy { it.guardId }
            .filter {
                // there are actually guards that don't fall asleep at all
                it.value.any { record -> record.event == FALL_ASLEEP }
            }
            .map { it.key to it.value.findMinuteAsleepMostFrequently() }
            .maxBy { it.second.sleepCount }
            .let { return it.first * it.second.minute }
    }

    private fun List<GuardDutyRecord>.findGuardWithMostSleep(): GuardSleepMinutes {
        val sleepMinutes = mutableMapOf<Int, Int>().withDefault { 0 }
        var guardId: Int = -1
        var fallAsleepAt: Int = -1
        forEach {
            when (it.event) {
                BEGIN_SHIFT -> guardId = it.guardId
                FALL_ASLEEP -> fallAsleepAt = it.minute()
                WAKE_UP -> sleepMinutes[guardId] = sleepMinutes.getValue(guardId) + it.minute() - fallAsleepAt
            }
        }
        return sleepMinutes
            .maxBy { it.value }
            .let { GuardSleepMinutes(it.key, it.value) }
    }

    private fun List<GuardDutyRecord>.findMinuteAsleepMostFrequently(): SleepPerMinute {
        val asleepPerMinute = mutableMapOf<Int, Int>().withDefault { 0 }
        var fallAsleepAt: Int = -1
        forEach {
            when (it.event) {
                FALL_ASLEEP -> fallAsleepAt = it.minute()
                WAKE_UP -> {
                    for (minute in fallAsleepAt..<it.minute()) {
                        asleepPerMinute[minute] = asleepPerMinute.getValue(minute) + 1
                    }
                }

                else -> {} // noting to do
            }
        }

        return asleepPerMinute.maxBy { it.value }.let { SleepPerMinute(it.key, it.value) }
    }
}

// records have the form:
// [1518-11-01 00:00] Guard #10 begins shift
// [1518-11-01 00:05] falls asleep
// [1518-11-01 00:25] wakes up
fun List<String>.parseRecords(): List<GuardDutyRecord> {
    var guardId = -1

    return map {
        val (time, event) = it.substringAfter("[").split("] ")
        time to event
    }
        .sortedBy { it.first }
        .map {
            when (it.second) {
                "falls asleep" -> GuardDutyRecord(it.first, guardId, FALL_ASLEEP)
                "wakes up" -> GuardDutyRecord(it.first, guardId, WAKE_UP)
                else -> {
                    guardId = it.second.substringAfter("Guard #").substringBefore(" begins shift").toInt()
                    GuardDutyRecord(it.first, guardId, BEGIN_SHIFT)
                }
            }
        }
}

data class GuardDutyRecord(private val time: String, val guardId: Int, val event: GuardEvent) {
    fun minute(): Int = time.split(":")[1].toInt()
}

enum class GuardEvent { BEGIN_SHIFT, FALL_ASLEEP, WAKE_UP }

data class GuardSleepMinutes(val guardId: Int, val minutes: Int)

data class SleepPerMinute(val minute: Int, val sleepCount: Int)
