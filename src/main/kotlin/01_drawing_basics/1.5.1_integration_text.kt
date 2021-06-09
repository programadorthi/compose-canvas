package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.*

fun main() = Window {
    val text = "Hello Canvas"
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use {
                Font(it.getTypeface(0))
            }
    }
    val framePaint = remember {
        Paint().apply {
            color = Color.Blue.toArgb()
            mode = PaintMode.STROKE
            strokeWidth = 5f
        }
    }
    var fontSize by remember { mutableStateOf<Float?>(null) }
    val textWidth = font.measureTextWidth(text)
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (fontSize == null) {
            fontSize = 0.9f * size.width * font.size / textWidth
            font.size = fontSize!!
        }
        val textBounds = font.measureText(text)
        val xText = center.x - (textBounds.right - textBounds.left) / 2
        val yText = center.y - (textBounds.bottom - textBounds.top) / 2
        var frameRect = Rect.makeLTRB(
            textBounds.left,
            textBounds.top,
            textBounds.right,
            textBounds.bottom
        )
        frameRect = frameRect.offset(xText, yText)
        frameRect = frameRect.inflate(10f)
        drawIntoCanvas { cvs ->
            val canvas = cvs.nativeCanvas
            canvas.drawString(
                text,
                xText,
                yText,
                font,
                Paint()
            )
            canvas.drawRRect(
                RRect.makeLTRB(
                    frameRect.left,
                    frameRect.top,
                    frameRect.right,
                    frameRect.bottom,
                    20f
                ),
                framePaint
            )
            frameRect = frameRect.inflate(10f)
            canvas.drawRRect(
                RRect.makeLTRB(
                    frameRect.left,
                    frameRect.top,
                    frameRect.right,
                    frameRect.bottom,
                    30f
                ),
                framePaint.makeClone().apply {
                    color = Color.Magenta.toArgb()
                }
            )
        }
    }
}