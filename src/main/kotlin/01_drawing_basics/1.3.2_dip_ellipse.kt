package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

fun main() = Window {
    val strokeWidth = 50f
    val halfStrokeWidth = strokeWidth / 2
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawOval(
            color = Color.Blue,
            topLeft = Offset(x = halfStrokeWidth, y = halfStrokeWidth),
            size = Size(width = size.width - strokeWidth, height = size.height - strokeWidth),
            style = Stroke(width = strokeWidth)
        )
    }
}