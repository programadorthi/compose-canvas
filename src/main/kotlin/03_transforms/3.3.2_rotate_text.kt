package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val font = remember {
            FontMgr.getDefault()
                .matchFamily("Arial")
                .use { Font(it.getTypeface(0)) }
        }
        val textPaint = remember {
            Paint().apply {
                color = Color.Black.toArgb()
                mode = PaintMode.FILL
            }
        }
        val text = "    ROTATE"
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            font.size = 72f
            val textBounds = font.measureText(text, textPaint)
            val yText = center.y - textBounds.height / 2 - textBounds.top
            for (degrees in 0..360 step 30) {
                rotate(degrees = degrees.toFloat()) {
                    val canvas = drawContext.canvas.nativeCanvas
                    canvas.drawString(
                        text,
                        center.x,
                        yText,
                        font,
                        textPaint
                    )
                }
            }
            // TODO: Uncomment below if you want an incremental rotate version
            /*val yText = -textBounds.height / 2 - textBounds.top
            val canvas = drawContext.canvas.nativeCanvas
            canvas.translate(center.x, center.y)
            repeat(12) {
                canvas.drawString(
                    text,
                    0f,
                    yText,
                    font,
                    textPaint
                )
                canvas.rotate(30f)
            }*/
        }
    }
}