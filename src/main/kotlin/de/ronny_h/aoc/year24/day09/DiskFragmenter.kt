package de.ronny_h.aoc.year24.day09

import de.ronny_h.aoc.AdventOfCode

fun main() = DiskFragmenter().run(6398608069280, 6427437134372)

class DiskFragmenter : AdventOfCode<Long>(2024, 9) {
    override fun part1(input: List<String>): Long {
        val diskMap = DiskMap(input)
        diskMap.printInfo()
        val blocks = diskMap.compact()
        // println("- compacted blocks: ${blocks.joinToString(" ")}")
        return diskMap.calculateChecksum(blocks)
    }

    override fun part2(input: List<String>): Long {
        val diskMap = DefragDiskMap(input)
        // diskMap.printBlocks()
        val blocks = diskMap.defrag()
        return diskMap.calculateChecksum(blocks)
    }
}
