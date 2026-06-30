package de.ronny_h.aoc.year2018.day23

import de.infix.testBalloon.framework.core.testSuite
import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.threedim.Vector
import io.kotest.matchers.shouldBe

val ExperimentalEmergencyTeleportationTest by testSuite {

    val input = """
        pos=<0,0,0>, r=4
        pos=<1,0,0>, r=1
        pos=<4,0,0>, r=3
        pos=<0,2,0>, r=1
        pos=<0,5,0>, r=3
        pos=<0,0,3>, r=1
        pos=<1,1,1>, r=1
        pos=<1,1,2>, r=1
        pos=<1,3,1>, r=1
    """.asList()

    test("example nanobots can be parsed") {
        input.parseBots() shouldBe listOf(
            Nanobot(Vector(0, 0, 0), 4),
            Nanobot(Vector(1, 0, 0), 1),
            Nanobot(Vector(4, 0, 0), 3),
            Nanobot(Vector(0, 2, 0), 1),
            Nanobot(Vector(0, 5, 0), 3),
            Nanobot(Vector(0, 0, 3), 1),
            Nanobot(Vector(1, 1, 1), 1),
            Nanobot(Vector(1, 1, 2), 1),
            Nanobot(Vector(1, 3, 1), 1),
        )
    }

    test("negative vector coordinates can be parsed") {
        listOf("pos=<-1,-2,-3>, r=1").parseBots() shouldBe listOf(Nanobot(Vector(-1, -2, -3), 1))
    }

    test("part 1: the number of nanobots in range") {
        ExperimentalEmergencyTeleportation().part1(input) shouldBe 7
    }


    test("part 2: the shortest manhattan distance between the range of any of the nanobots of the biggest cluster and 0,0,0") {
        val input = """
            pos=<10,12,12>, r=2
            pos=<12,14,12>, r=2
            pos=<16,12,12>, r=4
            pos=<14,14,14>, r=6
            pos=<50,50,50>, r=200
            pos=<10,10,10>, r=5
        """.asList()
        ExperimentalEmergencyTeleportation().part2(input) shouldBe 36
    }
}
