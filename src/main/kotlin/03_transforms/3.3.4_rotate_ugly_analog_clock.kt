package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.min

fun localDateTime(): LocalDateTime =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        var localDateTime by remember { mutableStateOf(localDateTime()) }
        LaunchedEffect(Unit) {
            while (true) {
                localDateTime = localDateTime()
                delay(1_000)
            }
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            translate(left = center.x, top = center.y) {
                scale(
                    scale = min(size.width / 200f, size.height / 200f),
                    pivot = Offset.Zero
                ) {
                    for (angle in 0..360 step 6) {
                        rotate(degrees = angle.toFloat(), pivot = Offset.Zero) {
                            drawCircle(
                                center = Offset(0f, -90f),
                                radius = if (angle % 30 == 0) 4f else 2f,
                                color = Color.Gray
                            )
                        }
                    }
                    // Hour hand
                    rotate(
                        degrees = 30f * localDateTime.hour + localDateTime.minute / 2f,
                        pivot = Offset.Zero
                    ) {
                        drawLine(
                            start = Offset.Zero,
                            end = Offset(0f, -50f),
                            color = Color.Black,
                            strokeWidth = 20f,
                            cap = StrokeCap.Round
                        )
                    }
                    // Minute hand
                    rotate(
                        degrees = 6f * localDateTime.minute + localDateTime.second / 10f,
                        pivot = Offset.Zero
                    ) {
                        drawLine(
                            start = Offset.Zero,
                            end = Offset(0f, -70f),
                            color = Color.Black,
                            strokeWidth = 10f,
                            cap = StrokeCap.Round
                        )
                    }
                    // Second hand
                    rotate(
                        degrees = 6f * localDateTime.second,
                        pivot = Offset.Zero
                    ) {
                        drawLine(
                            start = Offset(0f, 10f),
                            end = Offset(0f, -80f),
                            color = Color.Black,
                            strokeWidth = 2f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }
}