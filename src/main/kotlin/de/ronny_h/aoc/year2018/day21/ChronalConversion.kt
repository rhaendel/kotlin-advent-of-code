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
    var instrPtr = 0 // 0..30

    fun run() {
        // #ip 5 -> register 5 = the instruction pointer

        verifyBitwiseAndOperatesOnANumber()

        // 5. seti 0 0 4
        reg4 = 0

        while (true) {
            // 6. bori 4 65536 3
            reg3 = reg4 or 65536

            // 7. seti 4332021 4 4
            reg4 = 4332021

            while (true) {
                // 8. bani 3 255 2
                reg2 = reg3 and 255

                // 9. addr 4 2 4
                reg4 = reg4 + reg2

                // 10. bani 4 16777215 4
                reg4 = reg4 and 16777215

                // 11. muli 4 65899 4
                reg4 = reg4 * 65899

                // 12. bani 4 16777215 4
                reg4 = reg4 and 16777215

                // 13. gtir 256 3 2
                if (256 > reg3) {
                    reg2 = 1
                } else {
                    reg2 = 0
                }

                // 14. addr 2 5 5
                instrPtr = reg2 + instrPtr // skip next if reg3 < 256

                // 15. addi 5 1 5
                instrPtr = instrPtr + 1 // skip next

                // 16. seti 27 5 5
                instrPtr = 27 // goto step 28
                if (256 > reg3) {
                    break
                }

                // 17. seti 0 2 2
                reg2 = 0

                while (true) {
                    // 18. addi 2 1 1
                    reg1 = reg2 + 1

                    // 19. muli 1 256 1
                    reg1 = reg1 * 256

                    // 20. gtrr 1 3 1
                    if (reg1 > reg3) {
                        reg1 = 1
                    } else {
                        reg1 = 0
                    }

                    // 21. addr 1 5 5
                    instrPtr = reg1 + instrPtr // skip next if reg1 was > reg3 before step 20

                    // 22. addi 5 1 5
                    instrPtr = instrPtr + 1 // skip next

                    // 23. seti 25 2 5
                    instrPtr = 25 // goto step 26
                    if (reg1 == 1) {
                        break
                    }

                    // 24. addi 2 1 2
                    reg2 = reg2 + 1

                    // 25. seti 17 3 5
                    instrPtr = 17 // goto step 18
                }

                // 26. setr 2 7 3
                reg3 = reg2

                // 27. seti 7 1 5
                instrPtr = 7 // goto step 8
            }

            // 28. eqrr 4 0 2
            if (reg4 == reg0) {
                reg2 = 1
            } else {
                reg2 = 0
            }

            // 29. addr 2 5 5
            instrPtr = reg2 + instrPtr // skip next if reg4 == reg0 -> terminate
            if (reg4 == reg0) {
                return
            }

            // 30. seti 5 6 5
            instrPtr = 5 // goto step 6
        }
    }

    private fun verifyBitwiseAndOperatesOnANumber() {
        do {
            // 0. seti 123 0 4
            reg4 = 123 // 1111011

            // 1. bani 4 456 4
            reg4 = reg4 and 456 // 111001000
            //   1111011 AND
            // 111001000
            // 001001000 = 72

            // 2. eqri 4 72 4
            if (reg4 == 72) {
                reg4 = 1
            } else {
                reg4 = 0
            }

            // 3. addr 4 5 5
            instrPtr = reg4 + instrPtr // skip next if reg4 was 72 before step 2

            if (reg4 != 1) {
                // 4. seti 0 0 5
                instrPtr = 0 // go to step 0
            }
            log.info { "reg4: $reg4" }
        } while (reg4 != 1)
    }
}
