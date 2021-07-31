package `02_lines_and_paths`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.RadioButton
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
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import org.jetbrains.skija.PaintStrokeCap
import org.jetbrains.skija.Point

enum class PointMode {
    Lines, Points, Polygon
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val caps = remember { PaintStrokeCap.values() }
        val modes = remember { PointMode.values() }
        val pointsPaint = remember {
            Paint().apply {
                color = Color.Black.toArgb()
                mode = PaintMode.STROKE
                strokeWidth = 50f
            }
        }
        var cap by remember { mutableStateOf(PaintStrokeCap.BUTT) }
        var mode by remember { mutableStateOf(PointMode.Lines) }
        Column {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    caps.forEach { strokeCap ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = strokeCap == cap,
                                onClick = {
                                    cap = strokeCap
                                }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            ClickableText(
                                text = AnnotatedString(strokeCap.name),
                                onClick = {
                                    cap = strokeCap
                                }
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    modes.forEach { pointMode ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = pointMode == mode,
                                onClick = {
                                    mode = pointMode
                                }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            ClickableText(
                                text = AnnotatedString(pointMode.name),
                                onClick = {
                                    mode = pointMode
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
                val points = Array<Point>(size = 10) { Point.ZERO }
                repeat(2) { i ->
                    val x = (0.1f + 0.8f * i) * size.width
                    repeat(5) { j ->
                        val y = (0.1f + 0.2f * j) * size.height
                        points[2 * j + i] = Point(x, y)
                    }
                }
                drawIntoCanvas { cvs ->
                    cvs.nativeCanvas.run {
                        pointsPaint.strokeCap = cap
                        when (mode) {
                            PointMode.Lines -> drawLines(points, pointsPaint)
                            PointMode.Points -> drawPoints(points, pointsPaint)
                            PointMode.Polygon -> drawPolygon(points, pointsPaint)
                        }
                    }
                }
            }
        }
    }
}