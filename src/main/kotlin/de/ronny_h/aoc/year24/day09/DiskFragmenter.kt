package de.ronny_h.aoc.year24.day09

import printAndCheck
import readInput

fun main() {
    val day = "Day09"
    val input = readInput(day)
    val diskFragmenter = DiskFragmenter()

    println("$day part 1")
    printAndCheck(input, diskFragmenter::part1, 6398608069280)

    println("$day part 2")
    printAndCheck(input, diskFragmenter::part2, 6427437134372)
}

class DiskFragmenter {
    fun part1(input: List<String>): Long {
        val diskMap = DiskMap(input)
        diskMap.printInfo()
        val blocks = diskMap.compact()
        // println("- compacted blocks: ${blocks.joinToString(" ")}")
        return diskMap.calculateChecksum(blocks)
    }

    fun part2(input: List<String>): Long {
        val diskMap = DefragDiskMap(input)
        // diskMap.printBlocks()
        val blocks = diskMap.defrag()
        return diskMap.calculateChecksum(blocks)
    }
}
