package `03_transforms`

import androidx.compose.animation.core.*
import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.min

fun main() = Window {
    val transition = rememberInfiniteTransition()
    val revolveDegrees by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10_000,
                easing = LinearEasing
            )
        )
    )
    val rotateDegrees by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1_000,
                easing = LinearEasing
            )
        )
    )
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        withTransform(
            transformBlock = {
                translate(left = center.x, top = center.y)
                rotate(degrees = 360f * revolveDegrees, pivot = Offset.Zero)
                val radius = min(size.width, size.height) / 3f
                translate(left = radius)
                rotate(degrees = 360f * rotateDegrees, pivot = Offset.Zero)
            },
            drawBlock = {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(-50f, -50f),
                    size = Size(50f, 50f)
                )
            }
        )
    }
}