package de.ronny_h.aoc.year2018.day04

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.year2018.day04.GuardEvent.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ReposeRecordTest : StringSpec({

    val input = """
        [1518-11-01 00:00] Guard #10 begins shift
        [1518-11-01 00:05] falls asleep
        [1518-11-01 00:25] wakes up
        [1518-11-01 00:30] falls asleep
        [1518-11-01 00:55] wakes up
        [1518-11-01 23:58] Guard #99 begins shift
        [1518-11-02 00:40] falls asleep
        [1518-11-02 00:50] wakes up
        [1518-11-03 00:05] Guard #10 begins shift
        [1518-11-03 00:24] falls asleep
        [1518-11-03 00:29] wakes up
        [1518-11-04 00:02] Guard #99 begins shift
        [1518-11-04 00:36] falls asleep
        [1518-11-04 00:46] wakes up
        [1518-11-05 00:03] Guard #99 begins shift
        [1518-11-05 00:45] falls asleep
        [1518-11-05 00:55] wakes up
    """.asList()

    "input can be parsed" {
        input.parseRecords() shouldBe listOf(
            GuardDutyRecord("1518-11-01 00:00", 10, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-01 00:05", 10, FALL_ASLEEP),
            GuardDutyRecord("1518-11-01 00:25", 10, WAKE_UP),
            GuardDutyRecord("1518-11-01 00:30", 10, FALL_ASLEEP),
            GuardDutyRecord("1518-11-01 00:55", 10, WAKE_UP),
            GuardDutyRecord("1518-11-01 23:58", 99, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-02 00:40", 99, FALL_ASLEEP),
            GuardDutyRecord("1518-11-02 00:50", 99, WAKE_UP),
            GuardDutyRecord("1518-11-03 00:05", 10, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-03 00:24", 10, FALL_ASLEEP),
            GuardDutyRecord("1518-11-03 00:29", 10, WAKE_UP),
            GuardDutyRecord("1518-11-04 00:02", 99, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-04 00:36", 99, FALL_ASLEEP),
            GuardDutyRecord("1518-11-04 00:46", 99, WAKE_UP),
            GuardDutyRecord("1518-11-05 00:03", 99, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-05 00:45", 99, FALL_ASLEEP),
            GuardDutyRecord("1518-11-05 00:55", 99, WAKE_UP),
        )
    }

    "input gets sorted by timestamp" {
        """
            [1518-11-01 00:25] wakes up
            [1518-11-01 00:05] falls asleep
            [1518-11-01 00:00] Guard #10 begins shift
            [1518-11-01 00:30] falls asleep
        """.asList()
            .parseRecords() shouldBe listOf(
            GuardDutyRecord("1518-11-01 00:00", 10, BEGIN_SHIFT),
            GuardDutyRecord("1518-11-01 00:05", 10, FALL_ASLEEP),
            GuardDutyRecord("1518-11-01 00:25", 10, WAKE_UP),
            GuardDutyRecord("1518-11-01 00:30", 10, FALL_ASLEEP),
        )
    }

    "part 1: the guard's id who slept most, multiplied by the minute he slept most" {
        ReposeRecord().part1(input) shouldBe 240
    }

    "part 2: the guard's id who sleeps most frequently in the same minute, multiplied by that minute" {
        ReposeRecord().part2(input) shouldBe 4455
    }
})
