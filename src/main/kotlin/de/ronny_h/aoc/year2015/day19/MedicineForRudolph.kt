package de.ronny_h.aoc.year2015.day19

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.memoize
import de.ronny_h.aoc.extensions.split

fun main() = MedicineForRudolph().run(518, 200)

class MedicineForRudolph : AdventOfCode<Int>(2015, 19) {
    override fun part1(input: List<String>): Int {
        val (replacementStrings, moleculeString) = input.split()
        val replacements = replacementStrings.parseReplacements()
        val startMolecule = moleculeString.single()

        val producedMolecules = mutableSetOf<String>()
        replacements.forEach { replacement ->
            replacement.from
                .toRegex()
                .findAll(startMolecule)
                .forEach {
                    producedMolecules.add(
                        startMolecule.substring(
                            0,
                            it.range.start
                        ) + replacement.to + startMolecule.substring(it.range.endInclusive + 1)
                    )
                }
        }

        return producedMolecules.size
    }

    override fun part2(input: List<String>): Int {
        val (replacementStrings, moleculeString) = input.split()
        return produceMolecule(replacementStrings.parseReplacements(), moleculeString.single())
    }

    // For my input, a solution is printed very quickly. But then it takes an unacceptable amount of time to verify
    // that this solution is the minimal one.
    private fun produceMolecule(
        replacements: List<Replacement>,
        molecule: String,
    ): Int {
        data class Arguments(val molecule: String, val steps: Int)

        lateinit var produceMolecule: (Arguments) -> Int?

        produceMolecule = { arg: Arguments ->
            if (arg.molecule == "e") {
                println(arg.steps)
                arg.steps
            } else {
                replacements.mapNotNull { replacement ->
                    replacement.to
                        .toRegex()
                        .findAll(arg.molecule)
                        .mapNotNull {
                            produceMolecule(
                                Arguments(
                                    arg.molecule.substring(
                                        0,
                                        it.range.start
                                    ) + replacement.from + arg.molecule.substring(it.range.endInclusive + 1),
                                    arg.steps + 1
                                )
                            )
                        }.minOrNull()

                }.minOrNull()
            }
        }.memoize()

        return produceMolecule(Arguments(molecule, 0))!!
    }
}

data class Replacement(val from: String, val to: String)

fun List<String>.parseReplacements() = map {
    val (from, to) = it.split(" => ")
    Replacement(from, to)
}
