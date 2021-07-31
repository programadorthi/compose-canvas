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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val textSize = 150f
        val font = remember {
            FontMgr.getDefault()
                .matchFamily("Arial")
                .use { Font(it.getTypeface(0)) }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            font.size = textSize
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                mode = PaintMode.FILL
            }
            val x = 10f
            var y = textSize
            drawIntoCanvas { cvs ->
                val canvas = cvs.nativeCanvas
                // Shadow
                canvas.translate(x, x)
                canvas.drawString(
                    "SHADOW",
                    x,
                    y,
                    font,
                    paint
                )
                canvas.translate(-x, -x) // Skija canvas translation is cumulative
                paint.color = Color.Magenta.toArgb()
                canvas.drawString(
                    "SHADOW",
                    x,
                    y,
                    font,
                    paint
                )

                y += 2 * textSize

                // Engrave
                canvas.translate(-5f, -5f)
                paint.color = Color.Black.toArgb()
                canvas.drawString(
                    "ENGRAVE",
                    x,
                    y,
                    font,
                    paint
                )
                canvas.resetMatrix()
                paint.color = Color.White.toArgb()
                canvas.drawString(
                    "ENGRAVE",
                    x,
                    y,
                    font,
                    paint
                )

                y += 2 * textSize

                // Emboss
                canvas.save()
                canvas.translate(5f, 5f)
                paint.color = Color.Black.toArgb()
                canvas.drawString(
                    "EMBOSS",
                    x,
                    y,
                    font,
                    paint
                )
                canvas.restore()
                paint.color = Color.White.toArgb()
                canvas.drawString(
                    "EMBOSS",
                    x,
                    y,
                    font,
                    paint
                )
            }
        }
    }
}