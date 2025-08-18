package de.ronny_h.aoc.year2017.day13

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class PacketScannersTest : StringSpec({

    val input = """
        0: 3
        1: 2
        4: 4
        6: 4
    """.asList()

    "input can be parsed" {
        input.parseFirewallConfig() shouldBe listOf(
            ScanningArea(0, 3),
            ScanningArea(1, 2),
            ScanningArea(4, 4),
            ScanningArea(6, 4),
        )
    }

    "scanner is on top of the layer in the specific picosecond" {
        forAll(
            row(ScanningArea(0, 3), 0, true),
            row(ScanningArea(1, 2), 0, true),
            row(ScanningArea(4, 4), 0, true),
            row(ScanningArea(6, 4), 0, true),
            row(ScanningArea(0, 3), 1, false),
            row(ScanningArea(1, 2), 1, false),
            row(ScanningArea(4, 4), 1, false),
            row(ScanningArea(6, 4), 1, false),
            row(ScanningArea(0, 3), 2, false),
            row(ScanningArea(1, 2), 2, true),
            row(ScanningArea(4, 4), 2, false),
            row(ScanningArea(6, 4), 2, false),
            row(ScanningArea(0, 3), 3, false),
            row(ScanningArea(1, 2), 3, false),
            row(ScanningArea(4, 4), 3, false),
            row(ScanningArea(6, 4), 3, false),
            row(ScanningArea(0, 3), 4, true),
            row(ScanningArea(1, 2), 4, true),
            row(ScanningArea(4, 4), 4, false),
            row(ScanningArea(6, 4), 4, false),
        ) { layer, ps, expected ->
            layer.isScannerOnTopInPicosecond(ps) shouldBe expected
        }
    }

    "part 1: the severity of the example trip" {
        PacketScanners().part1(input) shouldBe 24
    }

    "part 2: the fewest number of picoseconds to delay to get through safely" {
        PacketScanners().part2(input) shouldBe 10
    }
})
