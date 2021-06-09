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
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode

fun main() = Window {
    val text = "OUTLINE"
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use {
                Font(it.getTypeface(0))
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
        drawIntoCanvas { cvs ->
            val canvas = cvs.nativeCanvas
            canvas.drawString(
                text,
                xText,
                yText,
                font,
                Paint().apply {
                    color = Color.Blue.toArgb()
                    mode = PaintMode.STROKE
                    strokeWidth = 1f
                }
            )
        }
    }
}