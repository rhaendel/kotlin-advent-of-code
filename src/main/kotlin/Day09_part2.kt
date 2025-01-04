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
    1212122
    0..1..2..33
    0331..2....
    03312......
     */
    printAndCheck(
        """
            1212122
        """.trimIndent().lines(),
        ::part2, 20
    )

    /*
    00000000.....111.22222.........33333333.....4444..55555666..7777777788888.......9
    ...
    000000009666.111.222228888844443333333355555................77777777.............
    TODO: without 'wasMoved' the 9 is moved right in the last step - why? Moves should always be to the left. Check indices!
     */
    printAndCheck(
        """
            8531598542503280571
        """.trimIndent().lines(),
        ::part2, 20
    )

    val input = readInput(day)
//    printAndCheck(input, ::part2, 809)
    // too high:  6427590702452
    // not right: 6427441424273 (mark changed blocks as 'wasMoved')
    // too low:   6410551341413 (checksum that skips free space in index counting)
}

private data class Block(var id: Int, var length: Int, var isFree: Boolean, var wasMoved: Boolean = false) {

    val isNotFree: Boolean
        get() = !isFree

    val wasNotMoved: Boolean
        get() = !wasMoved

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
        check(wasNotMoved)
        isFree = false
        wasMoved = true
        this.id = id
    }

    override fun toString() = if (isFree) {
        ".".repeat(length)
    } else {
        "$id".repeat(length)
    }

    fun splitAt(length: Int): Block {
        check(this.length > length)
        wasMoved = true
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
        val sizeFree = blocks.sizeFree()
        val sizeOccupied = blocks.sizeOccupied()

        val newBlocks = blocks.toMutableList()
        var backwardsIndex = newBlocks.lastIndex
        blocks.reversed().forEach { block ->
            if (block.isNotFree && block.wasNotMoved) {
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
                        printBlocks(newBlocks)
                        break
                    }
                    forwardsIndex++
                }
                check(newBlocks.sizeFree() == sizeFree)
                check(newBlocks.sizeOccupied() == sizeOccupied)
            }
            backwardsIndex--
        }
        // printBlocks(newBlocks)
        return newBlocks
    }
}

private fun List<Block>.sizeFree() = this.sumOf { if (it.isFree) it.length else 0 }
private fun List<Block>.sizeOccupied() = this.sumOf { if (it.isNotFree) it.length else 0 }
