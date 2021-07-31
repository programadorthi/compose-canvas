package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import utils.hendecagramPath

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val strokeWidth = 3f
        val path = hendecagramPath()
        Canvas(modifier = Modifier.fillMaxSize()) {
            val pathBounds = path.getBounds()
            // Uncomment below with you want a version without touch window borders
            // .inflate(strokeWidth / 2)
            scale(
                scaleX = size.width / pathBounds.width,
                scaleY = size.height / pathBounds.height,
                pivot = Offset.Zero
            ) {
                translate(
                    left = -pathBounds.left,
                    top = -pathBounds.top
                ) {
                    drawPath(
                        path = path,
                        color = Color.Magenta
                    )
                    drawPath(
                        path = path,
                        color = Color.Blue,
                        style = Stroke(
                            join = StrokeJoin.Round,
                            width = strokeWidth
                        )
                    )
                }
            }
        }
    }
}