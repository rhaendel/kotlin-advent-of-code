import Block.Companion.freeBlockWithLength

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

    // disk map 1234 represents individual
    // blocks   : 0..111....
    // compacted: 0111......
    printAndCheck(
        """
            1234
        """.trimIndent().lines(),
        ::part1, 6
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 6398608069280)


    println("$day part 2")

    fun part2(input: List<String>): Long {
        val diskMap = DefragDiskMap(input)
        // diskMap.printBlocks()
        val blocks = diskMap.defrag()
        return diskMap.calculateChecksum(blocks)
    }

    printAndCheck(
        """
            2333133121414131402
        """.trimIndent().lines(),
        ::part2, 2858
    )

    /*
    2322
    00...11..
    0011.....
     */
    printAndCheck(
        """
            2322
        """.trimIndent().lines(),
        ::part2, 5
    )

    printAndCheck(input, ::part2, 809)
    // too high: 6427590702452
}

private class DiskMap(input: List<String>) {
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

private data class Block(var id: Int, var length: Int, var isFree: Boolean) {

    val isNotFree: Boolean
        get() = !isFree

    companion object {
        private const val FREE_BLOCK_ID = -1

        fun freeBlockWithLength(length: Int) = Block(FREE_BLOCK_ID, length, true)
    }

    fun setFree() {
        id = FREE_BLOCK_ID
        isFree = true
    }

    fun occupyWith(id: Int) {
        check(isFree)
        isFree = false
        this.id = id
    }

    override fun toString() = if (isFree) {
        ".".repeat(length)
    } else {
        "$id".repeat(length)
    }

    fun splitAt(length: Int): Block {
        check(this.length > length)
        val newBlock = freeBlockWithLength(this.length - length)
        this.length = length
        return newBlock
    }

}

private class DefragDiskMap(input: List<String>) {
    val blocks = input
        .first()
        .map(Char::digitToInt)
        .mapIndexed { i, length ->
            if (i % 2 == 0) {
                Block(i / 2, length, false)
            } else {
                freeBlockWithLength(length)
            }
        }

    fun printBlocks(toPrint: List<Block> = blocks) {
        toPrint.forEach(::print)
        println()
    }

    fun calculateChecksum(blocks: List<Block>): Long {
        var checksum: Long = 0
        var position = 0
        blocks.forEach { block ->
            if (block.isNotFree) {
                repeat(block.length) {
                    checksum += position * block.id
                    position++
                }
            } else {
                position += block.length
            }
        }
        return checksum
    }

    fun defrag(): List<Block> {
        val newBlocks = blocks.toMutableList()
        var backwardsIndex = newBlocks.lastIndex
        newBlocks.reversed().forEach { block ->
            if (block.isNotFree) {
                // find a free block with enough space
                val newBlocksIterator = newBlocks.listIterator()
                var forwardsIndex = 0
                for (newBlock in newBlocksIterator) {
                    if (forwardsIndex >= backwardsIndex) {
                        break
                    }
                    if (newBlock.isFree && newBlock.length >= block.length) {
                        // move the data over
                        newBlock.occupyWith(block.id)
                        block.setFree()
                        if (newBlock.length > block.length) {
                            // split the target block
                            val blockToInsert = newBlock.splitAt(block.length)
                            newBlocksIterator.add(blockToInsert)
                            backwardsIndex++
                        }
                        // printBlocks(newBlocks)
                        break
                    }
                    forwardsIndex++
                }
            }
            backwardsIndex--
        }
        return newBlocks
    }
}
