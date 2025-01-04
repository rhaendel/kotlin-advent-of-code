fun main() {
    val day = "Day09"

    println("$day part 1")

    fun part1(input: List<String>): Long {
        val diskMap = DiskMap(input)
        diskMap.printInfo()
        val blocks = diskMap.compact()
        println("- compacted blocks: ${blocks.joinToString(" ")}")
        return diskMap.calculateChecksum(blocks)
    }

    // disk map 12345 represents individual
    // blocks   : 0..111....22222
    // compacted: 022111222......
    printAndCheck(
        """
            12345
        """.trimIndent().lines(),
        ::part1, 60
    )

    // the following represents
    // blocks   : 00...111...2...333.44.5555.6666.777.888899
    // compacted: 0099811188827773336446555566..............
    printAndCheck(
        """
            2333133121414131402
        """.trimIndent().lines(),
        ::part1, 1928
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 10819742216294)
    // too high: 10819742216294
    // too low :      719597670


//    println("$day part 2")
//
//    fun part2(input: List<String>): Long = input.size
//
//    printAndCheck(
//        """
//            12345
//        """.trimIndent().lines(),
//        ::part2, 0
//    )
//
//    printAndCheck(input, ::part2, 809)
}

private class DiskMap(input: List<String>) {
    val idOfFreeBlock = -1

    val diskMap = input.first().map(Char::digitToInt)

    // even (starting at 0): file content; consecutively numbered IDs
    val files = diskMap.filterIndexed { i, _ -> i % 2 == 0 }
    // odd: (starting at 1): free space
    val free = diskMap.filterIndexed { i, _ -> i % 2 == 1 }
    val blockCount = diskMap.sumOf { it }

    fun printInfo() {
        println("- ${files.size} files: $files")
        println("- ${free.size} free blocks: $free")
        println("- blockCount: $blockCount")
    }

    fun compact(): IntArray {
        val blocks = IntArray(blockCount)

        var index = 0
        var indexBackwards = blocks.lastIndex
        var idToMove = files.lastIndex
        var stillToMove = files[idToMove]

        for (id in files.indices) {
            // forwards: fill the file blocks that remain on their place
            repeat(files[id]) {
                blocks[index] = if (index >= indexBackwards - 1) {
                    idOfFreeBlock // we're done. there's nothing left to move
                } else {
                    id
                }
                index++
            }
            // backwards: move the file blocks to the free slots (that still are traversed forwards)
            if (id < free.size) {
                repeat(free[id]) {
                    blocks[index] = if (index >= indexBackwards - 1) {
                        idOfFreeBlock // we're done. there's nothing left to move
                    } else {
                        idToMove
                    }
                    stillToMove--
                    if (stillToMove == 0) {
                        idToMove--
                        stillToMove = files[idToMove]
                    }
                    index++
                    indexBackwards--
                }
            }
        }
        return blocks
    }

    fun calculateChecksum(blocks: IntArray): Long {
        var checksum: Long = 0
        blocks.forEachIndexed { position, id ->
            if (id == idOfFreeBlock) {
                return checksum
            }
            checksum += position * id
        }
        return checksum
    }
}
