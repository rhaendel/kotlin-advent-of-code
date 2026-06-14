package de.ronny_h.aoc.year2018.day21

import de.ronny_h.aoc.AdventOfCode
import de.ronny_h.aoc.year2018.day19.WristDeviceWithFlowControl
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select

fun main() = ChronalConversion().run(0, 0)

private val log = KotlinLogging.logger { }

class ChronalConversion : AdventOfCode<Int>(2018, 21) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun part1(input: List<String>): Int {
        runTheActualProgram(input)
        // ActivationSystem().run()
        return 0
    }

    private fun runTheActualProgram(input: List<String>) = runBlocking {
        val values = 0..100_000
        val deferreds = values.map { regZero ->
            async(Dispatchers.Default) {
                if (regZero % 1000 == 0) log.info { "launching $regZero" }
                val device = WristDeviceWithFlowControl(input, regZero)
                yield()
                device.runProgram()
                log.info { "finished $regZero" }
                regZero
            }
        }

        log.info { "receiving" }
        val result = select {
            deferreds.forEach {
                it.onAwait { value ->
                    log.info { "received $value" }
                    value
                }
            }
        }
        coroutineContext.cancelChildren()
        log.info { "result: $result" }
        return@runBlocking 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

class ActivationSystem {
    var reg0 = 0
    var reg1 = 0
    var reg2 = 0
    var reg3 = 0
    var reg4 = 0

    fun run() {
        reg4 = 0

        while (true) {
            reg3 = reg4 or 65536
            reg4 = 4332021

            while (true) {
                reg2 = reg3 and 255
                reg4 = reg4 + reg2
                reg4 = reg4 and 16777215
                reg4 = reg4 * 65899
                reg4 = reg4 and 16777215

                if (256 > reg3) {
                    reg2 = 1
                } else {
                    reg2 = 0
                }

                if (256 > reg3) {
                    break
                }

                reg2 = 0

                while (true) {
                    reg1 = reg2 + 1
                    reg1 = reg1 * 256
                    if (reg1 > reg3) {
                        reg1 = 1
                        break
                    } else {
                        reg1 = 0
                    }

                    reg2 = reg2 + 1
                }

                reg3 = reg2
            }

            if (reg4 == reg0) {
                reg2 = 1
                return
            } else {
                reg2 = 0
            }
        }
    }
}
