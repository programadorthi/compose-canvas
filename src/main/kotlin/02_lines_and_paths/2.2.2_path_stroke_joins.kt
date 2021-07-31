package `02_lines_and_paths`

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
import org.jetbrains.skija.*

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
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
        Canvas(modifier = Modifier.fillMaxSize()) {
            font.size = 75f
            val xLine1 = 100f
            val xLine2 = size.width - xLine1
            var y = 2 * font.spacing
            drawIntoCanvas { cvs ->
                val canvas = cvs.nativeCanvas
                for (join in PaintStrokeJoin.values()) {
                    val xText = size.width - font.measureTextWidth(join.toString()) - 100f
                    canvas.drawString(
                        join.toString(),
                        xText,
                        y,
                        font,
                        Paint()
                    )
                    val path = Path()
                    path.moveTo(xLine1, y - 80)
                    path.lineTo(xLine1, y + 80)
                    path.lineTo(xLine2, y + 80)
                    thickLinePaint.strokeJoin = join
                    canvas.drawPath(path, thickLinePaint)
                    canvas.drawPath(path, thinLinePaint)
                    y += 3 * font.spacing
                }
            }
        }
    }
}