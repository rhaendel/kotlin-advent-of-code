package de.ronny_h.aoc.year2017.day21

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import de.ronny_h.aoc.extensions.grids.Grid

fun main() = FractalArt().run(150, 2606275)

class FractalArt : AdventOfCode<Int>(2017, 21) {
    override fun part1(input: List<String>): Int = applyTheRulesToTheGivenInitConfiguration(input, 5)
    override fun part2(input: List<String>): Int = applyTheRulesToTheGivenInitConfiguration(input, 18)

    fun applyTheRulesToTheGivenInitConfiguration(input: List<String>, iterations: Int): Int {
        val rules = input.parseEnhancementRules()
        val initConfiguration = """
                .#.
                ..#
                ###
            """.asList()
        var grid = FractalArtGrid(initConfiguration)
        repeat(iterations) {
            grid = grid.applyRules(rules)
        }
        return grid.countPixelsThatAreOn()
    }
}

data class Rule(val pattern: List<String>, val onCount: Int, val replacement: List<String>)
data class RuleSet(val even: List<Rule>, val odd: List<Rule>)

private const val on = '#'
private const val off = '.'

fun List<String>.parseEnhancementRules(): RuleSet {
    val rules = map { input ->
        val (p, r) = input.split(" => ")
        Rule(pattern = p.split("/"), onCount = p.count { it == on }, replacement = r.split("/"))
    }.partition { it.pattern.size % 2 == 0 }
    return RuleSet(even = rules.first, odd = rules.second)
}

class FractalArtGrid(size: Int) : Grid<Char>(size, size, off) {

    constructor(initConfiguration: List<String>) : this(initConfiguration.size) {
        check(initConfiguration.size == initConfiguration[0].length) { "FractalArtGrid has to be square" }
        initGrid(initConfiguration)
    }

    override fun Char.toElementType(): Char = this

    fun countPixelsThatAreOn(): Int = forEachElement { _, _, e -> e }.filter { it == on }.count()

    fun applyRules(rules: RuleSet): FractalArtGrid {
        val squareSize = if (height % 2 == 0) 2 else 3
        val numberOfSquares = height / squareSize
        val newGrid = FractalArtGrid(numberOfSquares * (squareSize + 1))
        val rulesToApply = if (height % 2 == 0) rules.even else rules.odd
        forEachSquareOfSize(squareSize) { start, square ->
            var applied = false
            for (rule in rulesToApply) {
                if (rule.onCount != square.countPixelsThatAreOn()) {
                    // filter out bad candidates early
                    continue
                }
                if (squareSize == 2 && rule.onCount != 2 || rule.ruleMatches(square) || rule.ruleMatches(square.reversed())) {
                    newGrid.applyRuleAt(
                        rule,
                        Coordinates(start.row + (start.row / squareSize), start.col + (start.col / squareSize))
                    )
                    applied = true
                    break
                }
            }
            if (!applied) {
                throw RuntimeException("No rule to apply for square $square at $start.")
            }
        }
        return newGrid
    }

    private fun Rule.ruleMatches(square: List<List<Char>>): Boolean =
        // exact match
        square.allMatch { row, col, value -> pattern[row][col] == value } ||

                // rotate 90° right
                //     0   1
                // 0 [ 0 , 1 ]  ->  [ 2 , 0 ]  =  [ 1,0 , 0,0 ]  =  [ size-col,row , size-col,row ]
                // 1 [ 2 , 3 ]      [ 3 , 1 ]     [ 1,1 , 0,1 ]     [ size-col,row , size-col,row ]
                square.allMatch { row, col, value -> pattern[square.lastIndex - col][row] == value } ||

                // rotate 180°
                //     0   1
                // 0 [ 0 , 1 ]  ->  [ 3 , 2 ]  =  [ 1,1 , 1,0 ]  =  [ size-row,size-col , size-row,size-col ]
                // 1 [ 2 , 3 ]      [ 1 , 0 ]     [ 0,1 , 0,0 ]     [ size-row,size-col , size-row,size-col ]
                square.allMatch { row, col, value -> pattern[square.lastIndex - row][square.lastIndex - col] == value } ||

                // rotate 90° left
                //     0   1
                // 0 [ 0 , 1 ]  ->  [ 1 , 3 ]  =  [ 0,1 , 1,1 ]  =  [ col,size-row , col,size-row ]
                // 1 [ 2 , 3 ]      [ 0 , 2 ]     [ 0,0 , 1,0 ]     [ col,size-row , col,size-row ]
                square.allMatch { row, col, value -> pattern[col][square.lastIndex - row] == value }

    private fun List<List<Char>>.allMatch(condition: (Int, Int, Char) -> Boolean): Boolean {
        forEachIndexed { row, rowList ->
            rowList.forEachIndexed { col, value ->
                if (!condition(row, col, value)) {
                    return false
                }
            }
        }
        return true
    }

    private fun applyRuleAt(rule: Rule, start: Coordinates) {
        rule.replacement.forEachIndexed { row, rowString ->
            rowString.forEachIndexed { col, value ->
                this[start.row + row, start.col + col] = value
            }
        }
    }

    private fun forEachSquareOfSize(size: Int, block: (Coordinates, List<List<Char>>) -> Unit) {
        for (row in 0..<height step size) {
            for (col in 0..<height step size) {
                block(Coordinates(row, col), subGridAt(row, col, size))
            }
        }
    }
}

private fun List<List<Char>>.countPixelsThatAreOn() = sumOf { row ->
    row.count { it == on }
}
