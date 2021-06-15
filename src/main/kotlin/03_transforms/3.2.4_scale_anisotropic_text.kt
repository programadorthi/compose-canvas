package `03_transforms`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.*

fun main() = Window {
    val text = "HELLO"
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use { Font(it.getTypeface(0)) }
    }
    val textPaint = remember {
        Paint().apply {
            color = Color.Blue.toArgb()
            mode = PaintMode.STROKE
            strokeJoin = PaintStrokeJoin.ROUND
            strokeWidth = 0.1f
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val textBounds = font
            .measureText(text, textPaint)
            .inflate(textPaint.strokeWidth / 2)
        scale(
            scaleX = size.width / textBounds.width,
            scaleY = size.height / textBounds.height,
            pivot = Offset.Zero
        ) {
            translate(
                left = -textBounds.left,
                top = -textBounds.top
            ) {
                drawContext.canvas.nativeCanvas.drawString(
                    text,
                    0f,
                    0f,
                    font,
                    textPaint
                )
            }
        }
    }
}