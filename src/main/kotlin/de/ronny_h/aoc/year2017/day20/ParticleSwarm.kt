package de.ronny_h.aoc.year2017.day20

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.extensions.combinations
import de.ronny_h.aoc.extensions.numbers.sumOfFirstNaturalNumbers
import de.ronny_h.aoc.extensions.threedim.Vector
import de.ronny_h.aoc.extensions.threedim.Vector.Companion.ZERO
import de.ronny_h.aoc.extensions.threedim.times

fun main() = ParticleSwarm().run(344, 404)

class ParticleSwarm : AdventOfCode<Int>(2017, 20) {
    override fun part1(input: List<String>): Int {
        val allParticles = input.parseParticles()

        // in the long term, the particles with minimal absolute acceleration stay closest to ZERO
        val sorted = allParticles.withIndex().sortedBy { it.value.acceleration.abs() }
        val minAcceleration = sorted.first().value.acceleration.abs()
        var particles = sorted.takeWhile { it.value.acceleration.abs() == minAcceleration }

        particles.forEach { println("${it.index}: ${it.value.acceleration.abs()} - ${it.value}") }

        // for equal absolute acceleration, their direction and initial velocity are relevant
        // -> simulate a significant amount of ticks
        var closest = 0
        var oldClosest: Int
        var closestIsTheSameCount = 0
        var iterations = 0

        while (closestIsTheSameCount < 10000) {
            iterations++
            oldClosest = closest
            particles = particles.map { IndexedValue(it.index, it.value.update()) }
            closest = particles.map { IndexedValue(it.index, it.value.position taxiDistanceTo ZERO) }
                .minBy { it.value }
                .index
            closestIsTheSameCount += if (oldClosest == closest) 1 else 0
        }

        println("found closest in $iterations iterations")
        return closest
    }

    override fun part2(input: List<String>): Int {
        // for one particle:
        // p_i = p_0 + i*v_0 + (i*(i+1)/2)*a  // i*(i+1)/2 = sumOfFirstNaturalNumbers(i)
        //
        // particles q, r collide in iteration i when:
        //      p_q_i                              ==  p_r_i
        // <=>  p_q_0 + i*v_q_0 + (i*(i+1)/2)*a_q  ==  p_r_0 + i*v_r_0 + (i*(i+1)/2)*a_r
        // <=>  0 == p_q_0 - p_r_0 + i*(v_q_0 - v_r_0) + (i*(i+1)/2)*(a_q - a_r)

        val particles = input.parseParticles().withIndex().toList()
        val toRemove = mutableSetOf<Int>()

        for (i in 0..100) {
            particles
                .filterNot { it.index in toRemove }
                .combinations()
                .forEach {
                    if (ZERO == differenceOf(it.first.value, it.second.value, i)) {
                        toRemove.add(it.first.index)
                        toRemove.add(it.second.index)
                    }
                }
            if (i % 10 == 0) {
                println("$i: ${particles.size - toRemove.size}")
            }
        }

        return particles.size - toRemove.size
    }

    private fun differenceOf(q: Particle, r: Particle, i: Int): Vector {
        return q.position - r.position + i * (q.velocity - r.velocity) + sumOfFirstNaturalNumbers(i) * (q.acceleration - r.acceleration)
    }
}

data class Particle(val position: Vector, val velocity: Vector, val acceleration: Vector) {
    fun update(): Particle {
        val newVelocity = velocity + acceleration
        return Particle(position + newVelocity, newVelocity, acceleration)
    }
}

fun List<String>.parseParticles() = map {
    val (p, v, a) = it.split(", ")
    Particle(
        position = p.toVector("p"),
        velocity = v.toVector("v"),
        acceleration = a.toVector("a"),
    )
}

private fun String.toVector(name: String): Vector {
    val (x, y, z) = substringAfter("$name=<")
        .substringBefore(">")
        .split(",")
        .map(String::trim)
        .map(String::toLong)
    return Vector(x, y, z)
}
