package `02_lines_and_paths`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        // Has compose desktop support to multi-touch?
        // val inProgressPaths = remember { mutableMapOf<PointerId, Path>() }
        val recompose = currentRecomposeScope
        val completedPaths = remember { mutableListOf<Path>() }
        var inProgressPath by remember { mutableStateOf(Path()) }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragCancel = {
                            inProgressPath = Path()
                        },
                        onDragEnd = {
                            completedPaths += inProgressPath
                            inProgressPath = Path()
                        },
                        onDragStart = {
                            inProgressPath = Path().apply {
                                moveTo(it.x, it.y)
                            }
                        },
                        onDrag = { change, _ ->
                            inProgressPath.lineTo(
                                x = change.position.x,
                                y = change.position.y
                            )
                            recompose.invalidate() // Is it fine call invalidate manually?
                        }
                    )
                }
        ) {
            for (path in completedPaths) {
                drawPath(
                    path = path,
                    color = Color.Blue,
                    style = Stroke(
                        width = 10f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
            drawPath(
                path = inProgressPath,
                color = Color.Blue,
                style = Stroke(
                    width = 10f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}