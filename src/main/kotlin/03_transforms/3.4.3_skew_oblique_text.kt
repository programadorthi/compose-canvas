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
        val textPaint = remember {
            Paint().apply {
                color = Color.Magenta.toArgb()
                mode = PaintMode.FILL
            }
        }
        val title = "Oblique Text"
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { cvs ->
                font.size = size.width / 8

                val canvas = cvs.nativeCanvas
                val textBounds = font.measureText(title, textPaint)
                val textAlignCenter = -textBounds.width / 2

                canvas.translate(center.x, center.y)
                canvas.skew(
                    tan(PI * -20 / 180).toFloat(),
                    tan(PI * 0 / 180).toFloat(),
                )
                canvas.drawString(
                    title,
                    textAlignCenter,
                    0f,
                    font,
                    textPaint
                )
            }
        }
    }
}