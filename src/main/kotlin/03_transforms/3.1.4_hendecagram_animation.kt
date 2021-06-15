package `03_transforms`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.translate
import kotlinx.coroutines.delay
import utils.hendecagramPath
import utils.hslToRgb
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

fun main() = Window {
    val cycleTime = 5_000f // in milliseconds
    val path = hendecagramPath()
    var angle by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis { timeMillis ->
                val elapsed = timeMillis % cycleTime / cycleTime
                angle = 360 * elapsed
            }
            delay(1000 / 60)
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = center.x, top = center.y) {
            val radius = min(size.width, size.height) / 2 - 100
            val x = radius * sin(PI * angle / 180)
            val y = -radius * cos(PI * angle / 180)
            translate(left = x.toFloat(), top = y.toFloat()) {
                drawPath(
                    path = path,
                    color = hslToRgb(
                        hue = angle,
                        saturation = 100f,
                        lightness = 50f
                    )
                )
            }
        }
    }
}