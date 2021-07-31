package `01_drawing_basics`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import kotlin.math.PI
import kotlin.math.sin

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val textOne = "CODE"
        val textTwo = "MORE"
        val duration = 5f // seconds
        val font = remember {
            FontMgr.getDefault()
                .matchFamily("Arial")
                .use { Font(it.getTypeface(0)) }
        }
        val paint = remember {
            Paint().apply {
                color = Color.Blue.toArgb()
            }
        }
        var alpha by remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) {
            while (true) {
                withFrameNanos { nanoTime ->
                    val elapsedInSeconds = nanoTime / 1_000_000_000L
                    val progress = elapsedInSeconds % duration / duration
                    alpha = 0.5f * (1 + sin(progress * 2 * PI)).toFloat()
                }
                delay(1000 / 60)
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            font.size = 100f
            val textWidth = font.measureTextWidth(textOne)
            font.size *= 0.9f * size.width / textWidth
            drawIntoCanvas { cvs ->
                val canvas = cvs.nativeCanvas
                var textBounds = font.measureText(textOne)
                var xText = size.width / 2 - (textBounds.right - textBounds.left) / 2
                var yText = size.height / 2 - (textBounds.bottom - textBounds.top) / 2
                canvas.drawString(
                    textOne,
                    xText,
                    yText,
                    font,
                    paint.setAlphaf(1f - alpha)
                )

                textBounds = font.measureText(textTwo)
                xText = size.width / 2 - (textBounds.right - textBounds.left) / 2
                yText = size.height / 2 - (textBounds.bottom - textBounds.top) / 2
                canvas.drawString(
                    textTwo,
                    xText,
                    yText,
                    font,
                    paint.setAlphaf(alpha)
                )
            }
        }
    }
}