package de.ronny_h.aoc.year2017.day13

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import io.kotest.matchers.shouldBe

val PacketScannersTest by testSuite {

    val input = """
        0: 3
        1: 2
        4: 4
        6: 4
    """.asList()

    test("input can be parsed") {
        input.parseFirewallConfig() shouldBe listOf(
            ScanningArea(0, 3),
            ScanningArea(1, 2),
            ScanningArea(4, 4),
            ScanningArea(6, 4),
        )
    }

    data class Row(val layer: ScanningArea, val ps: Int, val expected: Boolean)

    testSuite("scanner is on top of the layer in the specific picosecond") {
        listOf(
            Row(ScanningArea(0, 3), 0, true),
            Row(ScanningArea(1, 2), 0, true),
            Row(ScanningArea(4, 4), 0, true),
            Row(ScanningArea(6, 4), 0, true),
            Row(ScanningArea(0, 3), 1, false),
            Row(ScanningArea(1, 2), 1, false),
            Row(ScanningArea(4, 4), 1, false),
            Row(ScanningArea(6, 4), 1, false),
            Row(ScanningArea(0, 3), 2, false),
            Row(ScanningArea(1, 2), 2, true),
            Row(ScanningArea(4, 4), 2, false),
            Row(ScanningArea(6, 4), 2, false),
            Row(ScanningArea(0, 3), 3, false),
            Row(ScanningArea(1, 2), 3, false),
            Row(ScanningArea(4, 4), 3, false),
            Row(ScanningArea(6, 4), 3, false),
            Row(ScanningArea(0, 3), 4, true),
            Row(ScanningArea(1, 2), 4, true),
            Row(ScanningArea(4, 4), 4, false),
            Row(ScanningArea(6, 4), 4, false),
        ).forEach { (layer, ps, expected) ->
            test("$layer, $ps, $expected") {
                layer.isScannerOnTopInPicosecond(ps) shouldBe expected
            }
        }
    }

    test("part 1: the severity of the example trip") {
        PacketScanners().part1(input) shouldBe 24
    }

    test("part 2: the fewest number of picoseconds to delay to get through safely") {
        PacketScanners().part2(input) shouldBe 10
    }
}
