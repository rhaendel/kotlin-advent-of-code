package de.ronny_h.aoc.year2015.day24

import de.ronny_h.aoc.AdventOfCode

fun main() = ItHangsInTheBalance().run(11266889531, 77387711)

class ItHangsInTheBalance : AdventOfCode<Long>(2015, 24) {
    override fun part1(input: List<String>): Long = minGroup1QuantumEntanglement(input, 3)
    override fun part2(input: List<String>): Long = minGroup1QuantumEntanglement(input, 4)

    private fun minGroup1QuantumEntanglement(input: List<String>, numberOfGroups: Int): Long {
        val groups = input.parseWeights()
            .takeMinSizedGroupOneOfGroupsOfSameWeight(numberOfGroups)
        val minSize = groups.first().size
        val minQuantumEntanglement = groups.minOf { it.quantumEntanglement() }
        println("found ${groups.size} groups of minimal size $minSize with minimum quantum entaglement $minQuantumEntanglement")
        return minQuantumEntanglement
    }
}

fun List<String>.parseWeights() = map<String, Int>(String::toInt)

fun List<Int>.takeMinSizedGroupOneOfGroupsOfSameWeight(numberOfGroups: Int): Set<List<Int>> {
    val totalWeight = sum()
    require(totalWeight % numberOfGroups == 0) { "the total weight must be a multiple of $numberOfGroups" }
    require(numberOfGroups in 3..4) { "the number of groups can only be 3 or 4" }
    val weight = totalWeight / numberOfGroups

    /**
     * Recursive function that distributes items from [group3], wich initially contains all items, to the other groups
     * so that all groups' weight (= the sum of their values) is equal and [group1]'s size is minimal.
     * It works for 3 and 4 groups.
     *
     * @param minSize The currently known minimal group size (= number of items in that group).
     * @param group1 The current group 1.
     * @param maxIndex1 The largest index in [group3] up to which to move elements to [group1] (larger indices already
     * have been tried or do not exist).
     * @param group2 The current group 2.
     * @param maxIndex2 The largest index in [group3] up to which to move elements to [group2].
     * @param group3 The current group 3. Initially contains all items. Is sorted in ascending order. In each recursive
     * call, an item from its back is moved to a different group.
     * @param group4 The current group 4. Stays empty if [numberOfGroups] is 3.
     * @param maxIndex4 The largest index in [group3] up to which to move elements to [group4].
     *
     * @return The set of possible [group1]s with minimal size.
     */
    fun groupingWithMinimalGroupOne(
        minSize: Int,
        group1: List<Int>,
        maxIndex1: Int,
        group2: List<Int>,
        maxIndex2: Int,
        group3: List<Int>,
        group4: List<Int>,
        maxIndex4: Int,
    ): Set<List<Int>> {
        // move items from group3 to group1 until group1 has the right weight
        if (group1.sum() < weight) {
            if (group1.size + 1 > minSize) {
                return emptySet()
            }
            var localMinSize = minSize
            return buildSet {
                for (i in maxIndex1 downTo 0) {
                    groupingWithMinimalGroupOne(
                        localMinSize,
                        group1 + group3[i],
                        i - 1,
                        group2,
                        maxIndex2 - 1,
                        group3 - group3[i],
                        group4,
                        maxIndex4 - 1,
                    )
                        .forEach {
                            if (it.size <= localMinSize) {
                                add(it)
                                localMinSize = it.size
                            }
                        }
                }
            }.filter { it.size == localMinSize }.toSet()
        }
        if (group1.sum() == weight) {
            // move items from group3 to group2 until group2 has the right weight
            if (group2.sum() < weight) {
                for (i in maxIndex2 downTo 0) {
                    if (groupingWithMinimalGroupOne(
                            minSize,
                            group1,
                            maxIndex1 - 1,
                            group2 + group3[i],
                            i - 1,
                            group3 - group3[i],
                            group4,
                            maxIndex4 - 1,
                        ).isNotEmpty()
                    ) {
                        // if one grouping of equal weight with group2 and group3 is found, we know group1 might be a solution
                        return setOf(group1)
                    }
                }
            }
            if (group2.sum() == weight) {
                if (numberOfGroups == 3) {
                    return setOf(group1)
                }
                // numberOfGroups == 4 -> continue by moving elements from group3 to group4
                if (group4.sum() < weight) {
                    for (i in maxIndex4 downTo 0) {
                        if (groupingWithMinimalGroupOne(
                                minSize,
                                group1,
                                maxIndex1 - 1,
                                group2,
                                maxIndex2 - 1,
                                group3 - group3[i],
                                group4 + group3[i],
                                i - 1,
                            ).isNotEmpty()
                        ) {
                            // if one grouping of equal size with group3 and group4 is found, we know group1 might be a solution
                            return setOf(group1)
                        }
                    }
                }
                if (group4.sum() == weight) {
                    return setOf(group1)
                }
            }
        }
        return emptySet()
    }

    return groupingWithMinimalGroupOne(
        Int.MAX_VALUE,
        emptyList(),
        lastIndex,
        emptyList(),
        lastIndex,
        sorted(),
        emptyList(),
        lastIndex,
    )
}

fun List<Int>.quantumEntanglement() = fold(1L) { acc, item -> acc * item }
