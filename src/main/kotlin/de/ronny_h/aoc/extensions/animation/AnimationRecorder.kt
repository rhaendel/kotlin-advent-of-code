package de.ronny_h.aoc.extensions.animation

import de.ronny_h.aoc.extensions.asList
import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.Color
import java.io.File

class AnimationRecorder {
    private val frames = mutableListOf<List<String>>()
    private val logger = KotlinLogging.logger {}

    fun record(frame: String) {
        frames.add(frame.asList())
    }

    fun saveTo(fileName: String, colors: Map<Char, Color> = emptyMap(), background: Color = purpleBlue) {
        frames
            .map { it.createImage(colors, background) }
            .writeToGifFile(File(fileName))
        logger.info { "An animation was saved to: $fileName" }
    }
}
