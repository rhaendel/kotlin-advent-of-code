package de.ronny_h.aoc.year2017.day07

import de.ronny_h.aoc.AdventOfCode

fun main() = RecursiveCircus().run("vgzejbd", "1226")

class RecursiveCircus : AdventOfCode<String>(2017, 7) {
    override fun part1(input: List<String>): String = input.parsePrograms().findRootProgram()

    private fun List<Program>.findRootProgram(): String {
        val balancingPrograms = filter { it.balancedPrograms.isNotEmpty() }
        val balancedProgramNames = balancingPrograms.flatMap(Program::balancedPrograms)
        return balancingPrograms
            .map(Program::name)
            .filterNot { it in balancedProgramNames }
            .single()
    }

    override fun part2(input: List<String>): String {
        val programs = input.parsePrograms()
        val root = programs.findRootProgram()
        val programsByName = programs.associateBy(Program::name)

        data class ProgramWeight(val weight: Int, val fixedWrongWeight: Int = 0)

        fun weightOf(programName: String): ProgramWeight {
            val program = programsByName.getValue(programName)
            if (program.balancedPrograms.isEmpty()) {
                return ProgramWeight(program.weight)
            }
            val subTowerWeights = program.balancedPrograms.map { weightOf(it) }
            subTowerWeights.find { it.fixedWrongWeight != 0 }?.let { fixed ->
                // we already found the one to fix -> propagate it up the recursive calls
                return ProgramWeight(program.weight + subTowerWeights.sumOf { it.weight }, fixed.fixedWrongWeight)
            }

            val firstSubTowerWeight = subTowerWeights.first().weight
            val sameWeightCount = subTowerWeights.count { it.weight == firstSubTowerWeight }

            val fixedWrongWeight = when (sameWeightCount) {
                subTowerWeights.size -> {
                    // nothing to rebalance
                    0
                }

                1 -> {
                    // the single program with wrong weight is the first sub-tower's root
                    val firstSubprogramWeight = programsByName.getValue(program.balancedPrograms.first()).weight
                    firstSubprogramWeight + subTowerWeights[1].weight - firstSubTowerWeight
                }

                else -> {
                    // the single program with wrong weight is one of the others
                    val differentWeight = subTowerWeights.single { it.weight != firstSubTowerWeight }
                    val differentWeightIndex = subTowerWeights.indexOfFirst { it.weight != firstSubTowerWeight }
                    val differentSubprogramWeight =
                        programsByName.getValue(program.balancedPrograms[differentWeightIndex]).weight

                    differentSubprogramWeight + firstSubTowerWeight - differentWeight.weight
                }
            }
            return ProgramWeight(program.weight + subTowerWeights.sumOf { it.weight }, fixedWrongWeight)
        }

        return weightOf(root).fixedWrongWeight.toString()
    }
}

data class Program(val name: String, val weight: Int, val balancedPrograms: List<String> = emptyList())

fun List<String>.parsePrograms(): List<Program> = map {
    val row = it.split(" -> ")
    val (name, weightInBraces) = row.first().split(" ")
    val weight = weightInBraces.substring(1..<weightInBraces.lastIndex).toInt()
    if (row.size == 1) {
        Program(name, weight)
    } else {
        Program(name, weight, row[1].split(", "))
    }
}
