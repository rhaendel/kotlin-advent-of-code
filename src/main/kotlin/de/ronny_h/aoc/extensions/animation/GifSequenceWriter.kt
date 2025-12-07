package de.ronny_h.aoc.extensions.animation

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


private const val fontSize = 20
private const val drawStartX = 3
private const val rowHeight = fontSize + 1
private const val colWidth = 3 / 5.0 * fontSize
private val font = Font(MONOSPACED, PLAIN, fontSize)

fun List<String>.createImage(): BufferedImage {
    val width = 2 * drawStartX + (this.first().length * colWidth).toInt()
    val height = size * rowHeight - 13
    val bufferedImage = BufferedImage(width, height, TYPE_INT_RGB)
    val graphics2D = bufferedImage.graphics as Graphics2D
    graphics2D.font = font
    graphics2D.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
    forEachIndexed { rowNo, row ->
        graphics2D.drawString(row, drawStartX, rowNo * rowHeight)
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
        GifSequenceWriter(out, first().type, 200, true).use { gifWriter ->
            forEach { image ->
                gifWriter.write(image)
            }
        }
    }
}

private operator fun IIOMetadataNode.set(name: String, value: String) = setAttribute(name, value)
