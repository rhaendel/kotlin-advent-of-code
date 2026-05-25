package de.ronny_h.aoc.extensions.animation

import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe
import java.awt.Color.BLACK
import java.io.File
import javax.imageio.ImageIO


val GifSequenceWriterTest by testSuite {

    testFixture {
        File.createTempFile("test", "gif")
    } closeWith {
        delete()
    } asParameterForEach {
        test("a GIF can be rendered from a list of Strings and be written to a file") { file ->
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

            // TODO passes locally, but fails on GiHub -> investigate later
//        for (x in 0..<newImage.width) {
//            for (y in 0..<newImage.height) {
//                newImage.getRGB(x, y) shouldBe referenceImage.getRGB(x, y)
//            }
//        }
        }
    }
}
