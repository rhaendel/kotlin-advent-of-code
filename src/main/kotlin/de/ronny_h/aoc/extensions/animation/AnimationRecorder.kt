package de.ronny_h.aoc.extensions.animation

import de.ronny_h.aoc.extensions.asList
import de.ronny_h.aoc.extensions.grids.Coordinates
import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.Color
import java.io.File
import kotlin.math.max

class AnimationRecorder(private val compact: Boolean = false) {
    private val frames = mutableListOf<List<String>>()
    private val logger = KotlinLogging.logger {}

    /**
     * Records the given [frame]. If the recorder is [compact], frames that are identical with the last recorded frame
     * get dropped.
     */
    fun record(frame: String) {
        val newFrame = frame.asList()
        if (frames.isEmpty() || !compact || frames.last() != newFrame) {
            frames.add(newFrame)
        }
    }

    /**
     * Repeats the last recorded frame while overriding the positions in [overrides] with the [overrideChar].
     */
    fun recordLastFrameWithOverrides(overrides: List<Coordinates>, overrideChar: Char) {
        if (frames.isEmpty()) return
        frames
            .last()
            .mapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    if (Coordinates(x, y) in overrides) overrideChar else char
                }.joinToString("")
            }
            .let { frames.add(it) }
    }

    /**
     * Repeats the last [nFrames] frames [times] times.
     */
    fun repeatLast(nFrames: Int, times: Int) {
        val toRepeat = frames.subList(max(0, frames.size - nFrames), frames.size).toList()
        repeat(times) {
            frames.addAll(toRepeat)
        }
    }

    /**
     * Saves the recorded frames sequence to an animated GIF file named [fileName]. If specified, colors are mapped
     * according to [colors] and the background gets colored in [background] color.
     */
    fun saveTo(
        fileName: String,
        colors: Map<Char, Color> = emptyMap(),
        background: Color = purpleBlue,
        timeBetweenFramesMS: Int = timeBetweenFramesMSDefault,
    ) {
        frames
            .map { it.createImage(colors, background) }
            .writeToGifFile(File(fileName), timeBetweenFramesMS)
        logger.info { "An animation was saved to: $fileName" }
    }
}
