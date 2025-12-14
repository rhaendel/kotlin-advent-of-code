package de.ronny_h.aoc.extensions.animation

import java.awt.Color
import java.awt.Color.LIGHT_GRAY
import java.awt.Font
import java.awt.Font.MONOSPACED
import java.awt.Font.PLAIN
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.*
import javax.imageio.metadata.IIOMetadata
import javax.imageio.metadata.IIOMetadataNode
import javax.imageio.stream.FileImageOutputStream
import javax.imageio.stream.ImageOutputStream

val green = Color(0, 153, 0)
val lightGreen = Color(0, 186, 13)
val purpleBlue = Color(15, 15, 35)
val lightGrey = Color(204, 204, 204)
val gray = Color(70, 70, 70)
val darkGrey = Color(16, 16, 26)

private const val scale = 1
private const val fontSize = scale * 20
private const val rowHeight = fontSize + scale
private const val colWidth = 3 / 5.0 * fontSize
private const val drawStartX = 3
private const val drawStartY = rowHeight - 2
private const val timeBetweenFramesMS = 250
private val font = Font(MONOSPACED, PLAIN, fontSize)

fun List<String>.createImage(colors: Map<Char, Color>, background: Color): BufferedImage {
    val gifColors = colors.withDefault { LIGHT_GRAY }
    val width = 2 * drawStartX + (this.first().length * colWidth).toInt()
    val height = drawStartY + size * rowHeight - scale * 13
    val bufferedImage = BufferedImage(width, height, TYPE_INT_RGB)
    val graphics2D = bufferedImage.graphics as Graphics2D
    graphics2D.font = font
    graphics2D.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
    graphics2D.background = background
    graphics2D.clearRect(0, 0, width, height)
    forEachIndexed { rowNo, row ->
        row.forEachIndexed { colNo, char ->
            graphics2D.color = gifColors.getValue(char)
            graphics2D.drawString("$char", (drawStartX + colNo * colWidth).toInt(), drawStartY + rowNo * rowHeight)
        }
    }
    return bufferedImage
}

class GifSequenceWriter(
    outputStream: ImageOutputStream,
    imageType: Int,
    timeBetweenFramesMS: Int,
    loopContinuously: Boolean,
) : AutoCloseable {

    private val gifWriter: ImageWriter = getWriter()
    private val imageWriteParam: ImageWriteParam = gifWriter.defaultWriteParam
    private val imageMetaData: IIOMetadata

    init {
        val imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType)
        imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam)

        val metaFormatName = imageMetaData.getNativeMetadataFormatName()
        val root = imageMetaData.getAsTree(metaFormatName) as IIOMetadataNode

        val graphicsControl: IIOMetadataNode = root.getNode("GraphicControlExtension")
        graphicsControl["disposalMethod"] = "none"
        graphicsControl["userInputFlag"] = "FALSE"
        graphicsControl["transparentColorFlag"] = "FALSE"
        graphicsControl["delayTime"] = (timeBetweenFramesMS / 10).toString()
        graphicsControl["transparentColorIndex"] = "0"

        root.getNode("CommentExtensions")["CommentExtension"] = "Created by rhaendel for Advent of Code in Kotlin"

        val loop = if (loopContinuously) 0 else 1
        val appAttributes = IIOMetadataNode("ApplicationExtension")
        appAttributes["applicationID"] = "NETSCAPE"
        appAttributes["authenticationCode"] = "2.0"
        appAttributes.userObject = byteArrayOf(0x1, (loop and 0xFF).toByte(), 0.toByte())

        root.getNode("ApplicationExtensions").appendChild(appAttributes)

        imageMetaData.setFromTree(metaFormatName, root)

        gifWriter.setOutput(outputStream)
        gifWriter.prepareWriteSequence(null)
    }

    fun write(img: RenderedImage) = gifWriter.writeToSequence(
        IIOImage(img, null, imageMetaData),
        imageWriteParam
    )

    override fun close() {
        gifWriter.endWriteSequence()
        gifWriter.dispose()
    }

    companion object {
        private fun getWriter(): ImageWriter {
            val iter = ImageIO.getImageWritersBySuffix("gif")
            if (!iter.hasNext()) {
                throw IIOException("No GIF Image Writers exist")
            } else {
                return iter.next()
            }
        }

        private fun IIOMetadataNode.getNode(nodeName: String): IIOMetadataNode {
            for (i in 0..<length) {
                if (item(i).nodeName.equals(nodeName, ignoreCase = true)) {
                    return item(i) as IIOMetadataNode
                }
            }
            return IIOMetadataNode(nodeName).also { node ->
                appendChild(node)
            }
        }

    }
}

fun List<BufferedImage>.writeToGifFile(file: File) {
    FileImageOutputStream(file).use { out ->
        GifSequenceWriter(out, first().type, timeBetweenFramesMS, true).use { gifWriter ->
            forEach { image ->
                gifWriter.write(image)
            }
            repeat(3) {
                gifWriter.write(last())
            }
        }
    }
}

private operator fun IIOMetadataNode.set(name: String, value: String) = setAttribute(name, value)
