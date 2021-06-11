package `02_lines_and_paths`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import org.jetbrains.skija.Path
import org.jetbrains.skija.PathFillMode
import kotlin.math.min

fun main() = Window {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { cvs ->
            val radius = min(size.width, size.height) / 4
            val path = Path()
            path.fillMode = PathFillMode.EVEN_ODD
            path.addCircle(center.x - radius / 2, center.y - radius / 2, radius)
            path.addCircle(center.x - radius / 2, center.y + radius / 2, radius)
            path.addCircle(center.x + radius / 2, center.y - radius / 2, radius)
            path.addCircle(center.x + radius / 2, center.y + radius / 2, radius)
            val canvas = cvs.nativeCanvas
            val paint = Paint().apply {
                color = Color.Cyan.toArgb()
                mode = PaintMode.FILL
            }
            canvas.drawPath(path, paint)

            paint.mode = PaintMode.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.Magenta.toArgb()
            canvas.drawPath(path, paint)
        }
    }
}