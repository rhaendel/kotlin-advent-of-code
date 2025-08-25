package de.ronny_h.aoc.year2017.day21

import de.ronny_h.aoc.extensions.asList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FractalArtTest : StringSpec({

    val input = """
        ../.# => ##./#../...
        .#./..#/### => #..#/..../..../#..#
    """.asList()

    "input can be parsed" {
        input.parseEnhancementRules() shouldBe RuleSet(
            listOf(
                Rule(
                    pattern = listOf("..", ".#"),
                    onCount = 1,
                    replacement = listOf("##.", "#..", "...")
                )
            ),
            listOf(
                Rule(
                    pattern = listOf(".#.", "..#", "###"),
                    onCount = 5,
                    replacement = listOf("#..#", "....", "....", "#..#")
                )
            ),
        )
    }

    "a 3x3 rule applies to a 3x3 grid that exactly matches the pattern" {
        val rules = listOf(".#./..#/### => #..#/..../..../#..#").parseEnhancementRules()
        val initConfiguration = """
            .#.
            ..#
            ###
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            #..#
            ....
            ....
            #..#
        """.trimIndent()
    }

    "a 3x3 rule applies to a 3x3 grid that matches the rotated pattern" {
        val rules = listOf(".#./..#/### => #..#/..../..../#..#").parseEnhancementRules()
        val initConfiguration = """
            .##
            #.#
            ..#
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            #..#
            ....
            ....
            #..#
        """.trimIndent()
    }

    "a 3x3 rule applies to a 3x3 grid that matches the rotated and flipped pattern" {
        val rules = listOf(".#./..#/### => #..#/..../..../#..#").parseEnhancementRules()
        val initConfiguration = """
            ..#
            #.#
            .##
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            #..#
            ....
            ....
            #..#
        """.trimIndent()
    }

    "a 3x3 rule applies to a 3x3 grid that matches the pattern flipped horizontally" {
        val rules = listOf(".#./..#/### => #..#/..../..../#..#").parseEnhancementRules()
        val initConfiguration = """
            .#.
            #..
            ###
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            #..#
            ....
            ....
            #..#
        """.trimIndent()
    }

    "a 3x3 rule applies to a 3x3 grid that matches the pattern flipped vertically" {
        val rules = listOf(".#./..#/### => #..#/..../..../#..#").parseEnhancementRules()
        val initConfiguration = """
            ###
            ..#
            .#.
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            #..#
            ....
            ....
            #..#
        """.trimIndent()
    }

    "a 2x2 rule applies to a 4x4 grid for exact matches of the pattern" {
        val rules = listOf("../.# => ##./#../...").parseEnhancementRules()
        val initConfiguration = """
            ....
            .#.#
            ....
            .#.#
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            ##.##.
            #..#..
            ......
            ##.##.
            #..#..
            ......
        """.trimIndent()
    }

    "a 2x2 rule applies to a 4x4 grid for rotated matches of the pattern" {
        val rules = listOf("#./.# => ##./#../...").parseEnhancementRules()
        val initConfiguration = """
            #..#
            .##.
            .##.
            #..#
        """.asList()
        val grid = FractalArtGrid(initConfiguration)

        grid.applyRules(rules).toString().trimIndent() shouldBe """
            ##.##.
            #..#..
            ......
            ##.##.
            #..#..
            ......
        """.trimIndent()
    }

    "part 1 and 2: with the example rules, after 2 iterations 12 pixels are on" {
        FractalArt().applyTheRulesToTheGivenInitConfiguration(input, 2) shouldBe 12
    }
})
