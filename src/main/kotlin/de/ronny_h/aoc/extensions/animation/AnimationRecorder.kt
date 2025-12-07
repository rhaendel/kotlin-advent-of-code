package de.ronny_h.aoc.extensions.animation

import de.ronny_h.aoc.extensions.asList
import java.awt.Color
import java.awt.Color.DARK_GRAY
import java.io.File

class AnimationRecorder {
    private val frames = mutableListOf<List<String>>()

    fun record(frame: String) {
        frames.add(frame.asList())
    }

    fun saveTo(fileName: String, colors: Map<Char, Color> = emptyMap(), background: Color = DARK_GRAY) = frames
        .map { it.createImage(colors, background) }
        .writeToGifFile(File(fileName))
}
