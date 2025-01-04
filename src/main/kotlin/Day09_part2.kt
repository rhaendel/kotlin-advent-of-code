import Block.Companion.freeBlockWithLength

fun main() {
    val day = "Day09"
    println("$day part 2")

    fun part2(input: List<String>): Long {
        val diskMap = DefragDiskMap(input)
        diskMap.printBlocks()
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

    val input = readInput(day)
    printAndCheck(input, ::part2, 809)
    // too high: 6427590702452
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
        printBlocks(newBlocks)
        return newBlocks
    }
}
