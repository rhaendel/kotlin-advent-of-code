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
        // runTheActualProgram(input)
        val registerZero = 0
        ActivationSystem(registerZero).run()
        return registerZero
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

class ActivationSystem(private val reg0: Int = 0) {
    private var reg2 = 0
    private var reg3 = 0
    private var reg4 = 0

    fun run() {
        reg4 = 0

        do {
            reg3 = reg4 or 65536
            reg4 = 4332021

            while (true) {
                reg2 = reg3 and 255
                reg4 += reg2
                reg4 = reg4 and 16777215
                reg4 *= 65899
                reg4 = reg4 and 16777215

                if (reg3 < 256) {
                    break
                }

                reg2 = 0
                while ((reg2 + 1) * 256 <= reg3) {
                    reg2++
                }

                reg3 = reg2
            }
        } while (reg4 != reg0)
    }
}
