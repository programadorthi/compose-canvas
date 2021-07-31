package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import utils.hendecagramPath
import kotlin.math.min

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val count = 10
        val path = hendecagramPath()
        Canvas(modifier = Modifier.fillMaxSize()) {
            val pathBounds = path.getBounds()
            var scale = min(size.width / pathBounds.width, size.height / pathBounds.height)
            // 0 <= index <= 10
            repeat(count + 1) { index ->
                val color = Color(
                    red = 255 * (count - index) / count,
                    green = 0,
                    blue = 255 * index / count
                )
                // withTransform has internal save() and restore() operations
                withTransform(
                    transformBlock = {
                        translate(left = center.x, top = center.y)
                        scale(scale = scale, pivot = Offset.Zero)
                        translate(left = -pathBounds.center.x, top = -pathBounds.center.y)
                    },
                    drawBlock = {
                        drawPath(
                            path = path,
                            color = color
                        )
                    }
                )
                scale *= 0.9f
            }
        }
    }
}