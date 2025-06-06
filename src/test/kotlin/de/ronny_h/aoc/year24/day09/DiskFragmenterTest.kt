package de.ronny_h.aoc.year24.day09

import de.ronny_h.aoc.extensions.asList
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class DiskFragmenterTest : StringSpec({
    val verySmallInput = """
        1234
    """.asList()

    // disk map 12345 represents individual
    // blocks   : 0..111....22222
    // compacted: 022111222......
    val smallInput1 = """
        12345
    """.asList()

    // the following represents
    // blocks   : 00...111...2...333.44.5555.6666.777.888899
    // compacted: 0099811188827773336446555566..............
    val mediumInput1 = """
        2333133121414131402
    """.asList()

    // 1212122
    // 0..1..2..33
    // 0331..2....
    // 03312......
    val smallInput2 = """
        1212122
    """.asList()

    // 00000000.....111.22222.........33333333.....4444..55555666..7777777788888.......9
    // ...
    // 000000009666.111.222228888844443333333355555................77777777.............
    val mediumInput2 = """
        8531598542503280571
    """.asList()

    "part 1: Compacted hard drive's filesystem checksum" {
        forall(
            row(verySmallInput, 6),
            row(smallInput1, 60),
            row(mediumInput1, 1928),
        ) { input, result ->
            DiskFragmenter().part1(input) shouldBe result
        }
    }

    "part 2: Compacted hard drive's filesystem checksum" {
        forall(
            row(smallInput2, 20),
            row(mediumInput1, 2858),
            row(mediumInput2, 7309),
        ) { input, result ->
            DiskFragmenter().part2(input) shouldBe result
        }
    }
})
