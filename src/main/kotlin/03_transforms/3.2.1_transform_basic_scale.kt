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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.skija.*

fun main() = Window {
    val font = remember {
        FontMgr.getDefault()
            .matchFamily("Arial")
            .use { Font(it.getTypeface(0)) }
    }
    val strokePaint = remember {
        Paint().apply {
            color = Color.Red.toArgb()
            mode = PaintMode.STROKE
            strokeWidth = 3f
            pathEffect = PathEffect.makeDash(floatArrayOf(7f, 7f), 0f)
        }
    }
    val textPaint = remember {
        Paint().apply {
            color = Color.Blue.toArgb()
            mode = PaintMode.FILL
        }
    }
    val margin = 10f
    val title = "Basic Scale"
    var horizontalScale by remember { mutableStateOf(1f) }
    var verticalScale by remember { mutableStateOf(1f) }
    Column {
        Slider(
            value = horizontalScale,
            valueRange = 1f..margin,
            onValueChange = { value ->
                horizontalScale = value
            }
        )
        Text(
            text = "Horizontal Scaling = %.1f".format(horizontalScale),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Slider(
            value = verticalScale,
            valueRange = 1f..margin,
            onValueChange = { value ->
                verticalScale = value
            }
        )
        Text(
            text = "Vertical Scaling = %.1f".format(verticalScale),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            drawIntoCanvas { cvs ->
                font.size = 50f

                val canvas = cvs.nativeCanvas
                canvas.scale(horizontalScale, verticalScale)

                val textBounds = font.measureText(title, textPaint)

                canvas.drawRRect(
                    RRect.makeXYWH(
                        margin,
                        margin,
                        textBounds.width,
                        textBounds.height,
                        20f
                    ),
                    strokePaint
                )
                canvas.drawString(
                    title,
                    margin,
                    -textBounds.top + margin,
                    font,
                    textPaint
                )
            }
        }
    }
}