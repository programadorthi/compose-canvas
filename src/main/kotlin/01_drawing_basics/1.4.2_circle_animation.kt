package `01_drawing_basics`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlin.math.min

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val cycleTime = 1000f
        var time by remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) {
            while (true) {
                withFrameMillis { millisTime ->
                    time = millisTime % cycleTime / cycleTime
                }
                delay(1000 / 60)
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val baseRadius = min(size.width, size.height) / 12
            repeat(5) { circle ->
                val radius = baseRadius * (circle + time)
                drawCircle(
                    color = Color.Blue.copy(
                        alpha = if (circle == 4) 1 - time else 1f
                    ),
                    radius = radius,
                    style = Stroke(
                        width = baseRadius / 2 * (if (circle == 0) time else 1f)
                    )
                )
            }
        }
    }
}