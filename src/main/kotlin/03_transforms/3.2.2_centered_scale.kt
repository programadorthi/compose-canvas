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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toComposeRect
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
    val title = "Centered Scale"
    var horizontalScale by remember { mutableStateOf(1f) }
    var verticalScale by remember { mutableStateOf(1f) }
    Column {
        Slider(
            value = horizontalScale,
            valueRange = -10f..10f,
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
            valueRange = -10f..10f,
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
            font.size = 50f
            val textBounds = font.measureText(title, textPaint)
            val margin = (size.width - textBounds.width) / 2f
            val px = margin + textBounds.width / 2f
            val py = margin + textBounds.height / 2f

            scale(
                scaleX = horizontalScale,
                scaleY = verticalScale,
                pivot = Offset(px, py)
            ) {
                drawRoundRect(
                    color = Color.Red,
                    topLeft = Offset(margin, margin),
                    size = textBounds.toComposeRect().size,
                    cornerRadius = CornerRadius(20f, 20f),
                    style = Stroke(
                        width = 3f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                            intervals = floatArrayOf(7f, 7f),
                            phase = 0f
                        )
                    )
                )

                val canvas = drawContext.canvas.nativeCanvas
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