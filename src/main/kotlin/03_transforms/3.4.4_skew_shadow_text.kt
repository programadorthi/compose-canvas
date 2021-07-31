package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import kotlin.math.PI
import kotlin.math.tan

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val font = remember {
            FontMgr.getDefault()
                .matchFamily("Arial")
                .use { Font(it.getTypeface(0)) }
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { cvs ->
                font.size = size.width / 6

                val text = "Shadow"
                val xText = 20f
                val yText = center.y
                val canvas = cvs.nativeCanvas
                val textPaint = Paint().apply {
                    color = Color.LightGray.toArgb()
                    mode = PaintMode.FILL
                }
                val textBounds = font.measureText(text, textPaint)

                // Shadow
                canvas.save()
                canvas.translate(xText, yText + textBounds.bottom)
                canvas.skew(tan(-PI / 4).toFloat(), 0f)
                canvas.scale(1f, 3f)
                canvas.translate(-xText, -yText - textBounds.bottom)
                canvas.drawString(
                    text,
                    xText,
                    yText,
                    font,
                    textPaint
                )
                canvas.restore()

                // Text
                textPaint.color = Color.Magenta.toArgb()
                canvas.drawString(
                    text,
                    xText,
                    yText,
                    font,
                    textPaint
                )
            }
        }
    }
}