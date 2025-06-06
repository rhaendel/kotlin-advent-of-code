package de.ronny_h.aoc.year24.day09

class DiskMap(input: List<String>) {
    val idOfFreeBlock = -1

    val diskMap = input.first().map(Char::digitToInt)

    // even (starting at 0): file content; consecutively numbered IDs
    val files = diskMap.filterIndexed { i, _ -> i % 2 == 0 }
    // odd: (starting at 1): free space
    val free = diskMap.filterIndexed { i, _ -> i % 2 == 1 }
    val filesCount = files.sumOf { it }
    val freeCount = free.sumOf { it }
    val blockCount = filesCount + freeCount

    fun printInfo() {
        println("- ${files.size} files: $files")
        println("- ${free.size} free blocks: $free")
        println("- filesCount: $filesCount")
        println("- freeCount : $freeCount")
        println("- blockCount: $blockCount")
    }

    fun compact(): IntArray {
        val blocks = IntArray(blockCount)

        var index = 0
        var indexBackwards = blocks.lastIndex
        var idToMove = files.lastIndex
        var stillToMove = files[idToMove]

        // if diskMap ends with free blocks (an even number), set them free in the result up front
        if (files.size == free.size) {
            repeat(free.last()) {
                blocks[indexBackwards] = idOfFreeBlock
                indexBackwards--
            }
        }

        for (id in files.indices) {
            // forwards: fill the file blocks that remain on their place
            repeat(files[id]) {
                blocks[index] = if (index >= filesCount) {
                    idOfFreeBlock // we're done. there's nothing left to move
                } else {
                    id
                }
                index++
            }
            // backwards: move the file blocks to the free slots (that still are traversed forwards)
            if (id < free.size) {
                repeat(free[id]) {
                    blocks[index] = if (index >= filesCount) {
                        idOfFreeBlock // we're done. there's nothing left to move
                    } else {
                        idToMove
                    }
                    stillToMove--
                    if (stillToMove == 0) {
                        idToMove--
                        if (idToMove >= 0) {
                            stillToMove = files[idToMove]
                        }
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
