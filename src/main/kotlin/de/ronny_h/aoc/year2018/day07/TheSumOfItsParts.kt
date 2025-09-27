package de.ronny_h.aoc.year2018.day07

import de.ronny_h.aoc.AdventOfCode

fun main() = TheSumOfItsParts().run("BKCJMSDVGHQRXFYZOAULPIEWTN", "1040")

class TheSumOfItsParts : AdventOfCode<String>(2018, 7) {
    override fun part1(input: List<String>): String = input
        .createDependencyGraph()
        .executeStepsInDependencyOrder()
        .joinToString("")

    override fun part2(input: List<String>): String = input
        .createDependencyGraph()
        .simulateSteps(5, 60)

    companion object {
        fun List<String>.createDependencyGraph(): MutableMap<String, MutableSet<String>> {
            // build a graph using a map: step -> list of steps that must be finished before
            val dependencyGraph = mutableMapOf<String, MutableSet<String>>()
            parseSteps().forEach {
                dependencyGraph.computeIfAbsent(it.second) { mutableSetOf() }.add(it.first)
            }
            return dependencyGraph
        }

        private fun Map<String, Set<String>>.executeStepsInDependencyOrder(): List<String> {
            // Find the first steps that must have no other dependencies.
            // ready steps = not done steps whose dependant steps are done
            var readySteps = values.flatten().filterNot { it in keys }.toSet()
            val stepsDone = mutableListOf<String>()
            while (readySteps.isNotEmpty()) {
                val step = readySteps.min()
                stepsDone.add(step)
                readySteps = (readySteps - step) + this
                    .filter { it.key !in stepsDone }
                    .filter { it.allDependenciesAreDone(stepsDone) }
                    .keys
            }
            return stepsDone
        }

        fun Map<String, Set<String>>.simulateSteps(workers: Int, baseDurationSeconds: Int): String {
            val stepsDone = mutableListOf<String>()
            val readySteps = values.flatten().filterNot { it in keys }.toMutableSet()
            var workersOccupied = 0
            var completionSeconds = -1
            var stepsInAction = listOf<StepInAction>()

            while (readySteps.isNotEmpty() || stepsInAction.isNotEmpty()) {
                completionSeconds++
                stepsInAction.forEach { it.remainingDuration-- }
                val (done, stillInAction) = stepsInAction.partition { it.remainingDuration == 0 }
                stepsDone.addAll(done.map(StepInAction::name))

                workersOccupied -= done.size

                readySteps += this.filter { it.key !in (stepsDone + stepsInAction.map(StepInAction::name)) }
                    .filter { it.allDependenciesAreDone(stepsDone) }
                    .keys
                val newSteps = readySteps.sorted().take(workers - workersOccupied).toSet()
                workersOccupied += newSteps.size
                stepsInAction = stillInAction + newSteps.map { StepInAction(it, it.duration(baseDurationSeconds)) }
                readySteps -= newSteps
            }
            return "$completionSeconds"
        }

        private data class StepInAction(val name: String, var remainingDuration: Int)

        private fun Map.Entry<String, Set<String>>.allDependenciesAreDone(stepsDone: List<String>): Boolean =
            value.all { it in stepsDone }

        // duration = baseDuration + letter's position in the alphabet
        private fun String.duration(baseDurationSeconds: Int) = first().code - 'A'.code + baseDurationSeconds + 1
    }
}

private val stepPattern = """Step ([A-Z]) must be finished before step ([A-Z]) can begin.""".toPattern()

fun List<String>.parseSteps() = map {
    val matcher = stepPattern.matcher(it)
    require(matcher.matches())
    matcher.group(1) to matcher.group(2)
}
