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
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import kotlin.math.PI
import kotlin.math.tan

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
    var xSkewDegrees by remember { mutableStateOf(0f) }
    var ySkewDegrees by remember { mutableStateOf(0f) }
    Column {
        Slider(
            value = xSkewDegrees,
            valueRange = -90f..90f,
            onValueChange = { value ->
                xSkewDegrees = value
            }
        )
        Text(
            text = "Horizontal Skew = %.1f°".format(xSkewDegrees),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Slider(
            value = ySkewDegrees,
            valueRange = -90f..90f,
            onValueChange = { value ->
                ySkewDegrees = value
            }
        )
        Text(
            text = "Vertical Skew = %.1f°".format(ySkewDegrees),
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
                font.size = 200f

                val canvas = cvs.nativeCanvas
                val text = "SKEW"
                val textBounds = font.measureText(text, textPaint)
                val xText = center.x - textBounds.width / 2
                val yText = center.y - textBounds.height / 2

                canvas.translate(center.x, center.y)
                canvas.skew(
                    tan(PI * xSkewDegrees / 180).toFloat(),
                    tan(PI * ySkewDegrees / 180).toFloat(),
                )
                canvas.translate(-center.x, -center.y)
                canvas.drawString(
                    text,
                    xText,
                    yText,
                    font,
                    textPaint
                )
            }
        }
    }
}