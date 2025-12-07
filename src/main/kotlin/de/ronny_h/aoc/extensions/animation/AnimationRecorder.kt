package de.ronny_h.aoc.extensions.animation

import de.ronny_h.aoc.extensions.asList
import java.io.File

class AnimationRecorder {
    private val frames = mutableListOf<List<String>>()

    fun record(frame: String) {
        frames.add(frame.asList())
    }

    fun saveTo(fileName: String) = frames
        .map(List<String>::createImage)
        .writeToGifFile(File(fileName))
}
