fun main() {
    val day = "Day14"

    val robotPattern = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()

    println("$day part 1")

    fun List<String>.parseRobots() = map {
        val (px, py, vx, vy) = robotPattern.find(it)!!.destructured
        Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
    }

    fun List<Robot>.move(width: Int, height: Int, seconds: Int) = map {
        it.copy(
            px = (it.px + seconds * it.vx).mod(width),
            py = (it.py + seconds * it.vy).mod(height),
        )
    }

    fun List<Robot>.countQuadrants(width: Int, height: Int) = fold(QuadrantCounts(0, 0, 0, 0)) { counts, it ->
        if (it.px in 0..<width / 2 && it.py in 0..<height / 2) {
            counts.q1++
        }
        if (it.px in width / 2 + 1..width && it.py in 0..<height / 2) {
            counts.q2++
        }
        if (it.px in 0..<width / 2 && it.py in height / 2 + 1..height) {
            counts.q3++
        }
        if (it.px in width / 2 + 1..width && it.py in height / 2 + 1..height) {
            counts.q4++
        }
        counts
    }

    fun part1Small(input: List<String>): Int {
        val width = 11
        val height = 7
        val (q1, q2, q3, q4) = input.parseRobots()
            .move(width, height, 100)
            .countQuadrants(width, height)
        return q1 * q2 * q3 * q4
    }

    fun part1(input: List<String>): Int {
        val width = 101
        val height = 103
        val (q1, q2, q3, q4) = input.parseRobots()
            .move(width, height, 100)
            .countQuadrants(width, height)
        return q1 * q2 * q3 * q4
    }

    printAndCheck(
        """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent().lines(),
        ::part1Small, 12
    )

    val input = readInput(day)
    printAndCheck(input, ::part1, 218433348)
}

data class Robot(val px: Int, val py: Int, val vx: Int, val vy: Int)

data class QuadrantCounts(var q1: Int, var q2: Int, var q3: Int, var q4: Int)
