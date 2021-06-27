package utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Paint
import kotlin.math.pow

class TouchPoint(x: Float, y: Float) {
    var center: Offset = Offset(x, y)
        private set
    var color: Color = Color(red = 0f, green = 0f, blue = 1f, alpha = 0.64f)
    var radius: Float = 25f

    fun updateCenter(point: Offset) {
        center = point
    }

    fun paint(canvas: Canvas) {
        canvas.drawCircle(
            center.x,
            center.y,
            radius,
            Paint().setColor(color.toArgb())
        )
    }

    fun pointInCircle(point: Offset): Boolean =
        ((point.x - center.x).pow(2) + (point.y - center.y).pow(2)) < radius * radius
}