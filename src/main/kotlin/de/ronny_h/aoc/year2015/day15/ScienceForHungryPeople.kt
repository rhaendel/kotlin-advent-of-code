package de.ronny_h.aoc.year2015.day15

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.sequenceNumbersOfEqualSum
import kotlin.math.max

fun main() = ScienceForHungryPeople().run(222870, 117936)

class ScienceForHungryPeople : AdventOfCode<Int>(2015, 15) {

    private val totalTeaspoons = 100
    private val totalCalories = 500

    override fun part1(input: List<String>): Int {
        val ingredients = input.parseIngredients()
        return sequenceNumbersOfEqualSum(ingredients.size, totalTeaspoons)
            .map { totalScoreOf(ingredients, it) }
            .max()
    }

    override fun part2(input: List<String>): Int {
        val ingredients = input.parseIngredients()
        return sequenceNumbersOfEqualSum(ingredients.size, totalTeaspoons)
            .filter { calorieTotalOf(ingredients, it) == totalCalories }
            .map { totalScoreOf(ingredients, it) }
            .max()
    }

    private fun calorieTotalOf(ingredients: List<Ingredient>, teaspoons: List<Int>): Int =
        ingredients.withIndex().sumOf {
            it.value.calories * teaspoons[it.index]
        }

    private fun totalScoreOf(
        ingredients: List<Ingredient>,
        teaspoons: List<Int>
    ): Int {
        val sums = ingredients.foldIndexed(Ingredient(0, 0, 0, 0, 0)) { i, acc, ingredient ->
            Ingredient(
                capacity = acc.capacity + teaspoons[i] * ingredient.capacity,
                durability = acc.durability + teaspoons[i] * ingredient.durability,
                flavor = acc.flavor + teaspoons[i] * ingredient.flavor,
                texture = acc.texture + teaspoons[i] * ingredient.texture,
                calories = acc.calories + teaspoons[i] + ingredient.calories,
            )
        }.let {
            Ingredient(
                capacity = max(it.capacity, 0),
                durability = max(it.durability, 0),
                flavor = max(it.flavor, 0),
                texture = max(it.texture, 0),
                calories = max(it.calories, 0),
            )
        }
        return sums.capacity * sums.durability * sums.flavor * sums.texture
    }
}

fun List<String>.parseIngredients() = this.map {
    val (capacity, durability, flavor, texture, calories) = it.substringAfter(": ").split(", ")
    Ingredient(
        capacity = capacity.secondAsInt(),
        durability = durability.secondAsInt(),
        flavor = flavor.secondAsInt(),
        texture = texture.secondAsInt(),
        calories = calories.secondAsInt(),
    )
}

private fun String.secondAsInt(): Int = split(" ")[1].toInt()

data class Ingredient(val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)
