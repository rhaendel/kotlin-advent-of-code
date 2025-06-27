package de.ronny_h.aoc.year2024.day09

import de.ronny_h.aoc.year2024.day09.Block.Companion.freeBlockWithLength
import kotlin.collections.iterator

data class Block(var id: Int, var length: Int, var isFree: Boolean, var wasMoved: Boolean = false) {

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

class DefragDiskMap(input: List<String>) {
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
//        val sizeFree = blocks.sizeFree()
//        val sizeOccupied = blocks.sizeOccupied()

        val newBlocks = blocks.toMutableList()
        blocks.reversed().forEach { block ->
            if (block.isNotFree && block.wasNotMoved) {
                // find a free block with enough space
                val newBlocksIterator = newBlocks.listIterator()
                for (newBlock in newBlocksIterator) {
                    if (block.id == newBlock.id) {
                        // forward and backward iteration meet each other
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
                        }
                        // printBlocks(newBlocks)
                        break
                    }
                }
//                check(newBlocks.sizeFree() == sizeFree)
//                check(newBlocks.sizeOccupied() == sizeOccupied)
            }
        }
        // printBlocks(newBlocks)
        return newBlocks
    }
}

private fun List<Block>.sizeFree() = this.sumOf { if (it.isFree) it.length else 0 }
private fun List<Block>.sizeOccupied() = this.sumOf { if (it.isNotFree) it.length else 0 }
