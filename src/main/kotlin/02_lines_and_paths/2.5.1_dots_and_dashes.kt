package `02_lines_and_paths`

import androidx.compose.desktop.Window
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
import org.jetbrains.skija.*

fun main() = Window {
    val dashArray = remember {
        listOf(
            "10, 10",
            "30, 10",
            "10, 10, 30, 10",
            "0, 20", "20, 20",
            "0, 20, 20, 20"
        )
    }
    var selectedCap by remember { mutableStateOf(PaintStrokeCap.BUTT) }
    var selectedDash by remember { mutableStateOf(dashArray.first()) }
    Column {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Stroke Cap:")
                for (cap in PaintStrokeCap.values()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = cap == selectedCap,
                            onClick = {
                                selectedCap = cap
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        ClickableText(
                            text = AnnotatedString(cap.toString()),
                            onClick = {
                                selectedCap = cap
                            }
                        )
                    }
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Dash Array:")
                for (dash in dashArray) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = dash == selectedDash,
                            onClick = {
                                selectedDash = dash
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        ClickableText(
                            text = AnnotatedString(dash),
                            onClick = {
                                selectedDash = dash
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
                val canvas = cvs.nativeCanvas
                val paint = Paint().apply {
                    color = Color.Blue.toArgb()
                    mode = PaintMode.STROKE
                    pathEffect = PathEffect.makeDash(selectedDash.toIntervals(), 20f)
                    strokeCap = selectedCap
                    strokeWidth = 10f
                }
                val path = Path()
                path.moveTo(0.2f * size.width, 0.2f * size.height)
                path.lineTo(0.8f * size.width, 0.8f * size.height)
                path.lineTo(0.2f * size.width, 0.8f * size.height)
                path.lineTo(0.8f * size.width, 0.2f * size.height)
                canvas.drawPath(path, paint)
            }
        }
    }
}

private fun String.toIntervals(): FloatArray =
    split(' ', ',')
        .filter { it.trim().isNotBlank() }
        .map { it.toFloat() }
        .toFloatArray()