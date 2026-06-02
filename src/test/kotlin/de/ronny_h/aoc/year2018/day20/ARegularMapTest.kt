package de.ronny_h.aoc.year2018.day20

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe

val ARegularMapTest by testSuite {

    testSuite("part 1: The shortest path to the furthest room") {
        mapOf(
            "^WNE$" to 3,
            "^ENWWW(NEEE|SSE(EE|N))$" to 10,
            "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$" to 18,
            "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$" to 23,
            "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$" to 31,
        ).forEach { (regex, length) ->
            test("$regex: $length") {
                ARegularMap().part1(listOf(regex)) shouldBe length
            }
        }
    }

    test("part 2: The number of rooms with shortest path at least") {
        ARegularMap().part2(listOf("^WNE$")) shouldBe 0

        val project = BaseConstructionProject("WNE")
        project.shortestPathToFurthestRoom()
        project.countPathsHavingAtLeastDoors(2) shouldBe 2
    }
}
