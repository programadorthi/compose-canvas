package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

fun main() = Window {
    var showFill by remember { mutableStateOf(true) }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                showFill = showFill.not()
            }
    ) {
        drawCircle(
            color = Color.Red,
            radius = 100f,
            style = Stroke(
                width = 50f
            )
        )
        if (showFill) {
            drawCircle(
                color = Color.Blue,
                radius = 100f
            )
        }
    }
}