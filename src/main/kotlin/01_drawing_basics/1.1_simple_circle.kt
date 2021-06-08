package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode

fun main() = Window {
    val strokePaint = remember {
        Paint().apply {
            color = Color.Red.toArgb()
            mode = PaintMode.STROKE
            strokeWidth = 25f
        }
    }
    val fillPaint = remember {
        Paint().apply {
            color = Color.Blue.toArgb()
            mode = PaintMode.FILL
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { cvs ->
            // You don't need native canvas to simple drawing
            // I'm using it because I'm learning Skia directly
            val canvas = cvs.nativeCanvas
            canvas.drawCircle(
                size.width / 2,
                size.height / 2,
                100f,
                strokePaint
            )
            canvas.drawCircle(
                size.width / 2,
                size.height / 2,
                100f,
                fillPaint
            )
        }
    }
}