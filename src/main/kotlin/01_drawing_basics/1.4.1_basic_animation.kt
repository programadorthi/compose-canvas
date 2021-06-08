package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sin

fun main() = Window {
    var cycleTime by remember { mutableStateOf(5f) }
    var scale by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { nanoTime ->
                val elapsedSeconds = nanoTime / 1_000_000_000L
                val time = elapsedSeconds % cycleTime / cycleTime
                scale = ((1 + sin(2 * PI * time)) / 2).toFloat()
            }
            delay(1000 / 60)
        }
    }
    Column {
        Slider(
            value = cycleTime,
            valueRange = 0.1f..10f,
            onValueChange = { value ->
                cycleTime = value
            }
        )
        Text(
            text = "Cycle time = %.1f seconds".format(cycleTime)
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            val maxRadius = 0.75f * min(size.width, size.height) / 2
            val minRadius = 0.25f * maxRadius
            val radius = minRadius * scale + maxRadius * (1 - scale)
            drawCircle(
                color = Color.Blue,
                radius = radius,
                style = Stroke(width = 50f)
            )
            drawCircle(
                color = Color.LightGray,
                radius = radius
            )
        }
    }
}