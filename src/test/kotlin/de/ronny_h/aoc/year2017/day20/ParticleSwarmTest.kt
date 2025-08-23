package de.ronny_h.aoc.year2017.day20

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.threedim.Vector
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ParticleSwarmTest : StringSpec({

    val input = """
        p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
        p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>
    """.asList()

    "input can be parsed" {
        input.parseParticles() shouldBe listOf(
            Particle(Vector(3, 0, 0), Vector(2, 0, 0), Vector(-1, 0, 0)),
            Particle(Vector(4, 0, 0), Vector(0, 0, 0), Vector(-2, 0, 0)),
        )
    }

    "a particle can be updated" {
        Particle(
            Vector(3, 2, 1),
            Vector(2, 1, -1),
            Vector(-1, 1, 2)
        ).update() shouldBe Particle(
            Vector(4, 4, 2),
            Vector(1, 2, 1),
            Vector(-1, 1, 2)
        )
    }

    "part 1: the particle that will stay closest to position <0,0,0> in the long term" {
        ParticleSwarm().part1(input) shouldBe 0
    }

    "part 2: only one particle is left after all collisions are resolved" {
        val input = """
            p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
            p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
            p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
            p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>
        """.asList()
        ParticleSwarm().part2(input) shouldBe 1
    }
})
