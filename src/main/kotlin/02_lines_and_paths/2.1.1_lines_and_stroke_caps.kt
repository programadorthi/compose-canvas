package `02_lines_and_paths`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.*

fun main() = Window {
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use { Font(it.getTypeface(0)) }
    }
    val thickLinePaint = remember {
        Paint().apply {
            color = Color.Magenta.toArgb()
            mode = PaintMode.STROKE
            strokeWidth = 50f
        }
    }
    val thinLinePaint = remember {
        Paint().apply {
            color = Color.Black.toArgb()
            mode = PaintMode.STROKE
            strokeWidth = 2f
        }
    }
    font.size = 75f
    Canvas(modifier = Modifier.fillMaxSize()) {
        val xLine1 = 100f
        val xLine2 = size.width - xLine1
        var y = font.spacing
        drawIntoCanvas { cvs ->
            val canvas = cvs.nativeCanvas
            for (cap in PaintStrokeCap.values()) {
                // Display StrokeCap name
                val textWidth = font.measureTextWidth(cap.name)
                canvas.drawString(
                    cap.name,
                    center.x - (textWidth / 2),
                    y,
                    font,
                    Paint()
                )
                y += font.spacing
                // Display thick line
                thickLinePaint.strokeCap = cap
                canvas.drawLine(
                    xLine1,
                    y,
                    xLine2,
                    y,
                    thickLinePaint
                )
                // Display thin line
                canvas.drawLine(
                    xLine1,
                    y,
                    xLine2,
                    y,
                    thinLinePaint
                )
                y += 2 * font.spacing
            }
        }
    }
}