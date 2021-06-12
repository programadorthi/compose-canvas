package `03_transforms`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate

fun main() = Window {
    val rectangleCount = 20
    val rect = Rect(
        offset = Offset.Zero,
        size = Size(250f, 250f)
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        var xTranslate = 0f
        var yTranslate = 0f
        repeat(rectangleCount) {
            translate(
                left = xTranslate,
                top = yTranslate
            ) {
                drawRect(
                    color = Color.Black,
                    size = rect.size,
                    style = Stroke(
                        width = 3f
                    )
                )
            }
            // DrawScope translation are not cumulative
            xTranslate += (size.width - rect.width) / (rectangleCount - 1)
            yTranslate += (size.height - rect.height) / (rectangleCount - 1)
        }
    }
}