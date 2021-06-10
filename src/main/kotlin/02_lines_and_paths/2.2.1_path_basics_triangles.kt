package `02_lines_and_paths`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

fun main() = Window {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path()
        path.moveTo(center.x, 0.1f * size.height)
        path.lineTo(0.2f * size.width, 0.4f * size.height)
        path.lineTo(0.8f * size.width, 0.4f * size.height)
        path.lineTo(center.x, 0.1f * size.height)

        path.moveTo(center.x, 0.6f * size.height)
        path.lineTo(0.2f * size.width, 0.9f * size.height)
        path.lineTo(0.8f * size.width, 0.9f * size.height)
        path.close()

        drawPath(
            path = path,
            color = Color.Cyan
        )
        drawPath(
            path = path,
            color = Color.Magenta,
            style = Stroke(
                width = 50f
            )
        )
    }
}