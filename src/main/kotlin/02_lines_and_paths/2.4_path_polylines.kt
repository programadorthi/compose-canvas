package `02_lines_and_paths`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

fun main() = Window {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val radius = min(center.x, center.y)
        val path = Path()
        for (angle in 0 until 3600) {
            val scaledRadius = radius * angle / 3600
            val radians = PI * angle / 180
            val x = center.x + scaledRadius * cos(radians)
            val y = center.y + scaledRadius * sin(radians)
            when (angle) {
                0 -> path.moveTo(x.toFloat(), y.toFloat())
                else -> path.lineTo(x.toFloat(), y.toFloat())
            }
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 5f
            )
        )
    }
}