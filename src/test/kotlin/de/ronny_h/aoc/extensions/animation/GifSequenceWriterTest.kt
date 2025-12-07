package de.ronny_h.aoc.extensions.animation

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import java.awt.Color.BLACK
import javax.imageio.ImageIO


class GifSequenceWriterTest : FunSpec({

    val file = tempfile()

    test("a GIF can be rendered from a list of Strings and be written to a file") {
        val frames = List(3) { i ->
            val text = buildList { repeat(5) { add("$i".repeat(5)) } }
            text.createImage(emptyMap(), BLACK)
        }
//        frames.writeToGifFile(File("reference.gif"))
        frames.writeToGifFile(file)

        val newImage = ImageIO.read(file)
        val resource = this.javaClass.getResource("/reference.gif")
        val referenceImage = ImageIO.read(resource)

        newImage.height shouldBe referenceImage.height
        newImage.width shouldBe referenceImage.width

        for (x in 0..<newImage.width) {
            for (y in 0..<newImage.height) {
                newImage.getRGB(x, y) shouldBe referenceImage.getRGB(x, y)
            }
        }
    }
})
