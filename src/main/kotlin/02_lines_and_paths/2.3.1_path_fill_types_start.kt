package `02_lines_and_paths`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val drawingModes = remember {
            listOf("Fill only", "Stroke only", "Stroke then Fill", "Fill then Stroke")
        }
        val fillPaint = remember {
            Paint().apply {
                color = Color.Blue.toArgb()
                mode = PaintMode.FILL
            }
        }
        val strokePaint = remember {
            Paint().apply {
                color = Color.Red.toArgb()
                mode = PaintMode.STROKE
                strokeJoin = PaintStrokeJoin.ROUND
                strokeWidth = 50f
            }
        }
        var drawingMode by remember { mutableStateOf(drawingModes.first()) }
        var fillMode by remember { mutableStateOf(PathFillMode.WINDING) }
        Column {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Path Fill Type:")
                    for (mode in PathFillMode.values()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = mode == fillMode,
                                onClick = {
                                    fillMode = mode
                                }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            ClickableText(
                                text = AnnotatedString(mode.name),
                                onClick = {
                                    fillMode = mode
                                }
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Drawing Mode:")
                    for (mode in drawingModes) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = mode == drawingMode,
                                onClick = {
                                    drawingMode = mode
                                }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            ClickableText(
                                text = AnnotatedString(mode),
                                onClick = {
                                    drawingMode = mode
                                }
                            )
                        }
                    }
                }
            }
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                drawIntoCanvas { cvs ->
                    val radius = 0.45f * min(size.width, size.height)
                    val path = Path()
                    path.fillMode = fillMode
                    path.moveTo(center.x, center.y - radius)
                    for (value in 1 until 5) {
                        val angle = (value * 4 * PI / 5).toFloat()
                        val point = Point(
                            radius * sin(angle),
                            -radius * cos(angle),
                        )
                        path.lineTo(
                            center.x + point.x,
                            center.y + point.y
                        )
                    }
                    path.closePath()
                    val canvas = cvs.nativeCanvas
                    when (drawingMode) {
                        drawingModes[0] -> canvas.drawPath(path, fillPaint)
                        drawingModes[1] -> canvas.drawPath(path, strokePaint)
                        drawingModes[2] -> canvas.run {
                            drawPath(path, strokePaint)
                            drawPath(path, fillPaint)
                        }
                        drawingModes[3] -> canvas.run {
                            drawPath(path, fillPaint)
                            drawPath(path, strokePaint)
                        }
                    }
                }
            }
        }
    }
}