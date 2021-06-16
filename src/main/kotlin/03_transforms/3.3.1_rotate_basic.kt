package `03_transforms`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode

fun main() = Window {
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use { Font(it.getTypeface(0)) }
    }
    val textPaint = remember {
        Paint().apply {
            color = Color.Blue.toArgb()
            mode = PaintMode.FILL
        }
    }
    val title = "Basic Rotate"
    var rotation by remember { mutableStateOf(0f) }
    Column {
        Slider(
            value = rotation,
            valueRange = -360f..360f,
            onValueChange = { value ->
                rotation = value
            }
        )
        Text(
            text = "Horizontal Scaling = %.1f".format(rotation),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            font.size = 100f

            // Compose pivot is center by default
            // To have a centered rotate version just remove pivot param below
            rotate(
                degrees = rotation
            ) {
                val canvas = drawContext.canvas.nativeCanvas
                canvas.drawString(
                    title,
                    center.x,
                    center.y,
                    font,
                    textPaint
                )
            }
        }
    }
}